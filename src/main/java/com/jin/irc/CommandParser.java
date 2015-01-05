package com.jin.irc;

import java.io.PrintStream;

public class CommandParser
{

    public CommandParser()
    {
    }

    public Command parse(String line)
    {
        if( line == null || line.trim().equals("")) {
            return null;
        }
        int len = line.length();
        String c = "";
        String arg = "";
        Command command = new Command();

        if(line.charAt(0) == '/') {
            int cend = 0;
            int index = 1;
            while(index < len) {
               if(line.charAt(index) == ' ') {
                    cend = index;
                    break;
               }
               index++;
            } 
            if(cend == 0) {
                c = line.substring(1, len);
            } 
            else {
                c = line.substring(1, cend);
                arg = line.substring(cend + 1, len);
            }
            command.setCommand(c, arg);
        } 
        else {
            command.setTalk(line);
        }
        return command;
    }
}
