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
    private Point lastDragPosition;
    private VideoField frameToResize;
    private Hero heroToResize;
    private int dragDirection;
    private int k=0;
    private int RESIZE_BORDER_SIZE=VideoField.border;
    private int NORTH =1;
    private int SOUTH=3;
    private int WEST=7;
    private int EAST=13;

    public ResizerPane(VideoField frame,Hero hero){
        frameToResize=frame;
        heroToResize=hero;
    }
    public void setK(int k){
        this.k=k;
    }
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
    public boolean contains(int x, int y) {
        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0)
            return false;

        return (getBorderSide(x, y) > 0);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastDragPosition = e.getLocationOnScreen();
        dragDirection = getBorderSide(e.getX(), e.getY());
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
        if (dragDirection == NORTH+EAST){
            width = currentBounds.width + deltaX;
            y = currentBounds.y + deltaY;
            height = currentBounds.height - deltaY;
        }
        if (dragDirection == NORTH+WEST){
            y = currentBounds.y + deltaY;
            height = currentBounds.height - deltaY;
            x = currentBounds.x + deltaX;
            width = currentBounds.width - deltaX;
        }
        if (dragDirection==SOUTH+EAST){
            height = currentBounds.height + deltaY;
            width = currentBounds.width + deltaX;
        }

        if (dragDirection == SOUTH+WEST){
            height = currentBounds.height + deltaY;
            x = currentBounds.x + deltaX;
            width = currentBounds.width - deltaX;
        }
        if (width>=VideoField.border*20&&height>=(VideoField.border*2+ menuborder)){
        frameToResize.setBounds(x, y, width, height);
        heroToResize.setBounds(VideoField.border,VideoField.border+VideoField.menuborder,width-VideoField.border*2,height-VideoField.border*2-VideoField.menuborder);}
        com.sun.awt.AWTUtilities.setWindowShape(frameToResize,frameToResize.setShape(frameToResize.screenpart));
        lastDragPosition = currentDragPosition;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int border = getBorderSide(e.getX(), e.getY());
        if (border == (SOUTH+EAST) || border == (NORTH+WEST)) {
            setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
            return;
        }

        if (border == (SOUTH+WEST) || border == (NORTH+EAST)) {
            setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
            return;
        }

        if (border == EAST || border == WEST) {
            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            return;
        }

        if (border == NORTH || border == SOUTH) {
            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            return;
        }
    }

}