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
    NikdeFicrus(int screenpart) {
        super(screenpart);
        URL imgURL = NikdeFicrus.class.getResource("res/Nikhead.png");
        headImage = new ImageIcon(imgURL).getImage();
        checkImage  = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/Nikhead.png");
        notsayingImage = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/Nikheadasking.png");
        sayingImage = new ImageIcon(imgURL).getImage();
        imgURL = NikdeFicrus.class.getResource("res/Nikbody.png");
        bodyImage = new ImageIcon(imgURL).getImage();
        new Thread(this).start();
    }

    //Функция, рисующая кадр анимации с данным номером данной кистью в данных границах
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
    private int i = 0; // количество кадров с начала анимации

    @Override
    public void paint(Graphics g) {
        //Определение границ, в которых будет нарисован герой, в зависимости от режима рисования
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

        //если нужно выполнить функцию, рисуется следующий кадр анимации
        if (isFunk == 1) {
            Mainfunction(g, x, y, width, height, i);
            i++;
            if (i == 74) {
                isFunk = 0;
                i = 0;
            }
        }
        //Если функция не выполняется, рисуется персонаж
        if (isFunk == 0) {
            g.setColor(Color.white);
            g.fillRect(x, y, width, height);
            //Получение размеров головы и тела героя в зависимости от размеров области рисования и пропорций головы и тела
            int Sheight = height * 583 / 924;
            int Swidth = Sheight * 599 / 583;
            int HIheight = Sheight * 157 / 583;
            int HIwidth = (337 * HIheight / 412);
            int BIwidth = Swidth;
            int BIheight = BIwidth * 652 / 400;

            //высота шеи
            double  deltay = 924 / 3925;
            //смещение головы по горизонтали
            double  deltax=0;
            //Изначально голова персонажа поворачивается относительно середины по горизонтали и нижней точки по вертикали
            //Если голова не симметрична, координаты точки поворота смещаются на deltay вверх и deltax влево
            //рисование тела
            g.drawImage(bodyImage, x + width/2 - BIwidth / 2, y +height- BIheight, BIwidth, BIheight, null);
            //Получение повернутого изображения головы
            Image rotatedImage = rotate(Main.toBufferedImage(headImage),angle,deltay,deltax);
            deltay = height/25;
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
            //Рисование головы таким образом, чтобы точка поворота всегда оставалась неподвижной
            g.drawImage(rotatedImage, (int)(x + width*97 / 200+height*83*154/(924*103)-HIwidth+(w-neww)/2+x1-deltax), (int)(y + height*26/25- BIheight*275/400 - HIheight +(h-newh)/2+-y1+Math.sqrt(r*r-deltax*deltax)), neww,newh, null);
            //рисование стола
            URL imgURL = NikdeFicrus.class.getResource("res/stolv.png");
            Image stol = new ImageIcon(imgURL).getImage();
            g.drawImage(stol, x + width / 2 - Swidth / 2, y + height - Sheight, Swidth, Sheight, null);
        }
    }

}
