package com.voxxr.fixtures.support.color;

import com.voxxr.fixtures.AbstractFixture;

public class BlueColorFixture extends AbstractFixture {

    public static final String REF_NAME = "color-blue";

    public static final String COLOR = "blue";

    @Override
    public void load() {
        setReference(REF_NAME, new Color(COLOR));
    }

}
