package com.company;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.net.URL;
import java.util.TimerTask;

/**
 * Created by Евросеть on 30.03.2017.
 */
public class NikdeFicrus extends Hero {
    NikdeFicrus(int screenpart, VideoCapture camera) {
        super(screenpart, camera);
        URL imgURL = NikdeFicrus.class.getResource("res/Nikhead.png");
        headImage = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/Nikbody.png");
        bodyImage = new ImageIcon(imgURL).getImage();
        new Thread(this).start();
    }

    public void Mainfunction(Graphics g, int x, int y, int width, int height, int i) {
        int Sheight = height * 583 / 924;
        int Swidth = Sheight * 599 / 583;
        int Nikwidth = Swidth;
        int Nikheight = Nikwidth * 652 / 400;
        URL imgURL = NikdeFicrus.class.getResource("res/stolv.png");
        Image stol = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/mainfunction/m" + (i + 1) + ".png");
        Image Nikimage = new ImageIcon(imgURL).getImage();
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        g.drawImage(Nikimage, x + width / 2 - Nikwidth / 2, y + height - Nikheight, Nikwidth, Nikheight, null);
        g.drawImage(stol, x + width / 2 - Swidth / 2, y + height - Sheight, Swidth, Sheight, null);
    }

    private long t = System.nanoTime();
    private int i = 0;

    @Override
    public void paint(Graphics g) {
        if (screenpart == 1) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle Herobounds = new Rectangle((int) dim.getWidth() / 6, (int) dim.getHeight() / 4);
            x = (int) (getWidth() - Herobounds.getWidth());
            y = (int) (getHeight() - Herobounds.getHeight());
            width = (int) Herobounds.getWidth();
            height = (int) Herobounds.getHeight();

        }
        if (screenpart == 0) {
            x = 0;
            y = 0;
            width = getWidth();
            height = getHeight();
        }


        if (isFunk == 1) {
            Mainfunction(g, x, y, width, height, i);
            i++;
            if (i == 74) {
                isFunk = 0;
                i = 0;
            }
        }
        if (isFunk == 0) {
            g.setColor(Color.white);
            g.fillRect(x, y, width, height);
            int Sheight = height * 583 / 924;
            int Swidth = Sheight * 599 / 583;
            int HIheight = Sheight * 157 / 583;
            int HIwidth = (337 * HIheight / 412);
            int BIwidth = Swidth;
            int BIheight = BIwidth * 652 / 400;

            int H_B = height / 25;
            //Point newPosition = new Point(20,20);
            //double angle = Math.atan((newPosition.x-VideoField.center.x)/(newPosition.y-VideoField.center.y));
            double angle = Math.toRadians(30);
            g.drawImage(bodyImage, x + width/2 - BIwidth / 2, y +height- BIheight, BIwidth, BIheight, null);
            Image rotatedImage = rotate(Main.toBufferedImage(headImage),angle,H_B);
            double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
            int w = HIwidth, h=HIheight;
            int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
            int r =HIheight/2-H_B;
            if (angle>=0){
                r=-r;
            }
            g.drawImage(rotatedImage, (int)(x + width*97 / 200+height*83*154/(924*103)-HIwidth+(w-neww)/2-r*sin), (int)(y + height- BIheight*275/400 - HIheight + H_B+(h-newh)/2+r*(1-cos)), neww,newh, null);
            URL imgURL = NikdeFicrus.class.getResource("res/stolv.png");
            Image stol = new ImageIcon(imgURL).getImage();
            g.drawImage(stol, x + width / 2 - Swidth / 2, y + height - Sheight, Swidth, Sheight, null);
        }
    }

}
