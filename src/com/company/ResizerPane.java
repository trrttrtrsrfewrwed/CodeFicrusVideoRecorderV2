package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static com.company.VideoField.menuborder;


/**
 * Created by Евросеть on 15.03.2017.
 */
public class ResizerPane extends JPanel implements MouseMotionListener, MouseListener {
    //координаты мыши
    private int posX;
    private int posY;
    //точка, в которой мышь была нажата
    private Point lastDragPosition;
    //фрейм, герой, кнопки "закрыть" и "свернуть", положение и размеры которых нужно изменить
    private VideoField frameToResize;
    private Hero heroToResize;
    public Button close;
    public Button minimize;
    //Часть рамки, за которую осуществляется сдвиг или масштабирование
    private int dragDirection;
    //Режим видеорекордера
    /* 0 - ожидание
       1 - съемка    */
    private int k=0;
    //Ширина области, при нажатии на которую будет происходить масштабирование
    private int RESIZE_BORDER_SIZE=VideoField.border;

    //Переменные, однозначно характеризующие стороны рамки
    private final int NORTH =1;
    private final int SOUTH=3;
    private final int WEST=7;
    private final int EAST=13;

    public ResizerPane(VideoField frame,Hero hero,Button close,Button minimize){
        frameToResize=frame;
        heroToResize=hero;
        this.close=close;
        this.minimize=minimize;
    }
    public void setHero(Hero hero){
        heroToResize=hero;
    }
    public void setK(int k){
        this.k=k;
    }
    public int getK(){return k;}
    public void paintComponent(Graphics g){
        if (k==0){
            g.setColor(Color.GRAY);
        }
        else {
            g.setColor(Color.RED);
        }
        g.fillRect(0,0,VideoField.border, getHeight());
        g.fillRect(0, menuborder,getWidth(), VideoField.border);
        g.fillRect(getWidth()-VideoField.border, menuborder,VideoField.border, getHeight());
        g.fillRect(0,getHeight()-VideoField.border,getWidth(), VideoField.border);

        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), menuborder);
    }

    //Определяет, в какой стороне или в каком углу рамки находится точка с данными координатами
    private int getBorderSide(int x, int y) {
        int result = 0;

        if (x <= RESIZE_BORDER_SIZE)
            result += WEST;
        if (x >= getWidth() - RESIZE_BORDER_SIZE - 1)
            result += EAST;
        if (y <= RESIZE_BORDER_SIZE)
            result += NORTH;
        if (y >= getHeight() - RESIZE_BORDER_SIZE - 1)
            result += SOUTH;
        return result;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    //определяет положение мыши в начале сдвига
    @Override
    public void mousePressed(MouseEvent e) {
        lastDragPosition = e.getLocationOnScreen();
        dragDirection = getBorderSide(e.getX(), e.getY());

        posX = e.getX();
        posY = e.getY();
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

    //Определяет, на сколько была сдвинута мышь, и меняет размеры или положение фрейма в зависимости от начального положения мыши
    @Override
    public void mouseDragged(MouseEvent e) {
        Point currentDragPosition = e.getLocationOnScreen();
        int deltaX = currentDragPosition.x - lastDragPosition.x;
        int deltaY = currentDragPosition.y - lastDragPosition.y;
        Rectangle currentBounds = frameToResize.getBounds();

        if (deltaX == 0 && deltaY == 0)
            return;

        int x = currentBounds.x;
        int y = currentBounds.y;
        int width = currentBounds.width;
        int height = currentBounds.height;
        if (dragDirection == 0) {
            int thisX = frameToResize.getLocation().x;
            int thisY = frameToResize.getLocation().y;


            int xMoved = e.getX() - posX;
            int yMoved = e.getY() - posY;

            int X = thisX + xMoved;
            int Y = thisY + yMoved;
            if (k==0){
            frameToResize.setLocation(X, Y);}
        } else {
            if (dragDirection == WEST) {
                x = currentBounds.x + deltaX;
                width = currentBounds.width - deltaX;
            }

            if (dragDirection == EAST) {
                width = currentBounds.width + deltaX;
            }

            if (dragDirection == NORTH) {
                y = currentBounds.y + deltaY;
                height = currentBounds.height - deltaY;
            }

            if (dragDirection == SOUTH) {
                height = currentBounds.height + deltaY;
            }
            if (dragDirection == NORTH + EAST) {
                width = currentBounds.width + deltaX;
                y = currentBounds.y + deltaY;
                height = currentBounds.height - deltaY;
            }
            if (dragDirection == NORTH + WEST) {
                y = currentBounds.y + deltaY;
                height = currentBounds.height - deltaY;
                x = currentBounds.x + deltaX;
                width = currentBounds.width - deltaX;
            }
            if (dragDirection == SOUTH + EAST) {
                height = currentBounds.height + deltaY;
                width = currentBounds.width + deltaX;
            }

            if (dragDirection == SOUTH + WEST) {
                height = currentBounds.height + deltaY;
                x = currentBounds.x + deltaX;
                width = currentBounds.width - deltaX;
            }
            if (width >= VideoField.border * 20 && height >= (VideoField.border * 2 + menuborder) && k==0) {
                frameToResize.setBounds(x, y, width, height);
                frameToResize.setWidth(width);
                frameToResize.setHeight(height);
                close.setBounds(width - close.getbwidth() - 5, 5, close.getbwidth(), close.getbheight());
                minimize.setBounds(width - close.getbwidth() * 2 - 10, 12, close.getbwidth(), close.getbheight());
                heroToResize.setBounds(VideoField.border, VideoField.border + VideoField.menuborder, width - VideoField.border * 2, height - VideoField.border * 2 - VideoField.menuborder);
            }
            com.sun.awt.AWTUtilities.setWindowShape(frameToResize, frameToResize.setShape(frameToResize.getScreenpart()));
            lastDragPosition = currentDragPosition;
        }
    }

    //Изменяет изображение курсора в зависимости от положения над рамкой
    @Override
    public void mouseMoved(MouseEvent e) {
        int border = getBorderSide(e.getX(), e.getY());
        if (border == (SOUTH+EAST) || border == (NORTH+WEST)) {
            setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
            return;
        }
        else {
            if (border == (SOUTH + WEST) || border == (NORTH + EAST)) {
                setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                return;
            }
            else {
                if (border == EAST || border == WEST)  {
                    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    return;
                }
                else {
                    if (border == NORTH || border == SOUTH) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        return;
                    }
                    else {
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }
        }
    }

}