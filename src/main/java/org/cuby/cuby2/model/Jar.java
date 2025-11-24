package org.cuby.cuby2.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author andrey.semenov
 */
@Data
@RequiredArgsConstructor
public class Jar {
    private int id;
    private int capacity;
    @NonNull
    private List<Cucumber> cucumbers;
}
