package org.openjfx.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum IconsEnum {
    MENU_WHITE,
    MENU_GREEN,
    EXIT_WHITE,
    EXIT_RED;

    public static String getIconPath(IconsEnum iconsEnum){

        return switch (iconsEnum) {
            case MENU_WHITE -> "/icons/menuWhite.png";
            case MENU_GREEN -> "/icons/menuGreen.png";
            case EXIT_WHITE -> "/icons/exitWhite.png";
            case EXIT_RED -> "/icons/exitRed.png";
            default -> null;
        };
    }
}
