package com.chatapp.UI;

import com.chatapp.App;
import com.chatapp.baseClasses.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;


public class GUI extends JFrame {

    private App app;
    
    private JTextField nameField;
    private JTextField numberField;
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

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel numberLabel = new JLabel("Phone Number:");
        numberField = new JTextField();

        createButton = new JButton("Create profile");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(numberLabel);
        panel.add(numberField);
        panel.add(new JLabel(""));
        panel.add(createButton);

        add(panel);

        createButton.addActionListener(e -> createProfile());

    }

    private void createProfile() {
        String name = nameField.getText().trim();
        String numberText = numberField.getText().trim();

        if (name.isEmpty() || numberText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Fill in both fields.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int number = Integer.parseInt(numberText);
            app.initAccount(name, number);

            JOptionPane.showMessageDialog(this, "profile created successfuly!");

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "enter valid phone number."
                ,"Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initLandingPage(){
        JFrame mainFrame = new JFrame("Chat App");
        mainFrame.setSize(1280,720);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setVisible(true);

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Chat App");
        title.setHorizontalAlignment(JLabel.CENTER);

        JButton profileButton = new JButton("Profile");

        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(profileButton, BorderLayout.EAST);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        //feed
        DefaultListModel<Chat> feedModel = new DefaultListModel<>();
        JList<Chat> feedList = new JList<>(feedModel);
        JScrollPane feedScroll = new JScrollPane(feedList);
        feedScroll.setPreferredSize(new Dimension(400,0));
        mainFrame.add(feedScroll, BorderLayout.WEST);

        //adding more soon going to shop lol


    }
    public static void main(String[] args) {
        App app = new App();
        new GUI(app);
    }

    
    
    
}

class InnerGUI extends Thread {
    
    
}