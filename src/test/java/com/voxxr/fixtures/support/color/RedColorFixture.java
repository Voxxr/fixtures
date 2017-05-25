package com.voxxr.fixtures.support.color;

import com.voxxr.fixtures.AbstractFixture;

public class RedColorFixture extends AbstractFixture {

    public static final String REF_NAME = "color-red";

    public static final String COLOR = "red";

    @Override
    public void load() {
        setReference(REF_NAME, new Color(COLOR));
    }

}
