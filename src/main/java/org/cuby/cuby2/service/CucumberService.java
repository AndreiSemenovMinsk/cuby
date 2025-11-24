package org.cuby.cuby2.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.cuby.cuby2.model.Cucumber;
import org.cuby.cuby2.model.Jar;
import org.springframework.stereotype.Service;

/**
 * @author andrey.semenov
 */
@Service
public class CucumberService {

    public Jar makeJar(List<Cucumber> cucumbers) {
        // если хочешь — можно проверить, что size == 5
        return new Jar(cucumbers);
    }

    public Flux<List<Cucumber>> groupByVolume(Flux<Cucumber> cucumbers,
                                              double minVolume,
                                              double maxVolume,
                                              double restLimit) {
        return Flux.defer(() -> {
            List<Cucumber> bucket = new ArrayList<>();
            MutableDouble currentVolume = new MutableDouble(0);

            boolean fullCucumber = false;
            double restCucumber = 0;

            return cucumbers.<List<Cucumber>>handle((cucumber, sink) -> {

                double newVolume = currentVolume.get() + cucumber.getVolume();
                double jarVolume = ThreadLocalRandom.current().nextDouble(minVolume, maxVolume);

                if (newVolume <= jarVolume) {
                    // помещается в текущую банку
                    bucket.add(cucumber);
                    currentVolume.add(cucumber.getVolume());
                } else {
                    boolean cutCucumber = false;
                    double beginCucumber = jarVolume - currentVolume.get();

                    if (beginCucumber > restLimit) {
                        bucket.add(new Cucumber(beginCucumber));
                        cutCucumber = true;
                    }
                    sink.next(List.copyOf(bucket));

                    // начинаем новую банку
                    bucket.clear();

                    if (cutCucumber) {
                        double rest = newVolume - jarVolume;
                        if (rest > restLimit) {
                            bucket.add(new Cucumber(rest));
                            currentVolume.set(rest);
                        }
                    } else {
                        bucket.add(cucumber);
                        currentVolume.set(cucumber.getVolume());
                    }
                }
            }).concatWith(Mono.defer(() -> {
                // когда поток закончится — не забудем отдать последнюю банку
                if (!bucket.isEmpty()) {
                    return Mono.just(List.copyOf(bucket));
                }
                return Mono.empty();
            }));
        });
    }

    private class MutableDouble {
        double value;
        MutableDouble(double value) { this.value = value; }
        void add(double addValue) { this.value += addValue; }
        double get() { return value; }
        MutableDouble set(double newValue) {value = newValue; return this; }
    }

}
