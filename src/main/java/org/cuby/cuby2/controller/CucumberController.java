package org.cuby.cuby2.controller;

import reactor.core.publisher.Flux;

import org.cuby.cuby2.configuration.JarProperties;
import org.cuby.cuby2.model.Cucumber;
import org.cuby.cuby2.model.Jar;
import org.cuby.cuby2.service.CucumberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrey.semenov
 */
@RestController
@RequestMapping("/api/cucumbers")
public class CucumberController {

    private final CucumberService service;

    public CucumberController(CucumberService service) {
        this.service = service;
    }

    @PostMapping("/make_jars")
    public Flux<Jar> process(@RequestBody Flux<Cucumber> cucumbers, JarProperties props) {


    }
}
