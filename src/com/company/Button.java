package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Евросеть on 11.02.2017.
 */
public class Button extends JPanel{
    int x;
    int y;
    int k=0;/* для кнопок уже выбранных персонажей:
    mousePressed или mouseClicked  rect.contains(mouseX,mouseY)==false - k=0;
                                   rect.contains(mouseX,mouseY)==true - k=1
    mouseExited if (k!=1) - k=0
    mouseEntered if (k!=1) - k=-1
     */
    int width;
    int height;
    ButtonImage buttonImage;
    Rectangle rect;

    public Button(int x,int y,int width,int height,ButtonImage buttonImage){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.rect = new Rectangle(x,y,width,height);
        this.buttonImage=buttonImage;
    }

    public void paint(Graphics g){
        if (k==0){g.drawImage(buttonImage.getImage(),x,y,width,height,null);}
        if (k==1){g.drawImage(buttonImage.getImageClicked(),x,y,width,height,null);}
        if (k==-1){g.drawImage(buttonImage.getImageEntered(),x,y,width,height,null);}
    }

}
