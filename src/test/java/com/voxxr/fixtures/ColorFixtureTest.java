package com.voxxr.fixtures;

import com.voxxr.fixtures.support.color.BlueColorFixture;
import com.voxxr.fixtures.support.color.Color;
import com.voxxr.fixtures.support.color.GreenColorFixture;
import com.voxxr.fixtures.support.color.RedColorFixture;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class ColorFixtureTest {

    @Test
    public void name() throws Exception {
        List<Fixture> fixtures = Arrays.asList(
                new RedColorFixture(),
                new BlueColorFixture(),
                new GreenColorFixture()
        );

        FixtureLoader loader = new FixtureLoader(fixtures);

        loader.load();

        assertTrue(loader.getReferenceRepository().hasReference(RedColorFixture.REF_NAME));
        assertThat(((Color) loader.getReferenceRepository().getReference(RedColorFixture.REF_NAME)).getName(),
                is(equalTo(RedColorFixture.COLOR)));

        assertTrue(loader.getReferenceRepository().hasReference(BlueColorFixture.REF_NAME));
        assertThat(((Color) loader.getReferenceRepository().getReference(BlueColorFixture.REF_NAME)).getName(),
                is(equalTo(BlueColorFixture.COLOR)));

        assertTrue(loader.getReferenceRepository().hasReference(GreenColorFixture.REF_NAME));
        assertThat(((Color) loader.getReferenceRepository().getReference(GreenColorFixture.REF_NAME)).getName(),
                is(equalTo(GreenColorFixture.COLOR)));
    }

}
