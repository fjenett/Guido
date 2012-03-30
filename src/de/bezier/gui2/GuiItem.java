package de.bezier.gui2;

import java.awt.event.*;
import processing.core.*;

public class GuiItem extends GuiBase
{
    PVector position = new PVector(0,0);
    PVector size = new PVector(0,0);
    
    GuiListener[] listeners = new GuiListener[0];
    
	boolean isDisabled = true;
    boolean isActive = true;   // focus?
    boolean isVisible = true;
    boolean hover = false;
	boolean autoRender = true;
    
    public String label = "";
    
    GuiItem ( Object args )
    {
        super( args );
    }
    
    public GuiItem setLabel ( String label )
    {
        this.label = label;
        return this;
    }

	public PVector size ()
	{
		return size;
	}
    
    public GuiItem setSize ( float w, float h )
    {
        size.x = w; size.y = h;
        return this;
    }

	public PVector position ()
	{
		return position;
	}
   
    public GuiItem setPosition ( float x, float y )
    {
        position.x = x; position.y = y;
        return this;
    }
    
    public GuiItem atRightEdgeOf ( GuiItem other )
    {
        setPosition( other.position.x+other.size.x+style.itemMargin[style.RIGHT], position.y );
        return this;
    }
    
    public GuiItem alignToTopOf ( GuiItem other )
    {
        setPosition( position.x, other.position.y );
        return this;
    }
    
    public void mouseEnteredRaw ( MouseEvent evt ) {}
    public void mouseExitedRaw ( MouseEvent evt ) {}
    
    public void mouseMovedRaw ( MouseEvent evt )
    {
        if ( !isVisible ) return;
        
        if ( isActive )
        {
            boolean inside = isInside( evt.getX(), evt.getY() );
            if ( inside && !hover )
                mouseEntered( evt );
            else if ( hover && !inside )
                mouseExited( evt );
            hover = inside;
        }
        
        mouseMoved( evt );
    }
    
    public void mousePressedRaw ( MouseEvent evt )
    {
        if ( isVisible && isInside(evt) && isActive && hover ) mousePressed( evt );
    }
    
    public void mouseReleasedRaw ( MouseEvent evt )
    {
        if ( isVisible && isInside(evt) && isActive ) mouseReleased( evt );
    }
    
    public GuiItem hide ()
    {
        isVisible = false;
        return this;
    }
    
    public GuiItem show ()
    {
        isVisible = true;
        return this;
    }
    
    public GuiItem activate ()
    {
        isActive = true;
        return this;
    }
    
    public GuiItem deactivate ()
    {
        isActive = false;
        return this;
    }

    public void draw ()
    {
        draw( autoRender );
    }

	public void draw ( boolean mode )
	{
		if ( mode )
		{
			if ( !isVisible ) return;
	        else draw( papplet );
		}
	}
    
    public void draw ( PApplet papplet ) {}

	public boolean getAutoRender ()
	{
		return autoRender;
	}
	
	public void setAutoRender ( boolean b )
	{
		autoRender = b;
	}
    
    public boolean isInside ( MouseEvent evt )
    {
        return isInside( evt.getX(), evt.getY() );
    }
    
    public boolean isInside ( float px, float py )
    {
        return ( px > position.x && px < position.x + size.x &&
                 py > position.y && py < position.y + size.y );
    }

	public GuiItem addListener ( String methName )
	{
		return addListener( papplet, methName );
	}
	
	public GuiItem addListener ( Object instance, String methName )
	{
		GuiListener listener = new GuiReflectionListener( instance, methName );
		return addListener( listener );
	}

	public GuiItem addListener ( String triggerOn, String methName )
	{
		return addListener( papplet, triggerOn, methName );
	}

	public GuiItem addListener ( Object instance, String triggerOn, String methName )
	{
		GuiListener listener = new GuiReflectionListener( instance, triggerOn, methName );
		return addListener( listener );
	}
    
    public GuiItem addListener ( GuiListener listener )
    {
        if ( listeners == null ) {
            listeners = new GuiListener[]{listener};
            return this;
        }
        GuiListener[] tmp = new GuiListener[listeners.length+1];
        System.arraycopy( listeners, 0, tmp, 0, listeners.length );
        tmp[tmp.length-1] = listener;
        listeners = tmp;
        return this;
    }
    
    public GuiItem removeListener ( GuiListener listener )
    {
        if ( listeners == null || listeners.length == 0 ) {
            return this;
        }
        int m = -1;
        GuiListener[] tmp = new GuiListener[listeners.length-1];
        for ( int i = 0, k = listeners.length, n = 0; i < k; i++ )
        {
            if ( listeners[i] == listener ) m = i;
            else if ( n < tmp.length )
            {
                tmp[n] = listeners[i];
                n++;
            }
        }
        if ( m == -1 ) return this;
        listeners = tmp;
        return this;
    }
}