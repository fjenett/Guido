package de.bezier.gui2;

import java.awt.event.*;

public class GuiButtonBase extends GuiItem
{
    GuiButtonBase ( Object args )
    {
        super( args );
    }
    
    public void mousePressed ( MouseEvent evt )
    {
        GuiEvent gvt = new GuiEvent( this );
        for ( GuiListener l : listeners )
        {
            l.event( gvt );
            l.pressed( gvt );
        }
    }
    
    public void mouseReleased ( MouseEvent evt )
    {
        GuiEvent gvt = new GuiEvent( this );
        for ( GuiListener l : listeners )
        {
            l.event( gvt );
            l.released( gvt );
            l.bang( gvt );
        }
    }
}