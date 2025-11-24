package org.cuby.cuby2.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.cuby.cuby2.configuration.JarProperties;
import org.cuby.cuby2.model.Cucumber;
import org.cuby.cuby2.model.Jar;
import org.springframework.stereotype.Service;

/**
 * @author andrey.semenov
 */
@Service
public class CucumberService {

    int maxVolume;
    int minVolume;
    int restLimit;

    public CucumberService() {

    }

    public Flux<Jar> groupByVolume(Flux<Cucumber> cucumbers,
                                              double minVolume,
                                              double maxVolume,
                                              double restLimit) {

        Jar currentJar = new Jar(minVolume, maxVolume);

        return Flux.defer(() -> {

            return cucumbers.<Jar>handle((cucumber, sink) -> {

                final double res = currentJar.add(cucumber);
                if (res != 0) {
                    sink.next(currentJar);
                    currentJar = new Jar(minVolume, maxVolume);
                    currentJar.add(new Cucumber(restLimit, res));
                }

            }).concatWith(Mono.defer(() -> {
                if (!currentJar.isEmpty()) {
                    return Mono.just(currentJar);
                }
                return Mono.empty();
            }));
        });
    }
}
