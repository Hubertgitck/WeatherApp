package org.openjfx.view;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class IconsEnumTest {

    @Test
    void shouldReturnPathToIcon() {

        //given
        IconsEnum iconsEnum = IconsEnum.MENU_WHITE;

        //when
        String iconPath = IconsEnum.getIconPath(iconsEnum);

        //then
        assertThat(iconPath, is("/icons/menuWhite.png"));
    }
}