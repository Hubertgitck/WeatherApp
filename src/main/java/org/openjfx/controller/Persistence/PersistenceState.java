package org.openjfx.controller.Persistence;

import org.openjfx.view.ColorTheme;
import org.openjfx.view.FontSize;

public class PersistenceState {

    private String leftCity;
    private String rightCity;

    private ColorTheme colorTheme;
    private FontSize fontSize;

    private int numberOfCitiesSavedToPersistence;

    public int getNumberOfCitiesSavedToPersistence() {
        return numberOfCitiesSavedToPersistence;
    }

    public void setNumberOfCitiesSavedToPersistence(int numberOfCitiesSavedToPersistence) {
        this.numberOfCitiesSavedToPersistence = numberOfCitiesSavedToPersistence;
    }

    public String getLeftCity() {
        return leftCity;
    }

    public void setLeftCity(String leftCity) {
        this.leftCity = leftCity;
    }

    public String getRightCity() {
        return rightCity;
    }

    public void setRightCity(String rightCity) {
        this.rightCity = rightCity;
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }
}
