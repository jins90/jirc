package com.jin.irc;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;


public class CommandListener extends KeyAdapter
{
    private CommandHandler ch;
    private JTextField commandTextField;

    public CommandListener(CommandHandler ch, JTextField commandTextField)
    {
        this.ch = ch;
        this.commandTextField = commandTextField;
    }

    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(code == 10) {
            ch.processLine(commandTextField.getText());
            commandTextField.setText("");
        }
    }

}
