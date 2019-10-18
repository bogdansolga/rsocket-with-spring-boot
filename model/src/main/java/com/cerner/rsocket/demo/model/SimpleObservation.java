package com.cerner.rsocket.demo.model;

import java.io.Serializable;

public class SimpleObservation implements Serializable {

    private final int id;
    private final String name;

    public SimpleObservation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
