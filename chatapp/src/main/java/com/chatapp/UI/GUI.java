package com.chatapp.UI;

import com.chatapp.App;
import com.chatapp.baseClasses.*;
import javax.swing.*;
import java.awt.*;


public class GUI extends JFrame {

    private App app;
    
    private JTextField nameField;
    private JTextField NumberField;
    private JButton createButton;

    public GUI(App app) {
        this.app = app;

        setTitle("Chat App");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    
    
    
}

class InnerGUI extends Thread {
    
    
}