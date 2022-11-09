package org.openjfx.controller;

import org.openjfx.model.Weather;
import org.openjfx.view.ViewFactory;

public abstract class BaseController {
    protected ViewFactory viewFactory;
    private final String fxmlName;

    public BaseController(ViewFactory viewFactory, String fxmlName) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }

}
