package org.cuby.cuby2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author andrey.semenov
 */
@Data
@AllArgsConstructor
public class Cucumber {

    private double limit;
    private double volume;

    public double tryFull(double rate) {
        if (rate > volume) {
            return rate;
        }
        return 0;
    }

    public double getBegin(double rate) {
        if (limit > rate) {
            return 0;
        }
        return rate;
    }

    public double getEnd(double rate) {
        if (volume - rate > limit) {
            return volume - rate;
        }
        return 0;
    }

}
