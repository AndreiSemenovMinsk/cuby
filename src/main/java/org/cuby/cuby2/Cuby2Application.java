package org.cuby.cuby2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Cuby2Application {

    public static void main(String[] args) {
        SpringApplication.run(Cuby2Application.class, args);
    }

}
