package com.voxxr.fixtures;

import com.voxxr.fixtures.Fixture;
import com.voxxr.fixtures.FixtureLoader;
import com.voxxr.fixtures.ReferenceRepository;
import com.voxxr.fixtures.support.car.AudiCarFixture;
import com.voxxr.fixtures.support.car.BmwCarFixture;
import com.voxxr.fixtures.support.car.VolvoCarFixture;
import com.voxxr.fixtures.support.color.BlueColorFixture;
import com.voxxr.fixtures.support.color.Color;
import com.voxxr.fixtures.support.color.GreenColorFixture;
import com.voxxr.fixtures.support.color.RedColorFixture;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CarFixtureTest {

    @Test
    public void name() throws Exception {
        // Purposefully initialize them in an incorrect order
        List<Fixture> fixtures = Arrays.asList(
                new VolvoCarFixture(),
                new BmwCarFixture(),
                new AudiCarFixture()
        );

        FixtureLoader loader = new FixtureLoader(fixtures);

        loader.load();

        ReferenceRepository referencesRepository = loader.getReferenceRepository();
        List<String> refIndex = new ArrayList<>(referencesRepository.getReferences().keySet());

        assertTrue(referencesRepository.hasReference(AudiCarFixture.REF_NAME));
        assertThat(((Color) loader.getReferenceRepository().getReference(AudiCarFixture.REF_NAME)).getName(),
                is(equalTo(AudiCarFixture.CAR)));
        assertThat(refIndex.indexOf(AudiCarFixture.CAR), is(equalTo(0)));

        assertTrue(referencesRepository.hasReference(BlueColorFixture.REF_NAME));
        assertThat(((Color) referencesRepository.getReference(BlueColorFixture.REF_NAME)).getName(),
                is(equalTo(BlueColorFixture.COLOR)));

        assertTrue(referencesRepository.hasReference(GreenColorFixture.REF_NAME));
        assertThat(((Color) referencesRepository.getReference(GreenColorFixture.REF_NAME)).getName(),
                is(equalTo(GreenColorFixture.COLOR)));
    }

}
