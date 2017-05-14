package com.company;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Area;
import java.io.IOException;


/**
 * Created by Евросеть on 15.03.2017.
 */
public class VideoField extends JFrame {
    private Hero hero;
    //размеры экрана
    private int width = 1000;
    private int height = 649;
    //ширина рамки видеорекордера
    public static int border = 5;
    //высота меню с кнопками над рамкой видеорекордера
    public static int menuborder = 20;
    /*режим рисования героя:
    0 - во весь экран
    1 - в правом нижнем углу
    -1 - не рисовать героя
     */
    private int screenpart = 1;
    //Точка, относительно которой считается вращение центра лица с камеры
    public static Point center;
    //Камера, следящая за движением
    private VideoCapture camera;
    //Поток, обрабатывающий информацию с камеры
    private CameraThread cThread = new CameraThread();
    //Поток, обрабатывающий информацию с микрофона
    private MicrophoneThread mThread = new MicrophoneThread();

    private VideoRecorder videorecorder = new VideoRecorder();

    public int getScreenpart(){
        return screenpart;
    }
    //задание формы фрейму по режиму рисования героя
    public Shape setShape(int screenpart) {
        Area s = new Area(new Rectangle(getWidth(), getHeight()));
        //Если герой во весь экран, фрейм представляет собой прямоугольную рамку, в которой нарисован герой
        if (screenpart == 0) {
            return s;
        } else {
            //Если герой не рисуется, фрейм представляет собой пустую внутри рамку
            if (screenpart==-1) {
                Rectangle r = new Rectangle(border,menuborder+border,getWidth()-border*2,getHeight()-border*2-menuborder);
                s.subtract(new Area(r));
                return s;
            }
            else{
                //Если герой рисуется в правом нижнем углу, фрейм представляет собой пустую внутри рамку, в правом нижнем углу
                //которой нарисован герой
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle Herobounds = new Rectangle((int) dim.getWidth() / 6, (int) dim.getHeight() / 4);
            Rectangle r = new Rectangle(border, menuborder + border, getWidth() - border * 2, (int) (getHeight() - border * 2 - menuborder - Herobounds.getHeight()));
            s.subtract(new Area(r));
            r = new Rectangle(border, menuborder + border, (int) (getWidth() - border * 2 - Herobounds.getWidth()), (int) (getHeight() - border * 2 - menuborder));
            s.subtract(new Area(r));
            return s;}
        }
    }

    public static void main(int[] arr) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VideoField videoField = new VideoField();
                //создание камеры
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                videoField.camera = new VideoCapture(0);
                videoField.camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 1280);
                videoField.camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 720);

                videoField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //делаем фрейм недекорированным (Убираем обрамление)
                videoField.setUndecorated(true);
                videoField.setLayout(new BorderLayout());
                videoField.setPreferredSize(new Dimension(videoField.width, videoField.height));
                //делаем фрейм всегда поверх других окон
                videoField.setAlwaysOnTop(true);
                //Создание первого героя, соответствующего первому герою линейки персонажей меню
                videoField.hero = videoField.setHero(arr[0]);
                VideoField.center = new Point(300, 400);
                //запуск потоков, отвечающих за микрофон и камеру
                videoField.cThread.start();
                videoField.mThread.start();
                //создаем кнопки "закрыть" и "свернуть" и добавляем к ним слушатели
                Button close = new Button(0, 0, 10, 10, new ButtonImage("res/closeimage.png", "res/closeimageclicked.png", "res/closeimageentered.png"));
                close.setBounds(1000 - close.getbwidth() - 5, 5, close.getbwidth(), close.getbheight());
                Button minimize = new Button(0, 0, 10, 3, new ButtonImage("res/minimizeimage.png", "res/minimizeimageclicked.png", "res/minimizeimageentered.png"));
                minimize.setBounds(1000 - close.getbwidth() * 2 - 10, 12, close.getbwidth(), close.getbheight());
                close.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            videoField.videorecorder.stopRecording();
                        } catch(Exception e3) {
                        }
                        System.exit(0);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        close.setbk(1);
                        videoField.repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        close.setbk( 0);
                        videoField.repaint();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        close.setbk(-1);
                        videoField.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        close.setbk(0);
                        videoField.repaint();
                    }
                });
                minimize.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        videoField.setState(JFrame.ICONIFIED);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        minimize.setbk(1);
                        videoField.repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        minimize.setbk( 0);
                        videoField.repaint();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        minimize.setbk(-1);
                        videoField.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        minimize.setbk(0);
                        videoField.repaint();
                    }
                });

                //добавление рамки, позволяющей менять размеры записываемой области, если запись не идет
                ResizerPane resize = new ResizerPane(videoField, videoField.hero, close, minimize);

                //создание кнопки старт/стоп, запускающей или останавливающей запись видео
                Button play = new Button(0, 0, 10, 10, new ButtonImage("res/startstopimage.png", "res/startstopimageclicked.png", "res/startstopimageentered.png"));
                play.setBounds(5,5,play.getbwidth(),play.getbheight());
                play.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try{
                            if (resize.getK()==0){
                            Rectangle rect = new Rectangle(videoField.getX()+border,videoField.getY()+menuborder+border,videoField.getWidth()-border*2,videoField.getHeight()-border*2-menuborder);
                            videoField.videorecorder.startRecording(rect);
                            resize.setK(1);
                            play.setbk(1);}
                            else {
                                videoField.videorecorder.stopRecording();
                                resize.setK(0);
                                play.setbk(-1);
                            }
                        } catch (Exception e2) {
                            System.out.println(e2);
                        }
                        videoField.repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        play.setbk(1);
                        videoField.repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (play.getbk()!=1){
                            play.setbk(-1);
                        }
                        videoField.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (play.getbk()!=1){
                            play.setbk(0);
                        }
                        videoField.repaint();
                    }
                });
                //добавление всех компонентов на рамку и на фрейм
                resize.setLayout(null);
                videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                resize.add(videoField.hero);
                resize.add(close);
                resize.add(minimize);
                resize.add(play);
                videoField.getContentPane().add(resize, BorderLayout.CENTER);
                resize.addMouseListener(resize);
                resize.addMouseMotionListener(resize);

                videoField.pack();
                videoField.setLocationRelativeTo(null);
                videoField.setVisible(true);

                //присваиваем фрейму начальную форму
                com.sun.awt.AWTUtilities.setWindowShape(videoField, videoField.setShape(videoField.screenpart));

                //добавляем слушатель клавиатуры:
                /*
                  пробел - смена режима рисования героя (полный экран <-> правый нижний угол)
                  1,2,3,4... - смена героя (устанавливается герой, стоявший в линейке персонажей меню под данным номером)
                  ENTER и др. - рисование сложных действий героя (попить чай и т. д.)
                 */
                videoField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            videoField.hero.setFunk(1);
                        }
                        if (e.getKeyCode() == KeyEvent.VK_1) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[0]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_2) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[1]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_3) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[2]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_4) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[3]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_5) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[4]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_6) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[5]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_7) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[6]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_8) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[7]);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }

                        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                            if (videoField.screenpart == 0) {
                                videoField.screenpart = 1;
                                videoField.hero.setScreenpart(videoField.screenpart);
                                com.sun.awt.AWTUtilities.setWindowShape(videoField, videoField.setShape(videoField.screenpart));
                                videoField.repaint();
                            }
                            else {
                                if (videoField.screenpart == 1) {
                                    videoField.screenpart = 0;
                                    videoField.hero.setScreenpart(videoField.screenpart);
                                    com.sun.awt.AWTUtilities.setWindowShape(videoField, videoField.setShape(videoField.screenpart));
                                    videoField.repaint();
                                }
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });


            }

        });


    }
    //функция, возвращающая героя, который был под данным номером в древе персонажей.
    //Если под данным номером персонажа не было (-1), функция ставит режим "не рисовать героя"
    private Hero setHero(int hero) {
        switch (hero) {
            case -1:
                screenpart=-1;
                com.sun.awt.AWTUtilities.setWindowShape(this, this.setShape(screenpart));
                return new Hero();
            case 0:
                if (screenpart==-1){
                    screenpart=1;
                    com.sun.awt.AWTUtilities.setWindowShape(this, this.setShape(screenpart));
                }
                return new NikdeFicrus(screenpart);
            case 1:
                if (screenpart==-1){
                    screenpart=1;
                    com.sun.awt.AWTUtilities.setWindowShape(this, this.setShape(screenpart));
                }
                return new Alien(screenpart);
            case 2:
                if (screenpart==-1){
                    screenpart=1;
                    com.sun.awt.AWTUtilities.setWindowShape(this, this.setShape(screenpart));
                }
                return new IronCreature(screenpart);
            case 3:
                if (screenpart==-1){
                    screenpart=1;
                    com.sun.awt.AWTUtilities.setWindowShape(this, this.setShape(screenpart));
                }
                return new Liften(screenpart);
            case 4:
                if (screenpart==-1){
                    screenpart=1;
                    com.sun.awt.AWTUtilities.setWindowShape(this, this.setShape(screenpart));
                }
                return new Dragon(screenpart);
            case 5:
                if (screenpart==-1){
                    screenpart=1;
                    com.sun.awt.AWTUtilities.setWindowShape(this, this.setShape(screenpart));
                }
                return new Droid(screenpart);
        }
        return new Hero(screenpart);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    //Обработчик событий микрофона:
    //Если звук превышает пороговое значение, герой открывает рот (Если не выполняется функция)
    private class MicrophoneThread extends Thread {
        @Override
        public void run(){
            try {
                AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
                TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
                targetDataLine.open(audioFormat);
                AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
                byte[] buffer = new byte[4];
                targetDataLine.start();
                while (audioInputStream.read(buffer) > 0) {
                    try {
                        this.sleep(30);
                    }catch(Exception e){}
                    if (hero.isFunk==0){
                        int value = ((buffer[0] & 0xff) | (buffer[1] << 8)) << 16 >> 16;
                        if (value > 1000) {
                            hero.setCheckImage(hero.getSayingImage());
                        } else {
                            hero.setCheckImage(hero.getNotSayingImage());
                        }
                    }
                }
                targetDataLine.stop();
                targetDataLine.close();
            } catch(Exception e){
                System.out.println(e);
            }
        }
    }
    //Обработчик событий камеры:
    //Находит угол, на который наклонился пользователь и поворачивает голову героя на данный угол
    private  class CameraThread extends Thread  {
        @Override
        public void run() {

            if (!camera.isOpened()) {
                System.out.println("Error");
            }
            else {
                Mat frame = new Mat();
                CascadeClassifier faceDetector = new CascadeClassifier("lbpcascade_frontalface.xml");
                while (true) {
                    try {
                        this.sleep(30);
                    }catch(Exception e){}
                    if (hero.isFunk == 0) {
                        MatOfRect faceDetections = new MatOfRect();
                        if (camera.read(frame)) {
                            faceDetector.detectMultiScale(frame, faceDetections);
                            Rect[] arr = faceDetections.toArray();
                            if (arr.length == 1) {
                                for (Rect rect : arr) {
                                    Point newPosition = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
                                    try {
                                        hero.setAngle(-Math.atan((newPosition.x - VideoField.center.x) / (VideoField.center.y - newPosition.y)));
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
    }
}