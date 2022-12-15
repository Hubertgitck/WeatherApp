package org.openjfx.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjfx.model.Persistence.Persistence;
import org.openjfx.model.Persistence.PersistenceState;
import org.openjfx.view.ColorTheme;
import org.openjfx.view.FontSize;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PersistenceTest {

    @Mock
    private Persistence persistence;
    @Test
    void shouldReturnPersistenceStateFromFile() {

        //given
        PersistenceState expectedPersistenceState = createPersistenceState();
        given(persistence.getPersistenceState()).willReturn(expectedPersistenceState);

        //when
        PersistenceState result = persistence.getPersistenceState();

        //then
        assertAll(
                () -> assertThat(result.getColorTheme(), is(ColorTheme.DEFAULT)),
                () -> assertThat(result.getFontSize(), is(FontSize.MEDIUM)),
                () -> assertThat(result.getLeftCity(), is("Kielce")),
                () -> assertThat(result.getRightCity(), is("Warszawa"))
        );
    }

    private PersistenceState createPersistenceState() {
        PersistenceState persistenceState = new PersistenceState();

        persistenceState.setColorTheme(ColorTheme.DEFAULT);
        persistenceState.setFontSize(FontSize.MEDIUM);
        persistenceState.setLeftCity("Kielce");
        persistenceState.setRightCity("Warszawa");

        return persistenceState;
    }
}