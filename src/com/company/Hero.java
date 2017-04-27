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
    int isFunk;
    VideoCapture camera;
    double angle =0;
    double newangle =0;
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

    public static BufferedImage rotate(BufferedImage image, double angle,int H_B) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        double rotationRequired = angle;
        double locationX = image.getWidth(null)/2;
        double locationY = image.getHeight(null)-H_B;
        double r=locationY-h/2;
        if (angle>=0){
            r=-r;
        }
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        tx.preConcatenate(new AffineTransform(1, 0, 0, 1, (neww-w)/2+r*sin, (newh-h)/2+r*(cos-1)));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(image, null),0,0,null);
        g.dispose();
        return result;
    }


    @Override
    public void run() {
        if (!camera.isOpened()) {
            System.out.println("Error");
        } else {
            Mat frame = new Mat();
            while (true) {
                if (isFunk==0){
                    camera.read(frame);
                    MatOfRect faceDetections = new MatOfRect();
                    faceDetector.detectMultiScale(frame, faceDetections);
                    for (Rect rect : faceDetections.toArray()) {
                        Point newPosition = new Point(rect.x+rect.width/2, rect.y+rect.height/2);
                        newangle = - Math.atan((newPosition.x-VideoField.center.x)/(VideoField.center.y-newPosition.y));
                        System.out.println(newangle);
                    }
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
