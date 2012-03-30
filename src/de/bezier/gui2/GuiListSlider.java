package de.bezier.gui2;

import processing.core.*;
import java.awt.event.*;

public class GuiListSlider extends GuiSlider
{
    GuiListSlider ( Object args )
    {
        super( args );
    }
    
    public void draw ( PApplet p ) {}
    
    public void drawSlider ( PApplet p ) 
    {
        style.drawRect( p, this, position.x, position.y, size.x, size.y );
        float y = papplet.map( value.floatValue(), minimum, maximum, position.y, position.y+size.y-size.x );
        style.drawRect( p, null, position.x, y, size.x, size.x );
    }
}