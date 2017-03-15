package com.company;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by user on 23.02.2017.
 */
public class Background extends JPanel {
    public void paintComponent(Graphics g) {
        URL imgURL = Main.class.getResource("menu.png");
        Image image = new ImageIcon(imgURL).getImage();
        g.drawImage(image, 0, 0, null);
    }
}
