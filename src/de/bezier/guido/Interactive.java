package de.bezier.guido;

import processing.core.*;
import java.util.*;
import java.awt.event.*;
import java.lang.reflect.*;

public class Interactive 
implements MouseWheelListener
{
	ArrayList<AbstractActiveElement> interActiveElements;
	static Interactive manager;
	PApplet papplet;
	
	private Interactive ( PApplet papplet )
	{
		this.papplet = papplet;
		
		papplet.registerMouseEvent( this );

		if ( papplet.frame != null )
		{
			papplet.frame.addMouseWheelListener(this);
		}
		
		papplet.registerDraw( this );
	}
	
	public static Interactive make ( PApplet papplet )
	{
		if ( manager == null )
			manager = new Interactive( papplet );
		return manager;
	}
	
	public static Interactive get ()
	{
		if ( manager == null )
		{
			System.err.println( "You need to initialize me first with ...\n\n\tInteractive.make(this);\n" );
		}
		return manager;
	}
	
	public static void setActive ( Object o, boolean tf )
	{
		if ( manager != null )
		{
			for ( AbstractActiveElement interActiveElement : manager.interActiveElements )
			{
				if ( interActiveElement.getClass().equals( ReflectiveActiveElement.class ) )
				{
					if ( ((ReflectiveActiveElement)interActiveElement).listener == o )
					{
						interActiveElement.setActive(tf);
					}
				}
				else if ( interActiveElement == o )
				{	
					interActiveElement.setActive(tf);
				}
			}
		}
	}

	public static boolean insideRect ( float x, float y, float width, float height, float mx, float my )
	{
		return mx >= x && mx <= x+width && my >= y && my <= y+height;
	}
	
	//
	//	instance methods
	//
	
	public void mouseEvent ( MouseEvent evt )
	{
		if ( interActiveElements == null ) return;
		
		switch ( evt.getID() )
		{
			case MouseEvent.MOUSE_ENTERED:
				mouseEntered( evt );
				break;
			case MouseEvent.MOUSE_MOVED:
				mouseMoved( evt );
				break;
			case MouseEvent.MOUSE_PRESSED:
				mousePressed( evt );
				break;
			case MouseEvent.MOUSE_DRAGGED:
				mouseDragged( evt );
				break;
			case MouseEvent.MOUSE_RELEASED:
				mouseReleased( evt );
				break;
			case MouseEvent.MOUSE_CLICKED:
				//mousePressed( evt );
				break;
			case MouseEvent.MOUSE_EXITED:
				mouseExited( evt );
				break;
				
		}
	}
	
	private void mouseEntered ( MouseEvent evt ) {
	}
	
	private void mouseMoved ( MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();

		for ( AbstractActiveElement interActiveElement : interActiveElements )
		{
			if ( !interActiveElement.isActive() ) continue;
			
			boolean wasHover = interActiveElement.hover;
			interActiveElement.hover = interActiveElement.isInside( mx, my );
			if ( interActiveElement.hover && !wasHover )
			{
				interActiveElement.mouseEntered( );
				interActiveElement.mouseEntered( mx, my );
			}
			else if ( !interActiveElement.hover && wasHover )
			{
				interActiveElement.mouseExited( );
				interActiveElement.mouseExited( mx, my );
			}
			else
			{
				interActiveElement.mouseMoved( );
				interActiveElement.mouseMoved( mx, my );
			}
		}
	}
	
	private void mousePressed ( MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();
		
		for ( AbstractActiveElement interActiveElement : interActiveElements )
		{
			if ( !interActiveElement.isActive() ) continue;

			if ( interActiveElement.hover )
				interActiveElement.mousePressedPre( mx, my );
		}
	}
	
	private void mouseDragged ( MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();
		
		for ( AbstractActiveElement interActiveElement : interActiveElements )
		{
			if ( !interActiveElement.isActive() ) continue;

			if ( interActiveElement.hover )
				interActiveElement.mouseDraggedPre( mx, my );
		}
	}
	
	private void mouseReleased ( MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();
		
		for ( AbstractActiveElement interActiveElement : interActiveElements )
		{
			if ( !interActiveElement.isActive() ) continue;

			if ( !interActiveElement.hover ) continue;
			
			interActiveElement.mouseReleasedPre( mx, my );
			interActiveElement.mouseReleasedPost( mx, my );
		}
	}
	
	private void mouseExited ( MouseEvent evt ) {
	}
	
	public static void add ( Object listener )
	{
		if ( manager == null) {
			System.err.println( "You need to call Interactive.make() first." );
			return;
		}
		
		Class klass = listener.getClass();
		while ( klass != null ) {
			if ( klass == AbstractActiveElement.class ) return;
			klass = klass.getSuperclass();
		}
		
		// this adds itself to Interactive in constructor
		new ReflectiveActiveElement( listener );
	}

	public void addElement ( AbstractActiveElement activeElement )
	{
		if ( interActiveElements == null ) 
			interActiveElements = new ArrayList<AbstractActiveElement>();
		if ( !interActiveElements.contains(activeElement) )
			interActiveElements.add( activeElement );
	}

	public void mouseWheelMoved ( MouseWheelEvent e ) 
	{
		int step = e.getWheelRotation();
		for ( AbstractActiveElement interActiveElement : interActiveElements )
		{
			if ( !interActiveElement.isActive() ) continue;

			interActiveElement.mouseScrolled( step );
			
			float mx = papplet.mouseX;
			float my = papplet.mouseY;
			boolean wasHover = interActiveElement.hover;
			interActiveElement.hover = interActiveElement.isInside( mx, my );
			if ( interActiveElement.hover && !wasHover )
			{
				interActiveElement.mouseEntered( );
				interActiveElement.mouseEntered( mx, my );
			}
			else if ( !interActiveElement.hover && wasHover )
			{
				interActiveElement.mouseExited( );
				interActiveElement.mouseExited( mx, my );
			}
		}
	}
	
	public void draw ()
	{
		for ( AbstractActiveElement interActiveElement : interActiveElements )
		{
			if ( !interActiveElement.isActive() ) continue;
			interActiveElement.draw();
		}
	}
}