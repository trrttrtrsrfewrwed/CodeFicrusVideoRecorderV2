package com.company;

import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.TimerTask;

/**
 * Created by Евросеть on 30.03.2017.
 */
public class Dragon extends Hero {
    //пояснения см. в классе NikdeFicrus
    Dragon(int screenpart){
        super(screenpart);
        URL imgURL = NikdeFicrus.class.getResource("res/dragonhead.png");
        headImage = new ImageIcon(imgURL).getImage();
        checkImage  = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/dragonhead.png");
        notsayingImage = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/dragonheadasking.png");
        sayingImage = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/dragonbody.png");
        bodyImage = new ImageIcon(imgURL).getImage();
        new Thread(this).start();
    }

    public void Mainfunction(Graphics g,int x,int y,int width,int height,int i)  {
        /*int Sheight = height * 583 / 924;
        int Swidth = Sheight * 599 / 583;
        int Nikwidth=Swidth;
        int Nikheight = Nikwidth*652/400;
        URL imgURL = NikdeFicrus.class.getResource("res/stolv.png");
        Image stol = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/mainfunction/m" + (i + 1) + ".png");
        Image Nikimage = new ImageIcon(imgURL).getImage();
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        g.drawImage(Nikimage, x + width / 2 - Nikwidth / 2, y + height - Nikheight, Nikwidth, Nikheight, null);
        g.drawImage(stol, x + width / 2 - Swidth / 2, y + height - Sheight, Swidth, Sheight, null);*/
    }
    private long t = System.nanoTime();
    private int i=0;
    @Override
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


        if (isFunk==1) {
            Mainfunction(g, x, y, width, height,i);
            i++;
            if (i==74){
                isFunk=0;
                i=0;
            }
        }
        if (isFunk==0) {
            g.setColor(Color.white);
            g.fillRect(x, y, width, height);
            int Sheight = height * 583 / 924;
            int Swidth = Sheight * 599 / 583;
            int BIwidth=Swidth;
            int BIheight = BIwidth*555/400;
            int HIwidth = BIheight * 114 / 555;
            int HIheight = (headImage.getHeight(null)* HIwidth / headImage.getWidth(null));

            double  deltay = 0;
            double deltax =0;
            g.drawImage(bodyImage, x + width/2 - BIwidth / 2, y +height- BIheight, BIwidth, BIheight, null);
            Image rotatedImage = rotate(Main.toBufferedImage(headImage),angle,deltay,deltax);
            double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
            int w = HIwidth, h=HIheight;
            int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
            double r =Math.sqrt((HIheight/2-deltay)*(HIheight/2-deltay)+deltax*deltax);

            //п/36
            double nanoAngle = Math.PI/72;
            if (angle-newangle>nanoAngle){
                angle-=nanoAngle;
            }
            if (newangle-angle>nanoAngle){
                angle+=nanoAngle;
            }
            sin = Math.sin(angle);
            cos = Math.cos(angle);
            double x1 = sin*Math.sqrt(r*r-deltax*deltax)+deltax*cos;
            double y1 =Math.sqrt(r*r-x1*x1);
            g.drawImage(rotatedImage, (int)(x + width/2-HIwidth*4/10+(w-neww)/2+x1-deltax), (int)(y + height- BIheight+(h-newh)/2-y1+Math.sqrt(r*r-deltax*deltax)) , neww, newh, null);
            URL imgURL = NikdeFicrus.class.getResource("res/stolv.png");
            Image stol = new ImageIcon(imgURL).getImage();
            g.drawImage(stol, x + width / 2 - Swidth / 2, y + height - Sheight, Swidth, Sheight, null);
        }
    }
}
