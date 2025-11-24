package org.cuby.cuby2.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author andrey.semenov
 */
@Component
@ConfigurationProperties(prefix = "app.jar")
public class JarProperties {

    private int maxVolume = 5;
    private int minVolume = 5;
    private int restLimit = 5;

    public int getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
    }

    public int getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(int maxVolume) {
        this.minVolume = maxVolume;
    }

    public int getRestLimit() {
        return restLimit;
    }

    public void setRestLimit(int restLimit) {
        this.restLimit = restLimit;
    }
}
