package org.cuby.cuby2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author andrey.semenov
 */
@Data
public class Jar {

    private double capacity;
    private double volume;

    public Jar(double min, double max) {
        capacity = ThreadLocalRandom.current().nextDouble(min, max);
    }

    @NonNull
    private List<Cucumber> cucumbers;

    public double add(Cucumber cucumber) {

        double rate = capacity - volume;

        double cucumberVolume = cucumber.tryFull(rate);

        if (cucumberVolume == 0) {
            //не всунули

            double addingVolume = cucumber.getBegin(rate);
            if (addingVolume > 0) {
                //кончик поместился
                volume += addingVolume;
                cucumbers.add(cucumber);
                return addingVolume;
            }
            return -1;
        } else {
            //всунули - неси еще
            volume += cucumberVolume;
            cucumbers.add(cucumber);
            return 0;
        }
    }

    public boolean isEmpty() {
        return cucumbers.isEmpty();
    }
}
