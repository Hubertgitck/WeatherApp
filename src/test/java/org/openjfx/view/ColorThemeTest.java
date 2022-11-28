package org.openjfx.view;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ColorThemeTest {

    @Test
    void shouldReturnPathToCssStylesheet() {

        //given
        ColorTheme colorTheme = ColorTheme.DEFAULT;

        //when
        String colorThemeStylesheet = ColorTheme.getCssPath(colorTheme);

        //then
        assertThat(colorThemeStylesheet, is("/css/themeDefault.css"));
    }
}