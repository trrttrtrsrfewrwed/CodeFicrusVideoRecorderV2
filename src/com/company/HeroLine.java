package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Евросеть on 28.02.2017.
 */
public class HeroLine extends JPanel {
    CharacterButton[] buttons;
    //расстояние между кнопками по горизонтали
    private final int deltawidth =10;

    public HeroLine(){
        buttons = new CharacterButton[8];
        //Создание первой кнопки с базовым персонажем (Ник де Фикрус)
        buttons[0]=new CharacterButton(deltawidth,deltawidth,new ButtonImage("res/NikdeFicrusimage.png","res/NikdeFicrusimageclicked.png","res/NikdeFicrusimageentered.png"));
        buttons[0].setbk(1);
        buttons[0].setI(0);
        //Cоздание пустых кнопок
        for (int i=1;i<buttons.length;i++) {
            buttons[i] = new CharacterButton(deltawidth * (i + 1) + CharacterButton.CONST * i, deltawidth, new ButtonImage("res/testbutton.png", "res/testbuttonclicked.png", "res/testbuttonentered.png"));
            buttons[i].setbk(0);
        }
        this.setLayout(null);
        //Добавление кнопок на линейку
        for (int i=0;i<buttons.length;i++){
            buttons[i].setBounds(buttons[i].getbx(),buttons[i].getby(),buttons[i].getbwidth(),buttons[i].getbheight());
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
