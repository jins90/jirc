package com.jin.irc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.io.PrintStream;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class JIRC extends JFrame
{
    private java.util.List channels;
    private JTabbedPane channelTab;
    private ChannelPane statusPane;
    private JTextField command;
    private CommandListener cl;
    private CommandHandler ch;
    private ChannelTabListener ctl;
    private IRCClient client;

    public JIRC()
    {
        super("jIRC");
        channels = new LinkedList();
        channelTab = new JTabbedPane();
        ctl = new ChannelTabListener(channelTab);
        channelTab.addChangeListener(ctl);
        statusPane = new ChannelPane("Status");
        channelTab.addTab("Status", statusPane);
        command = new JTextField();
        command.setFont(new Font("Courier New", Font.BOLD, 14));
        ch = new CommandHandler(channelTab);
        cl = new CommandListener(ch, command);
        command.addKeyListener(cl);
        getContentPane().add("Center", channelTab);
        getContentPane().add("South", command);
    }

    public boolean connect()
    {
        LoginPane lp = new LoginPane();
        int result = JOptionPane.showConfirmDialog(this, lp, "Login",
            JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);      
        while(result != JOptionPane.OK_OPTION) {
            result = JOptionPane.showConfirmDialog(this, lp, "Login",
              JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }
        String server = lp.getServer();
        String nickName = lp.getNickName();
        String fullName = lp.getFullName();
        String login = lp.getLogin();
        client = new IRCClient(server, nickName, login, fullName);
        String r = client.connect();
        if(r.equals("OK")) {
            ch.setWriter(client.getWriter());
            return true;
        }
        else {
            System.out.println(r);
            return false;
        }
    }

    public void addChannel(String s)
    {
        ChannelPane channelpane = new ChannelPane(s);
        channelTab.add(s, channelpane);
    }

    public void startRead()
    {
        ReadThread readthread = new ReadThread(client, new IncomingHandler(channelTab, client.getWriter(), client.getNickName()));
        readthread.start();
    }

    public static void main(String args[])
    {
        JIRC jirc = new JIRC();
        jirc.pack();
        jirc.setVisible(true);
        jirc.setDefaultCloseOperation(2);
        while(!jirc.connect());
        jirc.startRead();
    }

}
