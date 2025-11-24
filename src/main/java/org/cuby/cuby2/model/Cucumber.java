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

    public double tryWhole(double gauge) {
        if (gauge > volume) {
            return gauge;
        }
        return 0;
    }

    public double getBegin(double gauge) {
        if (limit > gauge) {
            return 0;
        }
        return gauge;
    }
}
