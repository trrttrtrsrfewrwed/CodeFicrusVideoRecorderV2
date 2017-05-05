package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Евросеть on 22.03.2017.
 */
public class Hero extends JPanel implements Runnable{
    //координаты и размер прямоугольника с героем
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    //Режим рисования героя
    protected int screenpart;
    //Изображение головы героя (рисуется)
    protected Image headImage;
    //Изображение тела героя (рисуется)
    protected Image bodyImage;
    //2 изображения головы героя: когда он говорит и когда не говорит
    protected Image sayingImage;
    protected Image notsayingImage;
    //Изображение головы, которое согласуется с микрофоном
    protected Image checkImage;
    //Рисуется headImage, в переменных sayingImage и notsayingImage хранятся изображения головы, которые присваиваются headImage
    //checkImage служит для того, чтобы слушатель микрофона не менял размер головы героя, пока она не отрисуется.

    //Есть ли функция у героя и какая
    protected int isFunk;
    //Угол, на которы повернута голова
    protected double angle=0;
    //Угол, на который наклонен пользователь
    protected double newangle=0;

    //геттеры и сеттеры полей
    public Image getSayingImage(){
        return sayingImage;
    }
    public Image getNotSayingImage(){
        return notsayingImage;
    }
    public void setCheckImage(Image checkImage){
        this.checkImage=checkImage;
    }
    public void setFunk(int currentFunk){
        isFunk = currentFunk;
    }
    public void setAngle(double angle){
        newangle = angle;
    }
    public Hero(int screenpart){
        this.screenpart=screenpart;
    }

    public Hero(){
        screenpart=1;
    }
    public void setScreenpart(int screenpart){
        this.screenpart=screenpart;
    }
    public void paint(Graphics g){
        if (screenpart==1){
            Dimension dim =Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle Herobounds  = new Rectangle((int)dim.getWidth()/6,(int)dim.getHeight()/4);
            x=(int)(getWidth()-Herobounds.getWidth());
            y=(int)(getHeight()-Herobounds.getHeight());
            width=(int)Herobounds.getWidth();
            height=(int)Herobounds.getHeight();

        }
        if (screenpart==0){
            x=0;
            y=0;
            width=getWidth();
            height=getHeight();
        }
        g.setColor(Color.cyan);
        g.fillRect(x,y,width,height);
}

    //поворот картинки на заданный угол относительно данной точки (Как находится точка поворота, сказано в классе NikdeFicrus)
    public static BufferedImage rotate(BufferedImage image, double angle,double deltay,double deltax) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        deltax=w*deltax;
        deltay=h*deltay;
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        double rotationRequired = angle;
        double locationX = w/2-deltax;
        double locationY = h-deltay;
        double r=Math.sqrt((locationY-h/2)*(locationY-h/2)+(deltax)*(deltax));
        sin = Math.sin(angle);
        cos=Math.cos(angle);
        double x = sin*Math.sqrt(r*r-deltax*deltax)+deltax*cos;
        double y =Math.sqrt(r*r-x*x);
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        tx.preConcatenate(new AffineTransform(1, 0, 0, 1, (neww-w)/2-x+deltax, (newh-h)/2+y-Math.sqrt(r*r-deltax*deltax)));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(image, null),0,0,null);
        g.dispose();
        return result;
    }

    //Функция, которая каждый 83 миллисекунды перерисовывает героя
    @Override
    public void run() {
        while (true) {
            headImage =checkImage;
            repaint();
            try {
                Thread.sleep(83);
            } catch (InterruptedException ex) {
            }
        }
    }

        }

