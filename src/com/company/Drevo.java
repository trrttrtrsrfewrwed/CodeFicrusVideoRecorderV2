package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Евросеть on 28.02.2017.
 */
public class Drevo extends JPanel{
    private final int width =410;
    private final int height =1000;
    CharacterButton []buttons;
    //количество гуманоидов
    private final int HumanoidsNum = 1;
    //количество чужих
    private final int AliensNum =1;
    //высота, на которой расположен текст "чужие"
    private int textaliensy;
    //количество дроидов
    private final int DroidsNum =4;
    //высота, на которой расположен текст "дроиды"
    private int textdroidsy;
    //общее количество героев
    private final int NUM = HumanoidsNum+AliensNum+DroidsNum;


    //расстояние между кнопками по вертикали, если между ними нет текста
    private  final int deltaheight= 10;
    //расстояние между кнопками по горизонтали
    private final int deltawidth =80;
    //отступ от левого края древа
    private final int delta =10;
    //расстояние между кнопками по вертикали, если между ними есть текст
    private final int deltaTextheight = 60;
    public Drevo(){
        ButtonImage[] buttonImage  = new ButtonImage[NUM];
        String [] name = new String[NUM];
        //создание изображений кнопок и подписей под ними(имен персонажей, изображенных на кнопках)
        buttonImage[0]=new ButtonImage("res/NikdeFicrusimage.png","res/NikdeFicrusimageclicked.png","res/NikdeFicrusimageentered.png");
        name[0]="Ник де Фикрус";
        buttonImage[1]=new ButtonImage("res/alienbutton.png","res/alienbuttonclicked.png","res/alienbuttonentered.png");
        name[1]="Чужой-трутень";
        buttonImage[2]=new ButtonImage("res/ironimage.png","res/ironimageclicked.png","res/ironimageentered.png");
        name[2]= String.format("Железное существо");
        buttonImage[3]=new ButtonImage("res/liftenimage.png","res/liftenimageclicked.png","res/liftenimageentered.png");
        name[3]="Лифтенштейн";
        buttonImage[4]=new ButtonImage("res/dragonimage.png","res/dragonimageclicked.png","res/dragonimageentered.png");
        name[4]="Дроид-снайпер";
        buttonImage[5]=new ButtonImage("res/droidimage.png","res/droidimageclicked.png","res/droidimageentered.png");
        name[5]="Дроид";
        buttons = new CharacterButton[buttonImage.length];
        //координата кнопок по горизонтали
        int widthnum=delta;
        //координата кнопок по вертикали
        int heightnum=deltaTextheight;

        //CONST - ширина кнопок и высота, если кнопка без текста
        //TextCONST - высота кнопки с текстом

        //Создание кнопок гуманоидов
        for (int i=0;i<HumanoidsNum;i++){
            if (widthnum+CharacterButton.CONST+deltawidth>width) {
                widthnum = delta;
                heightnum += deltaheight + CharacterButton.TextCONST;
            }
            buttons[i]=new CharacterButton(widthnum,heightnum,buttonImage[i],name[i]);
            widthnum+=CharacterButton.CONST+deltawidth;

        }
        heightnum+=deltaTextheight+CharacterButton.TextCONST+deltaheight;
        textaliensy= heightnum-deltaheight;
        widthnum=delta;

        //Создание кнопок чужих
        for (int i=HumanoidsNum;i<AliensNum+HumanoidsNum;i++){
            if (widthnum+CharacterButton.CONST+deltawidth>width) {
                widthnum = delta;
                heightnum += deltaheight + CharacterButton.TextCONST;
            }
            buttons[i]=new CharacterButton(widthnum,heightnum,buttonImage[i],name[i]);
            widthnum+=CharacterButton.CONST+deltawidth;

        }
        heightnum+=deltaTextheight+CharacterButton.TextCONST+deltaheight;
        textdroidsy = heightnum-deltaheight;
        widthnum=delta;

        //Создание кнопок дроидов
        for (int i=AliensNum+HumanoidsNum;i<DroidsNum+HumanoidsNum+AliensNum;i++){
            buttons[i]=new CharacterButton(widthnum,heightnum,buttonImage[i],name[i]);
            if (widthnum+CharacterButton.CONST+deltawidth>width) {
                widthnum = delta;
                heightnum += deltaheight + CharacterButton.TextCONST;
            }
            buttons[i]=new CharacterButton(widthnum,heightnum,buttonImage[i],name[i]);
            widthnum+=CharacterButton.CONST+deltawidth;

        }
        this.setLayout(null);
        //Добавление кнопок на древо
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
        g.fillRect(0,0,width,height);
        for (int i=0;i<buttons.length;i++){
            buttons[i].paint(g);
        }
        g.setColor(Color.green);
        g.drawString("Гуманоиды",delta,deltaTextheight-deltaheight);
        g.drawString("Чужие",delta,textaliensy);
        g.drawString("Дроиды",delta,textdroidsy);

    }
}
