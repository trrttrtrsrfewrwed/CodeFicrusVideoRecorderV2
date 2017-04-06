package com.company;

import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;


/**
 * Created by Евросеть on 15.03.2017.
 */
public class VideoField extends JFrame {
    private int posX;
    private int posY;
    private int[] heroarr;
    private Hero hero;

    private int width = 1000;
    private int height = 649;
    public static int border = 5;
    public static int menuborder = 20;
    public int screenpart = 1;

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
                VideoField videoField = new VideoField();
                videoField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                videoField.setUndecorated(true);
                videoField.setLayout(new BorderLayout());
                videoField.setPreferredSize(new Dimension(videoField.width, videoField.height));
                videoField.hero = videoField.setHero(arr[0]);
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

                //добавляю возможность перемещать окно с помощью мыши
                videoField.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {

                        videoField.posX = e.getX();
                        videoField.posY = e.getY();


                    }
                });
                videoField.addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        int thisX = videoField.getLocation().x;
                        int thisY = videoField.getLocation().y;


                        int xMoved = e.getX() - videoField.posX;
                        int yMoved = e.getY() - videoField.posY;

                        int X = thisX + xMoved;
                        int Y = thisY + yMoved;
                        videoField.setLocation(X, Y);

                    }
                });
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
                        if (e.getKeyCode()== KeyEvent.VK_1){
                            videoField.hero = videoField.setHero(4);
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

    public Hero setHero(int hero) {
        switch (hero) {
            case 0:
                return new NikdeFicrus();
            case 1:
                return new Alien();
            case 2:
                return new IronCreature();
            case 3:
                return new Liften();
            case 4:
                return new Dragon();
            case 5:
                return new Droid();
        }
        return new Hero();
    }
}
