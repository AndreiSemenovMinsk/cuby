package org.cuby.cuby2;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import org.cuby.cuby2.model.Cucumber;
import org.cuby.cuby2.service.CucumberService;
import org.junit.jupiter.api.Test;

/**
 * @author andrey.semenov
 */
public class CucumberServiceTest {

    private final CucumberService service = new CucumberService();

    @Test
    void testConvert() {

        List<Cucumber> cucumbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cucumbers.add(new Cucumber(i, 0.1));
        }
        final Flux<Cucumber> flux = Flux.fromIterable(cucumbers).log();
        StepVerifier.create(service.groupByVolume(flux, 19.8, 20.2, 0.1))
                .expectNextMatches(p -> p.getCucumbers().size() == 6)
                .expectNextMatches(p -> p.getCucumbers().size() == 4)
                .expectNextMatches(p -> p.getCucumbers().size() == 2)
                .verifyComplete();

        StepVerifier.create(service.groupByVolume(flux, 15.1, 15.4, 0.1))
                .expectNextMatches(p -> p.getCucumbers().size() == 6)
                .expectNextMatches(p -> p.getCucumbers().size() == 3)
                .expectNextMatches(p -> p.getCucumbers().size() == 3)
                .expectNextMatches(p -> p.getCucumbers().size() == 1)
                .verifyComplete();
    }
}
