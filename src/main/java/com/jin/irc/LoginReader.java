package com.jin.irc;

import java.io.*;

public class LoginReader
{
    private FileInputStream file;
    private String server = "";
    private String nickName = "";
    private String fullName = "";
    private String login = "";

    public LoginReader()
    {   
        try {
           file = new FileInputStream(".login");
           BufferedReader b = new BufferedReader(new InputStreamReader(file));
           server = b.readLine();
           nickName = b.readLine();
           login = b.readLine();
           fullName = b.readLine();

        }
        catch(IOException ie) {
  
        }
    } 

    public String getServer()
    {
        return server;
    }

    public String getNickName()
    {
        return nickName;
    }

    public String getLogin()
    {
        return login;
    }

    public String getFullName()
    {
        return fullName;
    }
}
