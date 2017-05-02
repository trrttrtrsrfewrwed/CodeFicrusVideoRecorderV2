package com.company;


import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.URL;


public class Main extends JFrame{
    private Image []HeroImage;
    private Image basicImage;
    private Review review;
    private Drevo drevo;
    private HeroLine heroline;
    private int posX;
    private int posY;


    //Добавление слушателей древу, линейке персонажей и кнопкам
    public void init(){
        heroline.addMouseListener(new HeroLineMouseListener());
        drevo.addMouseListener(new DrevoMouseListener());
        for(int i=0;i<drevo.buttons.length;i++){
            drevo.buttons[i].addMouseListener(new DrevoButtonListener(drevo.buttons[i],i));
        }
        for (int i=0;i<heroline.buttons.length;i++){
            heroline.buttons[i].addMouseListener(new HeroLineButtonListener(heroline.buttons[i],i));
        }
    }
    //Создание формы по изображению
    static Shape contour(final BufferedImage i) {
        final int w = i.getWidth();
        final int h = i.getHeight();
        final Area s = new Area(new Rectangle(w, h));
        final Rectangle r = new Rectangle(0, 0, 1, 1);
        for (r.y = 0; r.y < h; r.y++) {
            for (r.x = 0; r.x < w; r.x++) {
                if ((i.getRGB(r.x, r.y) & 0xFF000000) != 0xFF000000) {
                    s.subtract(new Area( r ));
                }
            }
        }
        return s;
    }
    //Преобразование из Image в BufferedImage
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static void main( String[] args )
    {
        //создание и наполнение фрейма
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Создание древа, линейки персонажей и окна обзора персонажа
        frame.drevo = new Drevo();
        frame.heroline = new HeroLine();
        frame.review = new Review();
        frame.drevo.setPreferredSize(new Dimension(410,1000));
        //Создание полосы прокрутки для древа персонажей (Так как персонажей может быть слишком много)
        JScrollPane scrollPane = new JScrollPane(frame.drevo);
        //Горизонтальная полоса прокрутки не должна быть видна
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //устанавливаю форму фрейма
        frame.setUndecorated(true);
       /*URL imgURL = Main.class.getResource("menu.png");
        Image image = new ImageIcon(imgURL).getImage();
        BufferedImage img = toBufferedImage(image);
        Shape shape = contour(img);
        com.sun.awt.AWTUtilities.setWindowShape(frame, shape);*/

        //Добавление всех компонентов на панель
        frame.getContentPane().setLayout(new BorderLayout());
        Background panel = new Background();
        panel.setLayout(null);
        frame.heroline.setBounds(290,100,410, 80);
        panel.add(frame.heroline);
        frame.review.setBounds(650,150,253,400);
        panel.add(frame.review);
        scrollPane.setBounds(200,200,410,300);
        panel.add(scrollPane);
        //Создание кнопки закрыть
        Button close = new Button(0,0,20,20,new ButtonImage("res/closeimage.png","res/closeimageclicked.png","res/closeimageentered.png"));
        close.setBounds(825,65,close.width,close.height);
        close.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                close.k=1;
                frame.repaint();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                close.k=0;
                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                close.k=-1;
                frame.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                close.k=0;
                frame.repaint();
            }
        });
        panel.add(close);
        //Создание кнопки свернуть
        Button minimize = new Button(0,0,20,5,new ButtonImage("res/minimizeimage.png","res/minimizeimageclicked.png","res/minimizeimageentered.png"));
        minimize.setBounds(800,80,close.width,close.height);
        minimize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                minimize.k=1;
                frame.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                minimize.k=0;
                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                minimize.k=-1;
                frame.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimize.k=0;
                frame.repaint();
            }
        });
        panel.add(minimize);
        //Создание кнопки play, запускающей видеорекордер
        Button playbutton = new Button(0,0,80,92,new ButtonImage("res/playbutton.png","res/playbuttonclicked.png","res/playbuttonentered.png"));
        playbutton.setBounds(760,410,playbutton.width,playbutton.height);
        playbutton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                int []arr= new int[frame.heroline.buttons.length];
                for (int i=0;i<frame.heroline.buttons.length;i++){
                    arr[i]=frame.heroline.buttons[i].getI();
                }
                VideoField.main(arr);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                playbutton.k=1;
                frame.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                playbutton.k=0;
                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playbutton.k=-1;
                frame.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playbutton.k=0;
                frame.repaint();
            }
        });
        panel.add(playbutton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(1000, 649));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //  Добавляю изображения героев и базовое изображение, если ни один герой не выбран
        frame.HeroImage = new Image[6];
        URL imgURL = MyRect.class.getResource("res/Nikreview.png");
        frame.HeroImage[0]=new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource("res/alienreview.png");
        frame.HeroImage[1]=new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource("res/ironreview.png");
        frame.HeroImage[2]=new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource("res/liftenreview.png");
        frame.HeroImage[3]=new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource("res/dragonreview.png");
        frame.HeroImage[4]=new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource("res/droidreview.png");
        frame.HeroImage[5]=new ImageIcon(imgURL).getImage();
        imgURL = MyRect.class.getResource("res/basicimagereview.png");
        frame.basicImage=new ImageIcon(imgURL).getImage();
        frame.init();

        //Добавляю возможность перемещать окно с помощью мыши

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                frame.posX = e.getX();
                frame.posY = e.getY();


            }
        });




        frame.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {

                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                int xMoved =e.getX()-frame.posX;
                int yMoved =e.getY()-frame.posY;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });

    }

    //слушатель событий древа:
    //если клик произошёл в древе, но ни одна кнопка не нажата, делает все кнопки ненажатыми.
    private class DrevoMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            for (int i = 0; i < drevo.buttons.length; i++) {
                if (!drevo.buttons[i].rect.contains(x, y)) {
                    drevo.buttons[i].k = 0;
                }

            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    //слушатель событий линейки персонажей:
    //если клик произошёл в линейке, но ни одна кнопка не нажата, делает все кнопки ненажатыми.
    private class HeroLineMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            for (int i=0;i<heroline.buttons.length;i++){
                if (!heroline.buttons[i].rect.contains(x,y)){
                    heroline.buttons[i].k=0;
                }
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    //слушатель событий кнопок древа:
    //Меняет цвет кнопки при наведении мыши и нажатии, при нажатии перерисовывает героя (Review),
    //и присваивает выбранной ячейке линейки персонажей(Heroline) героя данной кнопки
    private class DrevoButtonListener implements MouseListener{
        int n;
        Button button;
        DrevoButtonListener(Button button,int n){
            this.n=n;
            this.button=button;
        }
        @Override
        public void mouseClicked(MouseEvent e) {

            for (int i=0;i<drevo.buttons.length;i++){
                if (i==n){
                    drevo.buttons[i].k=1;
                    review.setImage(HeroImage[i]);
                    for (int j = 0; j < heroline.buttons.length; j++) {
                        if (heroline.buttons[j].k == 1) {
                            heroline.buttons[j].setImage(drevo.buttons[i].getImage());
                            heroline.buttons[j].setI(i);
                            heroline.buttons[j].k = 1;
                        }
                    }
                }
                else {
                    drevo.buttons[i].k=0;
                }
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (button.k!=1){
                button.k=-1;
            }
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (button.k!=1){
                button.k=0;
            }
            repaint();
        }
    }
    //слушатель событий кнопок линейки персонажей:
    //Меняет цвет кнопки при наведении мыши и нажатии, при нажатии перерисовывает героя (Review),
    //и показывает, какому герою древа соответствует данная кнопка
    private class HeroLineButtonListener implements MouseListener{
        Button button;
        int n;
        HeroLineButtonListener(Button button,int n){
            this.button = button;
            this.n=n;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i=0;i<heroline.buttons.length;i++){
                if (i==n){
                    heroline.buttons[i].k=1;
                    if (heroline.buttons[i].getI()>=0){
                        for (int j=0;j<drevo.buttons.length;j++){
                            if (heroline.buttons[i].getI()==j){
                                drevo.buttons[j].k=1;
                            }
                            else {
                                drevo.buttons[j].k=0;
                            }
                        }
                        review.setImage(HeroImage[heroline.buttons[i].getI()]);
                    }
                    else { review.setImage(basicImage);
                    }

                }
                else {
                    heroline.buttons[i].k=0;
                }
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (button.k!=1){
                button.k=-1;
            }
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (button.k!=1){
                button.k=0;
            }
            repaint();
        }
    }
}
