package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Евросеть on 28.02.2017.
 */
public class HeroLine extends JPanel {
    CharacterButton[] buttons;
    private final int deltawidth =10;

    public HeroLine(){
        buttons = new CharacterButton[8];
        buttons[0]=new CharacterButton(deltawidth,deltawidth,new ButtonImage("res/NikdeFicrusimage.png","res/NikdeFicrusimageclicked.png","res/NikdeFicrusimageentered.png"));
        buttons[0].k=1;
        buttons[0].setI(0);
        for (int i=1;i<buttons.length;i++) {
            buttons[i] = new CharacterButton(deltawidth * (i + 1) + CharacterButton.CONST * i, deltawidth, new ButtonImage("res/testbutton.png", "res/testbuttonclicked.png", "res/testbuttonentered.png"));
            buttons[i].k=0;
        }
        this.setLayout(null);
        for (int i=0;i<buttons.length;i++){
            buttons[i].setBounds(buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
            this.add(buttons[i]);
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,deltawidth*9+8*CharacterButton.CONST,deltawidth*2+CharacterButton.CONST);
        for (int i=0;i<buttons.length;i++){
            buttons[i].paint(g);
        }
    }
}
