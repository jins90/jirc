package com.jin.irc;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginPane extends JPanel
{
    private JTextField nickName;
    private JTextField login;
    private JTextField fullName;
    private JTextField server;
    private FileInputStream file;
    private LoginReader lr;

    public LoginPane()
    {
        lr = new LoginReader();
        setLayout(new GridLayout(0, 1));
        add(new JLabel("Server:"));
        server = new JTextField();
        server.setText(lr.getServer());
        add(server);
        add(new JLabel("Nick Name:"));
        nickName = new JTextField();
        nickName.setText(lr.getNickName());
        add(nickName);
        add(new JLabel("Login:"));
        login = new JTextField();
        login.setText(lr.getLogin());
        add(login);
        add(new JLabel("Full Name:"));
        fullName = new JTextField();
        fullName.setText(lr.getFullName());
        add(fullName);
    }

    public String getNickName()
    {
        return nickName.getText();
    }

    public String getLogin()
    {
        return login.getText();
    }

    public String getFullName()
    {
        return fullName.getText();
    }

    public String getServer()
    {
        return server.getText();
    }

}
