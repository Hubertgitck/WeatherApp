package org.openjfx.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openjfx.controller.Persistence.Persistence;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class PersistenceTest {

    @Disabled
    @Test
    void shouldReturnPersistenceDataFromFile() {

        //given
        String testFileLocation = "/rememberedCities.ser";
        List<String> persistenceList;

        //when
        persistenceList = Persistence.loadFromPersistence(testFileLocation);

        //then
        assertAll(
                () -> assertThat(persistenceList.get(0), is("DEFAULT")),
                () -> assertThat(persistenceList.get(1), is("MEDIUM")),
                () -> assertThat(persistenceList.get(2), is("Kielce")),
                () -> assertThat(persistenceList.get(3), is("Warsaw"))
        );
    }
}