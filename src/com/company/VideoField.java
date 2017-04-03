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

    private int width=1000;
    private  int height=649;
    public static int border=5;
    public static int menuborder=20;
    public int screenpart=1;
    public  Shape setShape(int screenpart){
        Area s = new Area(new Rectangle(getWidth(),getHeight()));
        if (screenpart==0){
           return s;
        }
        else {
            Dimension dim =Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle Herobounds  = new Rectangle((int)dim.getWidth()/6,(int)dim.getHeight()/4);
            Rectangle r = new Rectangle(border,menuborder+border,getWidth()-border*2,(int)(getHeight()-border*2-menuborder-Herobounds.getHeight()));
            s.subtract(new Area(r));
            r= new Rectangle(border,menuborder+border,(int)(getWidth()-border*2-Herobounds.getWidth()),(int)(getHeight()-border*2-menuborder));
            s.subtract(new Area(r));
            return s;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VideoField videoField = new VideoField();
                videoField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                videoField.setUndecorated(true);
                videoField.setLayout(new BorderLayout());
                videoField.setPreferredSize(new Dimension(videoField.width, videoField.height));
                Hero hero =videoField.setHero(0);


                //добавление рамки
                ResizerPane  resize= new ResizerPane(videoField,hero);
                resize.setLayout(null);
                hero.setBounds(border,menuborder+border,videoField.width-border*2,videoField.height-border*2-menuborder);
                resize.add(hero);
                videoField.getContentPane().add(resize,BorderLayout.CENTER);
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
                com.sun.awt.AWTUtilities.setWindowShape(videoField,videoField.setShape(videoField.screenpart));
                videoField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode()==KeyEvent.VK_1){
                            hero.isFunk=1;
                        }
                        if (e.getKeyCode()==KeyEvent.VK_SPACE){
                            if (videoField.screenpart==0){
                                videoField.screenpart=1;
                                hero.setScreenpart(videoField.screenpart);
                                com.sun.awt.AWTUtilities.setWindowShape(videoField,videoField.setShape(videoField.screenpart));
                                videoField.repaint();
                            }
                            else {
                                videoField.screenpart=0;
                                hero.setScreenpart(videoField.screenpart);
                                com.sun.awt.AWTUtilities.setWindowShape(videoField,videoField.setShape(videoField.screenpart));
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
    public Hero setHero(int hero){
        if (hero==0){
            return new NikdeFicrus();
        }
        else {
            return new Hero();
        }
    }
}
