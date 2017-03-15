package com.company;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ButtonImage {
    private Image image;
    private Image imageClicked;
    private Image imageEntered;
    public ButtonImage(String image,String imageClicked,String imageEntered){
        URL imgURL = MyRect.class.getResource(image);
        this.image = new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource(imageClicked);
        this.imageClicked = new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource(imageEntered);
        this.imageEntered = new ImageIcon(imgURL).getImage();
    }
    public Image getImage(){
        return image;

    }
    public Image getImageClicked(){
        return imageClicked;

    }
    public Image getImageEntered(){
        return imageEntered;

    }

}
