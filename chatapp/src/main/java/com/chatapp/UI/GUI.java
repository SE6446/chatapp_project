package com.chatapp.UI;

import com.chatapp.App;
import com.chatapp.baseClasses.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.ArrayList;


public class GUI extends JFrame {

    private App app;
    
    private JTextField nameField;
    private JTextField numberField;
    private JButton createButton;
    private JPanel centerPanel;
    private CardLayout cardLayout;
    private JLabel handleDisplay;
    private DefaultListModel<Chat> feedModel;
    private JTextArea chatArea;
    private JTextField messageField;
    private Chat currentChat;

    public GUI(App app, boolean needsProfileCreation) {
        this.app = app;

        setTitle("Chat App");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (needsProfileCreation) {
            initComponents();
        } else {
            initLandingPage();
        }

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

            JOptionPane.showMessageDialog(this, "Profile created successfully!");
            initLandingPage();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "enter valid phone number."
                ,"Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private JPanel createProfileView(){
        JPanel outerPanel = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel(new GridLayout(4,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(app.getProfile().getHandle());

        JLabel numberLabel = new JLabel("Phone Number:");
        numberField = new JTextField(String.valueOf(app.getProfile().getphoneNumber()));

        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");

        nameField.setPreferredSize(new Dimension(150,20));
        numberField.setPreferredSize(new Dimension(150,20));
        saveButton.setPreferredSize(new Dimension(150,20));
        backButton.setPreferredSize(new Dimension(150,20));

        backButton.addActionListener(e -> {
            cardLayout.show(centerPanel, "DEFAULT");
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        saveButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String newPhoneNumber = numberField.getText().trim();

            if (newName.isEmpty() || newPhoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(panel,"Fields cannot be empty","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int newNumber = Integer.parseInt(newPhoneNumber);

                app.editProfile(newName, newNumber);
                handleDisplay.setText(app.getProfile().getHandle());

                JOptionPane.showMessageDialog(panel, "Profile Updated!");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid phone number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(numberLabel);
        panel.add(numberField);
        panel.add(saveButton);
        panel.add(backButton);
        outerPanel.add(panel);

        return outerPanel;
    }

    private JPanel createNewGroupChatView(){
        JPanel outerPanel = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        DefaultListModel<String> selectModel = new DefaultListModel<>();
        JList<String> selectList = new JList<>(selectModel);
        JScrollPane selectScroll = new JScrollPane(selectList);
        selectScroll.setPreferredSize(new Dimension(200,150));

        JComboBox<String> contactDropDown = new JComboBox<>();

        for (Contact c: app.getAllContacts()) {
            contactDropDown.addItem(c.getHandle());
        }

        JButton addButton = new JButton("Add");

        JPanel selectPanel = new JPanel();
        selectPanel.add(contactDropDown);
        selectPanel.add(addButton);

        addButton.addActionListener(e -> {
            String selected = (String) contactDropDown.getSelectedItem();

            if (selected != null && !selectModel.contains(selected)) {
                selectModel.addElement(selected);
            }
        });

        selectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectModel.removeElement(selectList.getSelectedValue());
            }
        });

        JButton createButton = new JButton("Create Group");
        JButton backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton);
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            cardLayout.show(centerPanel, "DEFAULT");
        });

        createButton.addActionListener(e -> {
            ArrayList<Contact> members = new ArrayList<>();
            for (int i = 0; i < selectModel.size(); i++) {
                String name = selectModel.get(i);

                for (Contact c: app.getAllContacts()) {
                    if (c.getHandle().equals(name)) {
                        members.add(c);
                        break;
                    }
                }
            }

            app.createGroupChat(members);
            JOptionPane.showMessageDialog(panel,"Group Created!");
            refreshFeed();
            cardLayout.show(centerPanel, "DEFAULT");
        });

        panel.add(top,BorderLayout.NORTH);
        panel.add(selectPanel,BorderLayout.CENTER);
        panel.add(selectScroll,BorderLayout.EAST);
        panel.add(bottomPanel,BorderLayout.SOUTH);
        outerPanel.add(panel);

        return outerPanel;
    }

    // its duplicate code idc im too tired to deal with it
    private JPanel createNewContactView(){
        JPanel outerPanel = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel(new GridLayout(4,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel numberLabel = new JLabel("Phone Number:");
        numberField = new JTextField();

        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");

        nameField.setPreferredSize(new Dimension(150,20));
        numberField.setPreferredSize(new Dimension(150,20));
        saveButton.setPreferredSize(new Dimension(150,20));
        backButton.setPreferredSize(new Dimension(150,20));

        backButton.addActionListener(e -> {
            cardLayout.show(centerPanel, "DEFAULT");
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        saveButton.addActionListener(e -> {
            if(nameField.getText().isEmpty() || numberField.getText().isEmpty()){
                JOptionPane.showMessageDialog(panel,"Fields cannot be empty","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                int newContactNumber =  Integer.parseInt(numberField.getText());
                String newContactName =  nameField.getText();
                app.addContact(newContactName, newContactNumber);

                refreshFeed();
                JOptionPane.showMessageDialog(panel,"Contact Added!");
                cardLayout.show(centerPanel, "DEFAULT");

            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(panel,"Invalid phone number","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(numberLabel);
        panel.add(numberField);
        panel.add(saveButton);
        panel.add(backButton);
        outerPanel.add(panel);

        return outerPanel;
    }

    private void refreshFeed() {
        feedModel.clear();

        Stack<Chat> feed = (Stack<Chat>) app.getFeed().clone();

        if (feed.isEmpty()) {
            return;
        }

        int limit = Math.min(feed.size(), 3);

        for (int i = 0; i < limit; i++) {
            feedModel.addElement(feed.pop());
        }
    }

    private void openChatView(Chat chat) {
        currentChat = chat;

        JPanel chatPanel = new JPanel(new BorderLayout());

        JLabel chatTitle = new JLabel(chat.getOtherDisplayName(app.getProfile()), JLabel.CENTER);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        refreshChatArea();

        JScrollPane chatScroll = new JScrollPane(chatArea);

        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        JButton backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        backButton.addActionListener(e -> {
            cardLayout.show(centerPanel, "DEFAULT");
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        sendButton.addActionListener(e -> sendCurrentMessage());
        messageField.addActionListener(e -> sendCurrentMessage());

        chatPanel.add(chatTitle, BorderLayout.NORTH);
        chatPanel.add(chatScroll, BorderLayout.CENTER);
        chatPanel.add(bottomPanel, BorderLayout.SOUTH);

        centerPanel.add(chatPanel, "CHAT");
        cardLayout.show(centerPanel, "CHAT");
        centerPanel.revalidate();
        centerPanel.repaint();
    }

        
    private void sendCurrentMessage() {
        if (currentChat == null) {
            return;
        }

        String text = messageField.getText().trim();

        if (text.isEmpty()) {
            return;
        }

        Message message = new Message(text, app.getProfile());
        currentChat.sendMessage(message);
        messageField.setText("");

        refreshChatArea();
        refreshFeed();
    }

    private void refreshChatArea() {
        if (currentChat == null || chatArea == null) {
            return;
        }

        chatArea.setText(currentChat.toString());
    }

    private void initLandingPage(){
        cardLayout = new CardLayout();
        getContentPane().removeAll();
        setTitle("Chat App");
        setSize(1280, 720);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(cardLayout);

        JLabel title = new JLabel("Chat App");
        title.setHorizontalAlignment(JLabel.CENTER);

        handleDisplay = new JLabel(app.getProfile().getHandle());

        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> {
            System.out.println("Profile button pressed");
            cardLayout.show(centerPanel, "PROFILE");
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(handleDisplay, BorderLayout.WEST);
        topPanel.add(profileButton, BorderLayout.EAST);


        add(topPanel, BorderLayout.NORTH);

        //feed
        feedModel = new DefaultListModel<>();
        JList<Chat> feedList = new JList<>(feedModel);
        JScrollPane feedScroll = new JScrollPane(feedList);
        feedScroll.setPreferredSize(new Dimension(400,0));

        feedList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
        JLabel label = new JLabel();

        if (value == null) {
            label.setText("Unknown chat");
        } else {
            label.setText(value.getOtherDisplayName(app.getProfile()));
        }

        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            label.setOpaque(true);
            label.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }

        feedList.addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            Chat selectedChat = feedList.getSelectedValue();

        if (selectedChat != null) {
            openChatView(selectedChat);
            }
        }
        });

        return label;
        });

        JPanel feedPanel = new JPanel(new BorderLayout());
        JPanel topFeedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addContactButton = new JButton("Add Contact");
        addContactButton.addActionListener(e -> {
            cardLayout.show(centerPanel, "NEW CONTACT");
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        JButton addGroupChatButton = new JButton("New GroupChat");
        addGroupChatButton.addActionListener(e -> {
            centerPanel.add(createNewGroupChatView(), "NEW GROUP CHAT");
            cardLayout.show(centerPanel, "NEW GROUP CHAT");
        });


        topFeedPanel.add(addContactButton);
        topFeedPanel.add(addGroupChatButton);

        feedPanel.add(topFeedPanel, BorderLayout.NORTH);
        feedPanel.add(feedScroll, BorderLayout.CENTER);
        feedPanel.setPreferredSize(new Dimension(400,0));

        add(feedPanel, BorderLayout.WEST);

        //add chats to feed
        refreshFeed();

        //views/ view switching
        //central area on the landing page will switch between panels using the buttons, using the cardLayout
        //this whole class looks like glasgow central because of me but at least this is in one place

        JPanel defaultPanel = new JPanel(new BorderLayout());
        defaultPanel.add(new JLabel("Select a Chat to Start.", JLabel.CENTER), BorderLayout.CENTER);

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(new JLabel("Chat view.", JLabel.CENTER), BorderLayout.CENTER);

        JPanel newContactPanel = createNewContactView();

        JPanel newGroupChatPanel = createNewGroupChatView();

        JPanel profilePanel = createProfileView();

        centerPanel.add(defaultPanel, "DEFAULT");
        centerPanel.add(chatPanel, "CHAT");
        centerPanel.add(profilePanel, "PROFILE");
        centerPanel.add(newContactPanel, "NEW CONTACT");
        centerPanel.add(newGroupChatPanel, "NEW GROUP CHAT");

        add(centerPanel, BorderLayout.CENTER);

        cardLayout.show(centerPanel, "DEFAULT");

        setVisible(true);
    }

    public static void main(String[] args) {
        App app;
        boolean needsProfileCreation;

        if (args.length == 0) {
            app = new App(); 
            needsProfileCreation = true;
        } else if (args.length == 1) {
            app = new App(args[0]);
            needsProfileCreation = false;
        } else {
            System.out.println("Invalid arguments. Usage: java GUI [optional: saveFileName]");
            return;
        }
        new GUI(app, needsProfileCreation);
    }   

    
    
    
}

class InnerGUI extends Thread {
    
    
}