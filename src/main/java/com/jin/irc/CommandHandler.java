package com.jin.irc;

import java.io.PrintWriter;
import javax.swing.JTabbedPane;


public class CommandHandler
{
    JTabbedPane channelTab;
    ChannelPane statusPane;
    CommandParser cp;
    PrintWriter pw;

    public CommandHandler(JTabbedPane channelTab)
    {
        this.channelTab = channelTab;
        statusPane = (ChannelPane)channelTab.getComponentAt(0);
        cp = new CommandParser();
    }

    public void setWriter(PrintWriter printwriter)
    {
        pw = printwriter;
    }

    public void processLine(String line)
    {
        Command command = cp.parse(line);
        if(command == null) {
             return;
        }
        else if(!command.isCommand()) {
            sendChatRoomMSG(command.getArg());
        }
        else {
            processCommand(command);
        }
    }

    void sendChatRoomMSG(String msg)
    {
        ChannelPane channelpane = (ChannelPane)channelTab.getSelectedComponent();
        String cName = channelpane.getChannelName();
        pw.println("PRIVMSG " + cName + " :" + msg);
        pw.flush();
        channelpane.addText("> " + msg + "\n");
    }

    public void processCommand(Command c)
    {
        String cmd = c.getCommand().toUpperCase();
        if(cmd.equals("JOIN"))  {
            joinChannel(c.getArg());
        } 
        else if(cmd.equals("LEAVE") || cmd.equals("PART")) {
            leaveChannel(c.getArg());
        }
        else if(cmd.equals("QUOTE")) {
            System.out.println("HELLO " + c.getArg());
            sendCommand(c.getArg());            
        }
    }

    public void sendCommand(String command)
    {
        pw.println(command);
        pw.flush();
    }

    public void leaveChannel(String cName)
    {
        pw.println("PART " + cName );
        pw.flush();  
    }

    public void joinChannel(String cName)
    {
        int size = channelTab.getTabCount();
        for(int i = 1; i < size; i++)  {
            ChannelPane cPane = (ChannelPane)channelTab.getComponentAt(i);
            if(cPane.getChannelName().equals(cName))  {
                channelTab.setSelectedComponent(cPane);
                return;
            }
        }

        pw.println("JOIN " + cName);
        pw.flush();
    }

}
