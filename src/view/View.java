package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author Anésio Sousa
 */
public class View extends JFrame{
    public static void main(String[] args) {
        CriarEExibirInterface();
    }
    
    public static void CriarEExibirInterface(){
        JFrame frame = new JFrame("HelloWorld");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        JPanel painel = new JPanel();
        addComponentesAoPainel(painel);
        
        frame.setContentPane(painel);
        
        frame.setSize(600, 500);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void addComponentesAoPainel(Container painel) {
        painel.setLayout(new FlowLayout());
        JLabel label1 = new JLabel("Tell me something");
        
        JButton botao = new JButton("Vídeo aula");
        painel.add(botao);
        
        painel.add(label1);

    }
}
