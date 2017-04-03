package com.company;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.TimerTask;

/**
 * Created by Евросеть on 30.03.2017.
 */
public class NikdeFicrus extends Hero  implements Runnable{
    NikdeFicrus(){
        screenpart=1;
        URL imgURL = NikdeFicrus.class.getResource("res/Nikhead.png");
        headImage = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/Nikbody.png");
        bodyImage = new ImageIcon(imgURL).getImage();
        new Thread(this).start();
    }

    public void Mainfunction(Graphics g,int x,int y,int width,int height,int i)  {
        int Sheight = height * 583 / 924;
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
            g.drawImage(stol, x + width / 2 - Swidth / 2, y + height - Sheight, Swidth, Sheight, null);
    }
    private long t = System.nanoTime();
    private int i=0;
    @Override
    public void paint(Graphics g){
        /*super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        long tm = System.nanoTime() - t;
        double angle = tm / 300000000.0;

        double sn = Math.sin(Math.sin(angle) * 0.5);
        double cs = Math.cos(Math.sin(angle) * 0.5);
        int x = (int) (250 * sn + 150);
        int y = (int) (250 * cs);
        if (isFunk==1){
            g2d.setColor(Color.green);
        }
        g2d.drawLine(150, 0, x, y);
        g2d.drawOval(x - 20, y - 20, 40, 40);*/
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
            int HIheight = Sheight * 157 / 583;
            int HIwidth = (337* HIheight / 412);
            int BIwidth=Swidth;
            int BIheight = BIwidth*652/400;

            int H_B = height / 25;
            g.drawImage(bodyImage, x + width/2 - BIwidth / 2, y +height- BIheight, BIwidth, BIheight, null);
            g.drawImage(headImage, x + width*97 / 200+height*83*154/(924*103)-HIwidth, y + height- BIheight*275/400 - HIheight + H_B, HIwidth, HIheight, null);
            URL imgURL = NikdeFicrus.class.getResource("res/stolv.png");
            Image stol = new ImageIcon(imgURL).getImage();
            g.drawImage(stol, x + width / 2 - Swidth / 2, y + height - Sheight, Swidth, Sheight, null);
        }
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(83);
            } catch (InterruptedException ex) {}
        }
    }
}
