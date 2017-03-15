package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Евросеть on 15.03.2017.
 */
public class VideoField extends JFrame {
    public static void main() {
        VideoField videoField = new VideoField();
        videoField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        videoField.setPreferredSize(new Dimension(1000, 649));
        videoField.pack();
        videoField.setLocationRelativeTo(null);
        videoField.setVisible(true);
    }
}
