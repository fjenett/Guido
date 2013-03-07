package de.bezier.guido;

import java.lang.reflect.*;

public class ReflectiveActiveElement extends AbstractActiveElement
{
	Object listener;
	Method setter, getter;
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
	
	/**
	 *	TODO:
	 *	- check and warn about unreachable functions, wrong parameters, ...
	 */
	private void init ( Object l )
	{
		try {
			Class sClass = Class.forName( "DeBezierGuiGuiObject" );
			setter = sClass.getMethod( "set", Object.class, Object.class );
			if (debug) System.out.println( setter );
			
		} catch ( Exception e ) {
			if (debug) e.printStackTrace();
		}
		
		try {
			Class sClass = Class.forName( "DeBezierGuiGuiObject" );
			getter = sClass.getMethod( "get", Object.class, Field.class );
			if (debug) System.out.println( getter );

		} catch ( Exception e ) {
			if (debug) e.printStackTrace();
		}
		
		listener = l;
		
		Method[] meths = getClass().getDeclaredMethods();
		for ( Method m : meths ) {
			try {
				
				// System.out.println( m.getName()+m.getParameterTypes().length );

				Method mo = listener.getClass().getDeclaredMethod( m.getName(), m.getParameterTypes() );
				if ( mo != null )
				{
					getClass().getDeclaredField( m.getName()+m.getParameterTypes().length ).set( this, mo );
					if (debug) System.out.println( mo.getName() );
				}
				// else
				// {
				// 	mo = listener.getClass().getMethod( m.getName(), m.getParameterTypes() );
				// 	if ( mo != null )
				// 	{
				// 		getClass().getDeclaredField( m.getName()+m.getParameterTypes().length ).set( this, mo );
				// 		if (debug) System.out.println( mo.getName() );
				// 	}
				// }
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
	
	/**
	 *	Activate or deactivate callback.
	 *
	 *	Implement this in your class to be notified when Interactive.setActive() changes your element.
	 *
	 *	@param activeState the boolean state: active or not
	 */
	public void setActive ( boolean activeState )
	{
		if ( setActive1 != null )
			try {
				setActive1.invoke( listener, activeState );
			} catch ( Exception e ) { if (debug) e.printStackTrace(); }
		else
			super.setActive( activeState );
	}
	
	/**
	 *	Getter callback for active state.
	 *
	 *	Add this to your class to be notified when another element tries to read your elements active state.
	 *
	 *  @return true or false reflecting active state of element
	 */
	public boolean isActive ()
	{
		if ( isActive0 != null )
			try {
				return (Boolean)isActive0.invoke(listener);
			} catch ( Exception e ) { if (debug) e.printStackTrace(); }
		else
			return super.isActive();
		
		return false;
	}
	
	/**
	 *	Callback for mouse entered without arguments.
	 *
	 * 	Implement this to be notified once the mouse pointer (cursor) enters your element.
	 *	See isInside() for how entering is determined.
	 *
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseEntered () 
	{
		updateXY();
		try {
			if (mouseEntered0 != null) mouseEntered0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse entered with mouse position x and y.
	 *
	 * 	Implement this to be notified once the mouse pointer (cursor) enters your element.
	 *	See isInside() for how entering is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseEntered ( float mx, float my ) 
	{ 
		updateXY();
		try {
			if (mouseEntered2 != null) mouseEntered2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	/**
	 *	Callback for mouse moved over element without arguments.
	 *
	 * 	Implement this to be notified when the mouse pointer (cursor) moves over your element.
	 *	See isInside() for how "over" is determined.
	 *
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseMoved () 
	{ 
		updateXY();
		try {
			if (mouseMoved0 != null) mouseMoved0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	/**
	 *	Callback for mouse entered with mouse position x and y.
	 *
	 * 	Implement this to be notified when the mouse pointer (cursor) moves over your element.
	 *	See isInside() for how "over" is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseMoved ( float mx, float my ) 
	{ 
		updateXY();
		try {
			if (mouseMoved2 != null) mouseMoved2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse leave/exited without arguments.
	 *
	 * 	Implement this to be notified once the mouse pointer (cursor) leaves your element.
	 *	See isInside() for how leaving is determined.
	 *
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseExited () 
	{ 
		updateXY();
		try {
			if (mouseExited0 != null) mouseExited0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse leave/exited witho mouse position x and y.
	 *
	 * 	Implement this to be notified once the mouse pointer (cursor) leaves your element.
	 *	See isInside() for how leaving is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseExited ( float mx, float my ) 
	{ 
		updateXY();
		try {
			if (mouseExited2 != null) mouseExited2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	/**
	 *	Callback for mouse pressed without arguments.
	 *
	 * 	Implement this to be notified once the mouse is pressed on your element.
	 *	See isInside() for how on is determined.
	 *
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mousePressed () 
	{
		updateXY();
		try {
			if (mousePressed0 != null) mousePressed0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse pressed with mouse position x and y.
	 *
	 * 	Implement this to be notified once the mouse is pressed on your element.
	 *	See isInside() for how on is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mousePressed ( float mx, float my ) 
	{
		updateXY();
		try {
			if (mousePressed2 != null) mousePressed2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse double pressed without arguments.
	 *
	 * 	Implement this to be notified once your element has been double clicked (pressed twice in a short time frame).
	 *	See isInside() for how on is determined.
	 *
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseDoubleClicked () 
	{
		updateXY();
		try {
			if (mouseDoubleClicked0 != null) mouseDoubleClicked0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse double pressed with mouse position x and y.
	 *
	 * 	Implement this to be notified once your element has been double clicked (pressed twice in a short time frame).
	 *	See isInside() for how on is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseDoubleClicked ( float mx, float my ) 
	{
		updateXY();
		try {
			if (mouseDoubleClicked2 != null) mouseDoubleClicked2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse dragged with mouse position x and y.
	 *
	 * 	Implement this to be notified the mouse drags (moves pressed) on your element.
	 *	See isInside() for how on is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseDragged ( float mx, float my ) 
	{
		updateXY();
		try {
			if (mouseDragged2 != null) mouseDragged2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse dragged with mouse position x/y and 
	 *	drag distance xd and yd (difference between mouse position and click position).
	 *
	 * 	Implement this to be notified the mouse drags (moves pressed) on your element.
	 *	See isInside() for how on is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *  @param dx vertical drag distance: difference between mouse pressed x and current x
	 *  @param dy horizontal drag distance: difference between mouse pressed y and current y
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseDragged ( float mx, float my, float dx, float dy ) 
	{
		updateXY();
		try {
			if (mouseDragged4 != null) mouseDragged4.invoke( listener, mx, my, dx, dy );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse released without arguments.
	 *
	 * 	Implement this to be notified the mouse was released from pressing your element.
	 *	See isInside() for how on is determined.
	 *
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseReleased () 
	{
		updateXY();
		try {
			if (mouseReleased0 != null) mouseReleased0.invoke( listener );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback for mouse released with mouse position x and y.
	 *
	 * 	Implement this to be notified the mouse was released from pressing your element.
	 *	See isInside() for how on is determined.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseReleased ( float mx, float my ) 
	{
		updateXY();
		try {
			if (mouseReleased2 != null) mouseReleased2.invoke( listener, mx, my );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	/**
	 *	Callback for mouse wheel / scroll with (normalized) scroll step.
	 *
	 * 	Implement this to be notified the mouse wheel was used on your element.
	 *	See isInside() for how on is determined.
	 *
	 *  @param step mouse wheel step value
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void mouseScrolled ( float step ) 
	{
		updateXY();
		try {
			if (mouseScrolled1 != null) mouseScrolled1.invoke( listener, step );
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}
	
	/**
	 *	Callback for drawing your element.
	 *
	 *	This is being called <em>after</em> PApplet.draw to be able to draw elements on top.
	 *
	 *	@see de.bezier.guido.ReflectiveActiveElement#isInside(float mx,float my)
	 */
	public void draw ()
	{
		if ( draw0 != null )
			try { 
				draw0.invoke( listener ); 
			} catch ( Exception e ) { if (debug) e.printStackTrace(); }
	}

	/**
	 *	Callback to determin if mouse pointer is over element.
	 *
	 *	A simple rectangle test is automatically done by reading x,y,width,height from your element if
	 *	available.
	 *
	 *	By implementing this in your class you can alter the test to work with almost any shape and 
	 *  probably even in 3D space.
	 *
	 *  @param mx mouse pointer x coordinate
	 *  @param my mouse pointer y coordinate
	 *	@see de.bezier.guido.Interactive#insideRect(float x,float y,float width,float height,float mx,float my)
	 */
	public boolean isInside ( float mx, float my ) 
	{
		updateXY();
		try {
			if (isInside2 != null)
				return ((Boolean)(isInside2.invoke( listener, mx, my ))).booleanValue();
		} catch ( Exception e ) { if (debug) e.printStackTrace(); }
		return isInsideFromFields( mx, my );
	}
	
	private boolean isInsideFromFields ( float mx, float my ) 
	{
		if ( fieldX == null || fieldY == null || fieldWidth == null || fieldHeight == null )
			return super.isInside( mx, my );
		else if ( getter != null )
		{
			try {
				
				float x = 		(Float)(getter.invoke( null, this.listener, fieldX ));
				float y = 		(Float)(getter.invoke( null, this.listener, fieldY ));
				float width = 	(Float)(getter.invoke( null, this.listener, fieldWidth ));
				float height = 	(Float)(getter.invoke( null, this.listener, fieldHeight ));
				
				return Interactive.insideRect( x, y, width, height, mx, my );
				
			} catch ( Exception e ) {
				if (debug) e.printStackTrace();
			}
		}
		System.err.println( "Unable to set from fields." );
		return false;
	}
}