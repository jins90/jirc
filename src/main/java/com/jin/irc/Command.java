package com.jin.irc;


public class Command
{
    private String command;
    private String arg;
    private boolean isCommand;

    public Command()
    {
        isCommand = false;
    }

    public void setCommand(String command, String arg)
    {
        isCommand = true;
        this.command = command;
        this.arg = arg;
    }

    public void setTalk(String arg)
    {
        this.arg = arg;
    }

    public String getCommand()
    {
        return command;
    }

    public String getArg()
    {
        return arg;
    }

    public boolean isCommand()
    {
        return isCommand;
    }

}
