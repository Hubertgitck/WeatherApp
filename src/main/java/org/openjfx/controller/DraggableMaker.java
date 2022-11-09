package org.openjfx.controller;

import javafx.scene.Node;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeWindowDraggable(Node node) {

        node.setOnMousePressed( event ->{
            mouseAnchorX = event.getX();
            mouseAnchorY = event.getY();
        });

        node.setOnMouseDragged( event ->{
            node.getScene().getWindow().setX(event.getScreenX() - mouseAnchorX);
            node.getScene().getWindow().setY(event.getScreenY() - mouseAnchorY);
           /* node.setLayoutX(event.getSceneX() - mouseAnchorX);
            node.setLayoutY(event.getSceneY() - mouseAnchorY);*/
        });
    }
}
