package org.openjfx.view;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ImageFactoryTest {

    private final ImageFactory imageFactory = new ImageFactory();
    private final ImageView result = imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.MENU_GREEN),30,30);

    @Test
    void shouldReturnImageViewObject() {

        //then
        assertThat(result, instanceOf(ImageView.class));
    }

    @Test
    void shouldReturnImageWithGivenWidth() {

        //then
        assertThat(result.getFitWidth(), equalTo(30D));
    }

    @Test
    void shouldReturnImageWithGivenHeight() {

        //then
        assertThat(result.getFitHeight(), equalTo(30D));
    }
}