package com.company;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Евросеть on 22.03.2017.
 */
public class Hero extends JPanel implements Runnable{
    int x;
    int y;
    int width;
    int height;
    int screenpart;
    Image headImage;
    Image bodyImage;
    Image sayingImage;
    Image notsayingImage;
    int isFunk;
    double angle=0;
    double newangle=0;

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


    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(83);
            } catch (InterruptedException ex) {
            }
        }
    }

        }

