package org.openjfx.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageFactory {

    public ImageView getImageView(String iconPath,double width,double height){
        Image image = new Image(getClass().getResourceAsStream(iconPath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        return imageView;
    }

    public Image getImageFromUrl(String url){
        return new Image(url);
    }

    public Image getImageFromUrl(SimpleStringProperty iconStringProperty) {
        return new Image(iconStringProperty.get());
    }
}
