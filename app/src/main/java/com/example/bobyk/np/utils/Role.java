package com.example.bobyk.np.utils;

/**
 * Created by bobyk on 4/17/17.
 */

public enum Role {
    DRIVER("Driver"), ADMIN("Administrator"), USER("User");

    private final String text;

    private Role(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
