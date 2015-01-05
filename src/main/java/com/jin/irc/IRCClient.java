package com.jin.irc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class IRCClient
{
    private Socket s;
    private BufferedReader b;
    private PrintWriter pw;
    private String host;
    private final int port = 6667;
    private String nickName;
    private String login;
    private String name;

    public IRCClient(String host, String nickName, String login, String name)
    {
        this.host = host;
        this.nickName = nickName;
        this.login = login;
        this.name = name;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public PrintWriter getWriter()
    {
        return pw;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }

    public String readLine()
    {
        String line = null;
        try  {
            line = b.readLine();
        }
        catch(IOException ie)  {
            System.out.println("Unable to read line " + ie);
        }
        return line;
    }

    public String connect()
    {
        System.out.println("Connecting");
        try {
            s = new Socket(host, 6667);
            b = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream(), true);
        }
        catch(UnknownHostException ue) {
            System.out.println("Following Host Unknown " + ue);
            return "Unable to Connect to Server";
        }
        catch(IOException ie) {
            System.out.println("Error connecting to " + ie);
            return "Unable to Connect to Server";
        }
        System.out.println("OK");
        pw.println("NICK " + nickName);
        pw.println("USER " + login + " 8 * : " + name);
        pw.flush();
       
        return "OK";
    }
}
