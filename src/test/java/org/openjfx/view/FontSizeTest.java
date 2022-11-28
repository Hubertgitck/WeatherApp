package org.openjfx.view;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FontSizeTest {

    @Test
    void shouldReturnPathToFontSizeStylesheet() {

        //given
        FontSize fontSize = FontSize.MEDIUM;

        //when
        String fontSizeStylesheet = FontSize.getCssPath(fontSize);

        //then
        assertThat(fontSizeStylesheet, is("/css/fontMedium.css"));
    }


}