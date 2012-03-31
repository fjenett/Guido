package de.bezier.gui;

import java.lang.reflect.*;

public class ReflectiveActiveElement extends AbstractActiveElement
{
	private boolean debug = false;
	
	Object listener;
	Method setter;
	Method mouseEntered0, mouseEntered2,
	mouseMoved0, mouseMoved2,
	mouseExited0, mouseExited2,
	mousePressed0, mousePressed2,
	mouseReleased2, mouseReleased0,
	mouseDragged2, mouseDragged4,
	mouseDoubleClicked0, mouseDoubleClicked2,
	mouseScrolled1,
	isInside2, 
	isActive0, setActive1,
	draw0;
	Field fieldX, fieldY, fieldWidth, fieldHeight;
	
	ReflectiveActiveElement ( Object l )
	{
		super();
		init( l );
	}
	
	ReflectiveActiveElement ( Object l, float x, float y, float width, float height ) 
	{
		super( x, y, width, height );
		init( l );
	}
	
	private void init ( Object l ) 
	{
		try {
			Class sClass = Class.forName( "DeBezierGuiGuiObject" );
			setter = sClass.getMethod( "set", Object.class, Object.class );
			if (debug) System.out.println( setter );
			
		} catch ( Exception e ) {
			if (debug) e.printStackTrace();
		}
		
		listener = l;
		
		Method[] meths = getClass().getDeclaredMethods();
		for ( Method m : meths ) {
			try {
				// m.getName()+m.getParameterTypes().length
				Method mo = listener.getClass().getDeclaredMethod( m.getName(), m.getParameterTypes() );
				if ( mo != null )
				{
					getClass().getDeclaredField( m.getName()+m.getParameterTypes().length ).set( this, mo );
					if (debug) System.out.println( mo.getName() );
				}
			} catch ( Exception e ) {
				if (debug) e.printStackTrace();
			}
		}
		
		Field[] fields = listener.getClass().getDeclaredFields();
		for ( Field f : fields ) 
		{	
			if ( f.getType() == float.class )
			{
				String fName = f.getName();
				     
				if ( fName.equals("x") )
				{
					fieldX = f;
					if (debug) System.out.println( "x" );
				}
				else if ( fName.equals("y") )
				{
					fieldY = f;
					if (debug) System.out.println( "y" );
				}
				else if ( fName.equals("width") )
				{
					fieldWidth = f;
					if (debug) System.out.println( "width" );
				}
				else if ( fName.equals("height") )
				{
					fieldHeight = f;
					if (debug) System.out.println( "height" );
				}
			}
		}
	}
	
	private void updateXY ()
	{
		// if ( fieldX != null && fieldY != null ) {
		// 	try {
		// 		x = fieldX.getFloat( listener );
		// 		y = fieldY.getFloat( listener );
		// 	} catch ( Exception e ) {
		// 		if (debug) e.printStackTrace();
		// 	}
		// }
		
		// try {
		// 	setter.invoke( null, this, listener );
		// } catch ( Exception e ) {
		// 	if (debug) e.printStackTrace();
		// }
	}
	
	public void setActive ( boolean tf )
	{
		if ( setActive1 != null )
			try {
				setActive1.invoke(listener, tf);
			} catch ( Exception e ) { if (debug) e.printStackTrace(); }
		else
			super.setActive( tf );
	}

	public boolean isActive ( )
	{
		if ( isActive0 != null )
			try {
				return (Boolean)isActive0.invoke(listener);
			} catch ( Exception e ) { if (debug) e.printStackTrace(); }
		else
			return super.isActive();
		
		return false;
	}
	
	public void mouseEntered () {
		updateXY();
		try {
			if (mouseEntered0 != null) mouseEntered0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseEntered ( float mx, float my ) { 
		updateXY();
		try {
			if (mouseEntered2 != null) mouseEntered2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseMoved () { 
		updateXY();
		try {
			if (mouseMoved0 != null) mouseMoved0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseMoved ( float mx, float my ) { 
		updateXY();
		try {
			if (mouseMoved2 != null) mouseMoved2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseExited () { 
		updateXY();
		try {
			if (mouseExited0 != null) mouseExited0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseExited ( float mx, float my ) { 
		updateXY();
		try {
			if (mouseExited2 != null) mouseExited2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mousePressed () {
		updateXY();
		try {
			if (mousePressed0 != null) mousePressed0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mousePressed ( float mx, float my ) {
		updateXY();
		try {
			if (mousePressed2 != null) mousePressed2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseDoubleClicked ( ) {
		updateXY();
		try {
			if (mouseDoubleClicked0 != null) mouseDoubleClicked0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseDoubleClicked ( float mx, float my ) {
		updateXY();
		try {
			if (mouseDoubleClicked2 != null) mouseDoubleClicked2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseDragged ( float mx, float my ) {
		updateXY();
		try {
			if (mouseDragged2 != null) mouseDragged2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseDragged ( float mx, float my, float dx, float dy ) {
		updateXY();
		try {
			if (mouseDragged4 != null) mouseDragged4.invoke( listener, mx, my, dx, dy );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseReleased ( ) {
		updateXY();
		try {
			if (mouseReleased0 != null) mouseReleased0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseReleased ( float mx, float my ) {
		updateXY();
		try {
			if (mouseReleased2 != null) mouseReleased2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void mouseScrolled ( float step ) {
		updateXY();
		try {
			if (mouseScrolled1 != null) mouseScrolled1.invoke( listener, step );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	public void draw ()
	{
		if ( draw0 != null )
			try { 
				draw0.invoke( listener ); 
			} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	public boolean isInside ( float mx, float my ) {
		updateXY();
		try {
			if (isInside2 != null) 
				return ((Boolean)(isInside2.invoke( listener, mx, my ))).booleanValue();
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
		return isInsideFromFields( mx, my );
	}
	
	private boolean isInsideFromFields ( float mx, float my ) {
		return super.isInside( mx, my );
	}
}