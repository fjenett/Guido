package de.bezier.gui2;

import processing.core.*;

public class GuiButton extends GuiButtonBase
{
    public GuiButton ( Object args )
    {
        super( args );
    }
    
    public void draw ( PApplet p )
    {
        style.drawRect( p, this, position.x, position.y, size.x, size.y );
        style.drawLabel( p, this, label, position.x, position.y, size.x, size.y );
    }
}