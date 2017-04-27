package com.company;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;


/**
 * Created by Евросеть on 15.03.2017.
 */
public class VideoField extends JFrame {
    private Hero hero;
    private int width = 1000;
    private int height = 649;
    public static int border = 5;
    public static int menuborder = 20;
    public int screenpart = 1;
    public static Point center; //!!!!!!!!!!!!!!!!!!

    public VideoField() {
    }

    public Shape setShape(int screenpart) {
        Area s = new Area(new Rectangle(getWidth(), getHeight()));
        if (screenpart == 0) {
            return s;
        } else {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle Herobounds = new Rectangle((int) dim.getWidth() / 6, (int) dim.getHeight() / 4);
            Rectangle r = new Rectangle(border, menuborder + border, getWidth() - border * 2, (int) (getHeight() - border * 2 - menuborder - Herobounds.getHeight()));
            s.subtract(new Area(r));
            r = new Rectangle(border, menuborder + border, (int) (getWidth() - border * 2 - Herobounds.getWidth()), (int) (getHeight() - border * 2 - menuborder));
            s.subtract(new Area(r));
            return s;
        }
    }

    public static void main(int[] arr) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                VideoCapture camera = new VideoCapture(0);
                camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 1280);
                camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 720);

                VideoField videoField = new VideoField();
                videoField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                videoField.setUndecorated(true);
                videoField.setLayout(new BorderLayout());
                videoField.setPreferredSize(new Dimension(videoField.width, videoField.height));
                videoField.hero = videoField.setHero(arr[0],camera);
                VideoField.center=new Point(250,400); //!!!!!!!!!!!!!!тут вообще вопрос, должно зависеть только от размеров получаемого с камеры изображения
                Button close = new Button(0, 0, 10, 10, new ButtonImage("res/closeimage.png", "res/closeimageclicked.png", "res/closeimageentered.png"));
                close.setBounds(1000 - close.width - 5, 5, close.width, close.height);
                Button minimize = new Button(0, 0, 10, 3, new ButtonImage("res/minimizeimage.png", "res/minimizeimageclicked.png", "res/minimizeimageentered.png"));
                minimize.setBounds(1000 - close.width * 2 - 10, 12, close.width, close.height);
                close.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.exit(0);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        close.k = 1;
                        videoField.repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        close.k = 0;
                        videoField.repaint();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        close.k = -1;
                        videoField.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        close.k = 0;
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
                        minimize.k = 1;
                        videoField.repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        minimize.k = 0;
                        videoField.repaint();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        minimize.k = -1;
                        videoField.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        minimize.k = 0;
                        videoField.repaint();
                    }
                });

                //добавление рамки
                ResizerPane resize = new ResizerPane(videoField,videoField.hero, close, minimize);
                resize.setLayout(null);
                videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                resize.add(videoField.hero);
                resize.add(close);
                resize.add(minimize);
                videoField.getContentPane().add(resize, BorderLayout.CENTER);
                resize.addMouseListener(resize);
                resize.addMouseMotionListener(resize);

                videoField.pack();
                videoField.setLocationRelativeTo(null);
                videoField.setVisible(true);

                com.sun.awt.AWTUtilities.setWindowShape(videoField, videoField.setShape(videoField.screenpart));
                videoField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            videoField.hero.isFunk = 1;
                        }
                        if (e.getKeyCode()==KeyEvent.VK_1) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[0],camera);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode()==KeyEvent.VK_2) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[1],camera);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode()==KeyEvent.VK_3) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[2],camera);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode()==KeyEvent.VK_4) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[3],camera);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode()==KeyEvent.VK_5) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[4],camera);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode()==KeyEvent.VK_6) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[5],camera);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode()==KeyEvent.VK_7) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[6],camera);
                            videoField.hero.setBounds(border, menuborder + border, videoField.width - border * 2, videoField.height - border * 2 - menuborder);
                            resize.add(videoField.hero);
                            resize.setHero(videoField.hero);
                            resize.repaint();
                            videoField.revalidate();
                        }
                        if (e.getKeyCode()==KeyEvent.VK_8) {
                            resize.remove(videoField.hero);
                            videoField.hero = videoField.setHero(arr[7],camera);
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
                            } else {
                                videoField.screenpart = 0;
                                videoField.hero.setScreenpart(videoField.screenpart);
                                com.sun.awt.AWTUtilities.setWindowShape(videoField, videoField.setShape(videoField.screenpart));
                                videoField.repaint();
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

    public Hero setHero(int hero, VideoCapture camera) {
        switch (hero) {
            case 0:
                return new NikdeFicrus(screenpart,camera);
            case 1:
                return new Alien(screenpart,camera);
            case 2:
                return new IronCreature(screenpart,camera);
            case 3:
                return new Liften(screenpart,camera);
            case 4:
                return new Dragon(screenpart,camera);
            case 5:
                return new Droid(screenpart,camera);
        }
        return new Hero(screenpart,camera);
    }
    public void setWidth(int width){
        this.width=width;
    }
    public void setHeight(int height){
        this.height=height;
    }

}
