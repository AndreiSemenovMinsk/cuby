package org.cuby.cuby2.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.cuby.cuby2.model.Cucumber;
import org.cuby.cuby2.model.Jar;
import org.springframework.stereotype.Service;

/**
 * @author andrey.semenov
 */
@Service
public class CucumberService {

    public Flux<Jar> groupByVolume(Flux<Cucumber> cucumbers,
                                              double minVolume,
                                              double maxVolume,
                                              double restLimit) {

        final Jar[] currentJar = {new Jar(minVolume, maxVolume)};

        return Flux.defer(() -> {

            return cucumbers.<Jar>handle((cucumber, sink) -> {

                final double res = currentJar[0].push(cucumber);
                if (res != 0) {
                    sink.next(currentJar[0]);
                    currentJar[0] = new Jar(minVolume, maxVolume);
                    currentJar[0].push(new Cucumber(restLimit, res));
                }

            }).concatWith(Mono.defer(() -> {
                if (!currentJar[0].isEmpty()) {
                    return Mono.just(currentJar[0]);
                }
                return Mono.empty();
            }));
        });
    }
}
