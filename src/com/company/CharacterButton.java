package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Евросеть on 11.02.2017.
 */
public class CharacterButton extends Button{
    public static final int CONST=40;
    public static final int TextCONST =60;
    private int i=-1;
    String name="";
    public CharacterButton(int x, int y, ButtonImage buttonImage,String name){
        super(x,y,CONST,TextCONST,buttonImage);
        this.name=name;
    }
    public void setI(int i){
        this.i=i;
    }
    public int getI(){
        return i;
    }
    public CharacterButton(int x, int y, ButtonImage buttonImage){
        super(x,y,CONST,CONST,buttonImage);
    }
    public void setImage(ButtonImage buttonImage){
        this.buttonImage=buttonImage;
    }
    public ButtonImage getImage(){return buttonImage;}
    public void paint(Graphics g){
        if (k==0){g.drawImage(buttonImage.getImage(),x,y,CONST,CONST,null);}
        if (k==1){g.drawImage(buttonImage.getImageClicked(),x,y,CONST,CONST,null);}
        if (k==-1){g.drawImage(buttonImage.getImageEntered(),x,y,CONST,CONST,null);}
        g.setColor(Color.black);
        if (!name.equals("")){
            g.drawString(name,x,y+TextCONST);
        }
    }




}
