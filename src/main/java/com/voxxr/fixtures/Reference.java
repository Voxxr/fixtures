package com.voxxr.fixtures;

public class Reference<T> {

    private T object;

    public Reference(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

}
