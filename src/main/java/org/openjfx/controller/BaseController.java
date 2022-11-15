package org.openjfx.controller;

import org.openjfx.view.ImageFactory;
import org.openjfx.view.ViewFactory;

public abstract class BaseController {
    protected ViewFactory viewFactory;
    private final String fxmlName;
    protected final DraggableMaker draggableMaker = new DraggableMaker();
    protected final ImageFactory imageFactory = new ImageFactory();

    public BaseController(ViewFactory viewFactory, String fxmlName) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }

}
