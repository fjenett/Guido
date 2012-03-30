package de.bezier.gui2;

import processing.core.*;
import java.awt.event.*;

public class GuiSlider extends GuiItem
{
    final static int HORIZONTAL = 1, VERTICAL = 2;
    int orientation = HORIZONTAL;
	
    float minimum=0f, maximum=1f, current=0.5f;
	Float value;
    
    public GuiSlider ( Object args )
    {
        super( args );
		value = new Float(current);
    }

	public void postSet ()
	{
		super.postSet();
		if ( value != null )
		{
			value = current;
		}
	}
    
    public GuiSlider setMinMax ( float mi, float ma )
    {
        minimum = mi; maximum = ma;
        return this;
    }

    public GuiSlider setValue ( float de )
    {
		if ( value != null )
			value = de;
        return this;
    }
    
    public GuiSlider setOrientation ( int ori )
    {
        orientation = ori == HORIZONTAL ? HORIZONTAL : VERTICAL;
        return this;
    }
    
    public float value ()
    {
        return value.floatValue();
    }
    
    public float valueNormalized ()
    {
        return papplet.map( value.floatValue(), minimum, maximum, 0, 1 );
    }
	
	protected float calcMousedValue ( MouseEvent evt )
	{
		float nValue = 0;
		if ( orientation == HORIZONTAL )
        {	
			nValue = pixelCoordToValue( evt.getX() );
 		}
		else
		{
			nValue = pixelCoordToValue( evt.getY() );
		}
		return nValue;
	}
	
	protected float pixelCoordToValue ( int px )
	{
		float nValue = Float.NaN;
		if ( orientation == HORIZONTAL )
        {
            nValue = papplet.map( px, position.x + style.itemPadding[style.LEFT],
                 position.x + size.x - style.itemPadding[style.RIGHT], 
                 minimum, maximum );
        }
        else
        {
            nValue = papplet.map( px, position.y + style.itemPadding[style.TOP],
                 position.y + size.y - style.itemPadding[style.BOTTOM], 
                 minimum, maximum );
        }
		nValue = papplet.constrain(nValue,minimum,maximum);
		return nValue;
	}
    
    public void mouseDraggedRaw ( MouseEvent evt )
    {
        if ( isInside(evt) && isActive )
		{
			mouseDragged(evt);
		}
    }
    
    public void mouseDragged ( MouseEvent evt )
    {
        float nValue = calcMousedValue(evt);
        value = papplet.constrain(nValue, minimum, maximum);
		
		GuiEvent gvt = new GuiEvent( this );
        for ( GuiListener l : listeners )
        {
            l.event( gvt );
            l.changed( gvt );
        }
    }

	public void mousePressedRaw ( MouseEvent evt )
    {	
		super.mousePressedRaw(evt);
		
	    if ( isVisible && isInside(evt) && isActive && hover )
		{
	        GuiEvent gvt = new GuiEvent( this );
	        for ( GuiListener l : listeners )
	        {
	            l.event( gvt );
	            l.changed( gvt );
	        }
		}
    }
    
    public void mouseReleasedRaw ( MouseEvent evt )
    {	
		super.mouseReleasedRaw(evt);
		
		if ( isVisible && isInside(evt) && isActive )
		{
	        GuiEvent gvt = new GuiEvent( this );
	        for ( GuiListener l : listeners )
	        {
	            l.event( gvt );
	            l.changed( gvt );
	        }
		}
    }
    
    public void draw ( PApplet p )
    {
        style.drawRect( p, this, position.x, position.y, size.x, size.y );
        style.drawLabel( p, this, label, position.x, position.y, size.x, size.y, style.ALIGN_LEFT );
		
		if ( value != null )
		{
	        style.drawLabel( p, this, value.toString(), position.x, position.y, size.x, size.y, style.ALIGN_RIGHT );
	        drawHandle( p, minimum, value.floatValue(), maximum );
		}
    }

	protected void drawHandle ( PApplet p, float vMin, float vNow, float vMax )
	{
		p.pushStyle();
        p.stroke(style.labelColor);
        p.strokeWeight(1);
        if ( orientation == HORIZONTAL )
        {
            int pl = style.itemPadding[style.LEFT];
            int pr = style.itemPadding[style.RIGHT];
            float x = papplet.map( vNow, vMin, vMax, position.x+pl, position.x+size.x-pr );
            p.line( x, position.y+style.itemPadding[style.TOP], x, position.y+size.y-style.itemPadding[style.BOTTOM] );
        }
        else
        {
            int pt = style.itemPadding[style.TOP];
            int pb = style.itemPadding[style.BOTTOM];
            float y = papplet.map( vNow, vMin, vMax, position.y+pt, position.y+size.y-pb );
            p.line( position.x+style.itemPadding[style.LEFT], y, position.x+size.x-style.itemPadding[style.RIGHT], y );
        }
        p.popStyle();
	}
}