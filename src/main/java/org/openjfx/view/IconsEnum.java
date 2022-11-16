package org.openjfx.view;

public enum IconsEnum {
    MENU_WHITE,
    MENU_GREEN,
    EXIT_WHITE,
    EXIT_RED,
    CLOSE_WHITE,
    CLOSE_RED;

    public static String getIconPath(IconsEnum iconsEnum){

        return switch (iconsEnum) {
            case MENU_WHITE -> "/icons/menuWhite.png";
            case MENU_GREEN -> "/icons/menuGreen.png";
            case EXIT_WHITE -> "/icons/exitWhite.png";
            case EXIT_RED -> "/icons/exitRed.png";
            case CLOSE_WHITE ->"/icons/closeWhite.png";
            case CLOSE_RED -> "/icons/closeRed.png";
            default -> null;
        };
    }
}
