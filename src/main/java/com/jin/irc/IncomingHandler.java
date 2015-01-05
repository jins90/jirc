package com.jin.irc;

import java.io.PrintWriter;
import javax.swing.JTabbedPane;
import javax.swing.*;

public class IncomingHandler
{
    final private JTabbedPane channelTab;
    private PrintWriter pw;
    final private ChannelPane statusPane;
    private String nickName;

    public IncomingHandler(JTabbedPane channelTab, PrintWriter pw, String nickName)
    {
        this.channelTab = channelTab;
        this.pw = pw; 
        statusPane = (ChannelPane)channelTab.getComponentAt(0);
        this.nickName = nickName;
    }

    public void update(String line)
    {
        System.out.println(line);
        String prefix = "";
        String command = "";
        String params = "";
        String trailer = "";
        int len = line.length();
        int prefixEnd = -1;
        if(line.startsWith(":")) {
            prefixEnd = line.indexOf(" ");
            prefix = line.substring(1, prefixEnd);
        }
        int trailStart = line.indexOf(" :");
        if(trailStart >= 0) {
            trailer = line.substring(trailStart + 2, len);
        }
        else {
            trailStart = len;
        }
        String commandarg = line.substring(prefixEnd + 1, trailStart);
        String args[] = commandarg.split("\\s+");
        if(args[0].equals("PRIVMSG")) {
            privateMessage(prefix, args[1], trailer);
        } 
        else if(args[0].equals("PING")) {
            sendPong(trailer);
        }
        else if(args[0].equals("JOIN")) {
            String[] name = prefix.split("!");
            final String nick = name[0]; 
            if(nick.equals(nickName)) { 
               openChannel(trailer);
            }
            else {
               updateChannelUserList(trailer , nick, name[1]); 
            }
        }
        else if(args[0].equals("375") || args[0].equals("372") || args[0].equals("376"))  {
            updateStatusPane(trailer);
        }
        else if(args[0].equals("332")) {
            updateChannelPane(args[2], trailer);
        }
        else if(args[0].equals("TOPIC")) {
            updateChannelPane(args[1], trailer);
        }
        else if(args[0].equals("PART")) {
            int index = prefix.indexOf("!");
            String nick = prefix.substring(0, index);
            if(nick.equals(nickName)) {
               leaveChannel(args[1]);
            }
            else {
               removeUserFromChannel(args[1] , nick);
            }

        }
        else if(args[0].equals("353")) {
            updateChannelUserList(args[3].trim(), trailer, null);
        }
        else if(args[0].equals("NOTICE")) {
            updateStatusPane(line); 
        }
        else if(args[0].equals("QUIT")) {
           /* FILL THIS PORTOIN OUT */
            int index = prefix.indexOf("!");
            String nick = prefix.substring(0, index);
        }
    }

    public void removeUserFromChannel(final String cName, final String nick)
    {
        int size = channelTab.getTabCount();
        for(int i = 1; i < size; i++)  {
            final ChannelPane channelpane = (ChannelPane)channelTab.getComponentAt(i);
            if(channelpane.getChannelName().equals(cName)) {
               SwingUtilities.invokeLater(new Runnable() {
                  @Override
                  public void run() {
                      channelpane.addText("*** " + nick + " has left channel " + cName + "\n");
                      channelpane.removeUser(nick);
                  }
                });
                break;
            }
        }

    }

    public void updateChannelUserList(final String cName, final String users, final String login)
    {
        int size = channelTab.getTabCount();
        for(int i = 1; i < size; i++)  {
            final ChannelPane channelpane = (ChannelPane)channelTab.getComponentAt(i);
            if(channelpane.getChannelName().equals(cName)) {
               SwingUtilities.invokeLater(new Runnable() {
                  @Override
                  public void run() {
                      if(login != null) {
                          channelpane.addText("*** " + users + " (" + login + ") has joined channel " + cName + "\n");
                      }
                      channelpane.updateUsers(users);
                  }
                });
                break;
            }
        }

    }

    public void leaveChannel(String cName)
    {
        int size = channelTab.getTabCount();
        for(int i = 1; i < size; i++)  {
            ChannelPane channelpane = (ChannelPane)channelTab.getComponentAt(i);
            if(channelpane.getChannelName().equals(cName)) {
                channelTab.remove(i);       
                break;
            }
        }
        updateStatusPane("Leaving " + cName + "\n");
    }


    public void updateChannelPane(String cName, String trailer)
    {
        int size = channelTab.getTabCount();
        for(int i = 1; i < size; i++)  {
            ChannelPane channelpane = (ChannelPane)channelTab.getComponentAt(i);
            if(channelpane.getChannelName().equals(cName)) {
                channelpane.addText(trailer + "\n");
                return;
            }
        }

    }

    public void openChannel(String cName) 
    {
        ChannelPane cPane = new ChannelPane(cName);
        channelTab.add(cName, cPane);
        channelTab.setSelectedComponent(cPane);
        statusPane.addText("Join Channel " + cName + "\n");
    }

    public void sendPong(String trailer)
    {
        pw.println("PONG :" + trailer);
        pw.flush();
    }
  
    public void updateStatusPane(String msg)
    {
        statusPane.addText(msg + "\n");            
    }

    public void privateMessage(String from, String cName, String msg)
    {
        int size = channelTab.getTabCount();
        int end = from.indexOf("!");
        String nick = from.substring(0, end); 
        cName = cName.toUpperCase();
        for(int i = 1; i < size; i++)  {
            ChannelPane channelpane = (ChannelPane)channelTab.getComponentAt(i);
            if(channelpane.getChannelName().toUpperCase().equals(cName)) {
                channelpane.addText("<" + nick + "> "  + msg + "\n" );
                return;
            }
        }
        for(int i = 1; i < size; i++) {
            ChannelPane channelpane = (ChannelPane)channelTab.getComponentAt(i);
            if(channelpane.getChannelName().equals(nick)) {
                channelpane.addText("<" + nick + "> "  + msg + "\n" );
                return;
            }
        }     
        ChannelPane cPane = new ChannelPane(nick);
        channelTab.add(nick, cPane);
        channelTab.setSelectedComponent(cPane);
        cPane.addText("<" + nick + "> " + msg + "\n");
    }
}
