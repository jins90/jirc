package com.jin.irc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.text.DefaultCaret;
import javax.swing.*;
import java.awt.*;

public class ChannelPane extends JPanel
{
    private String channelName;
    private JTextArea view;
    private JList<String> userList;
    private JScrollPane jspane;
    private DefaultListModel<String> listModel;
    private JScrollPane jspane2;

    public ChannelPane(String s)
    {
        setLayout(new BorderLayout());
        channelName = s;
        view = new JTextArea(30, 60);
        view.setEditable(false);
        DefaultCaret caret = (DefaultCaret)view.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        view.setFont(new Font("Courier New", Font.BOLD, 14)); 
        listModel = new DefaultListModel<String>();
        userList = new JList<String>(listModel);
        jspane = new JScrollPane(view);
        jspane.setHorizontalScrollBarPolicy(31);
        jspane2 = new JScrollPane(userList);
        jspane2.setPreferredSize(new Dimension(90, 60));
        add(jspane, "Center");
        add(jspane2, "East");
    }

    public void updateUsers(String users)
    {
        String ulist[] = users.split("\\s+");
        for(int i=0; i<ulist.length; i++) {
            listModel.addElement(ulist[i]);
        }
    }

    public void removeUser(String nickName)
    {
       listModel.removeElement(nickName);
    }

    public void addText(String s)
    {
        view.append(s);
    }

    public String getChannelName()
    {
        return channelName;
    }
}
