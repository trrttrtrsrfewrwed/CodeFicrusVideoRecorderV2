package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Евросеть on 11.02.2017.
 */
public class Button extends JPanel{
    protected int x;
    protected int y;
    protected int k=0;/*
    для кнопок уже выбранных персонажей:
    mousePressed или mouseClicked  k=1
    mouseExited if (k!=1) - k=0
    mouseEntered if (k!=1) - k=-1
     */
    private int width;
    private int height;
    protected ButtonImage buttonImage;  //три изображения кнопки (нажатие, наведение, обычное состояние)
    private Rectangle rect;

    public Button(int x,int y,int width,int height,ButtonImage buttonImage){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.rect = new Rectangle(x,y,width,height);
        this.buttonImage=buttonImage;
    }
    //геттеры и сеттеры полей
    public int getbx(){
        return x;
    }
    public void setbx(int x){
        this.x=x;
    }
    public int getby(){
        return y;
    }
    public void setby(int y){
        this.y=y;
    }
    public int getbk(){
        return k;
    }
    public void setbk(int k){
        this.k=k;
    }
    public int getbwidth(){
        return width;
    }
    public void setbwidth(int width){
        this.width=width;
    }
    public int getbheight(){
        return height;
    }
    public void setbheight(int height){
        this.height=height;
    }
    public Rectangle getbrect(){
        return rect;
    }
    public void setbrect(Rectangle rect){
        this.rect=rect;
    }


    public void paint(Graphics g){
        if (k==0){g.drawImage(buttonImage.getImage(),x,y,width,height,null);}
        if (k==1){g.drawImage(buttonImage.getImageClicked(),x,y,width,height,null);}
        if (k==-1){g.drawImage(buttonImage.getImageEntered(),x,y,width,height,null);}
    }


}
