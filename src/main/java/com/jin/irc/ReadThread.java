package com.jin.irc;

public class ReadThread extends Thread
{
    private IRCClient ircClient;
    private IncomingHandler ih;

    public ReadThread(IRCClient ircclient, IncomingHandler incominghandler)
    {
        this.ircClient = ircclient;
        this.ih = incominghandler;
    }

    public void run()
    {
        while(true) {
            String s = ircClient.readLine();
            ih.update(s);
        } 
    }
}
