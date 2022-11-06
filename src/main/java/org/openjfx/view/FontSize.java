package org.openjfx.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCssPath(FontSize fontSize){
        return switch(fontSize){
            case SMALL -> "/css/fontSmall.css";
            case MEDIUM -> "/css/fontMedium.css";
            case BIG -> "/css/fontBig.css";
            default -> null;
        };
    }
}
