package view;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Anésio Sousa
 */
public class View {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HelloWorld");
        frame.pack();
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Hello world!", JLabel.CENTER);
        frame.getContentPane().add(label);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
