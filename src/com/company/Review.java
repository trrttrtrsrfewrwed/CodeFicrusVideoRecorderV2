package com.company;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by Евросеть on 28.02.2017.
 */
public class Review extends JPanel{
    private Image hero;

    Review(){
        URL imgURL = Main.class.getResource("res/Nikreview.png");
        this.hero = new ImageIcon(imgURL).getImage();
    }

    public void update(Graphics g) {
        paint(g);
    }
    public void setImage(Image image){
        hero = image;
    }


    public void paint(Graphics g) {
        URL imgURL = Main.class.getResource("res/stol.png");
        Image stol = new ImageIcon(imgURL).getImage();

        g.drawImage(hero,0,125+stol.getHeight(null)-hero.getHeight(null),null);
        g.drawImage(stol,0,125,null);
    }
}
