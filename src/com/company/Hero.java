package com.company;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

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
    int isFunk;
    VideoCapture camera;
    CascadeClassifier faceDetector = new CascadeClassifier("lbpcascade_frontalface.xml");
    public Hero(int screenpart,VideoCapture camera){
        this.screenpart=screenpart;
        this.camera = camera;
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

    protected AffineTransformOp rotate(double angle){
        double rotationRequired = angle;
        double locationX = headImage.getWidth(null)/2;
        double locationY = headImage.getHeight(null)/2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op;
    }

    @Override
    public void run() {
        if (!camera.isOpened()) {
            System.out.println("Error");
        } else {
            int index = 0;
            Mat frame = new Mat();
            while (true) {
               if (isFunk==0){
                        MatOfRect faceDetections = new MatOfRect();
                        faceDetector.detectMultiScale(frame, faceDetections);
                        for (Rect rect : faceDetections.toArray()) {}
                }
                repaint();
                try {
                    Thread.sleep(83);
                } catch (InterruptedException ex) {
                }
            }

        }
    }
}
