package com.jin.irc;

import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChannelTabListener implements ChangeListener
{
    private JTabbedPane channelTab;

    public ChannelTabListener(JTabbedPane jtabbedpane)
    {
        channelTab = jtabbedpane;
    }

    public void stateChanged(ChangeEvent changeevent)
    {
        int i = channelTab.getSelectedIndex();
        channelTab.setForegroundAt(i, Color.BLACK);
    }
}
