package de.bezier.guido;

import processing.core.*;
import java.util.*;
import java.awt.event.*;
import java.lang.reflect.*;

/**
 *	<h1>This is the main element for you to use.</h1>
 *
 *	<p>
 *	The normal setup would be to create a class implementing some of the callbacks
 *  provided by Guido and then register it with the manager.
 *  </p>
 *
 *  <p>
 *	See <a href="ReflectiveActiveElement.html">ReflectiveActiveElement</a> for a list
 *	of all available callbacks and their forms.
 *	</p>
 */
public class Interactive
implements MouseWheelListener
{
	private boolean enabled = true;

	/*
	 * These flags influence which AbstractActiveElement callbacks are called.
	 * This permits Guido to be used with Jython, and perhaps, in the future,
	 * other languages that don't support function name overloading by parameter
	 * signature. 
	 */
	public static final int CALLBACKS_POSITION = 1;
	public static final int CALLBACKS_NO_POSITION = 2;
	public static final int CALLBACKS_DELTA = 4;
	public static final int CALLBACKS_NO_DELTA = 8;
	private int callbackStyle = CALLBACKS_POSITION | CALLBACKS_NO_POSITION | CALLBACKS_DELTA
			| CALLBACKS_NO_DELTA;

	boolean shouldCallbackWithPosition()
	{
		return (callbackStyle & CALLBACKS_POSITION) != 0;
	}

	boolean shouldCallbackWithoutPosition()
	{
		return (callbackStyle & CALLBACKS_NO_POSITION) != 0;
	}

	boolean shouldCallbackWithDelta()
	{
		return (callbackStyle & CALLBACKS_POSITION) != 0;
	}

	boolean shouldCallbackWithoutDelta()
	{
		return (callbackStyle & CALLBACKS_NO_POSITION) != 0;
	}

	ArrayList<AbstractActiveElement> interActiveElements;
	private ArrayList<AbstractActiveElement> interActiveElementsList;

	static Interactive manager;
	PApplet papplet;

	static HashMap<String, ArrayList<EventBinding>> eventBindings;
	static Method emitMethod;

	private Interactive ( PApplet papplet )
	{
		this.papplet = papplet;

		papplet.registerMethod( "mouseEvent" ,this );

		addMouseWheelListener();

		papplet.registerMethod( "draw", this );
	}

	/**
	 *	Add java.awt.event.MouseWheel listener to PApplet
	 */
	private void addMouseWheelListener ()
	{
		new Thread()
		{
			int runs = 0;

			public void run ()
			{
				runs++;
				if ( manager.papplet.frame != null )
				{
					manager.papplet.frame.addMouseWheelListener( manager );
					return;
				}

				try {
					Thread.sleep( 1000 );
					if (runs < 10 ) run();
				} catch ( Exception e ) {
					System.err.println( e );
				}
			}
		}.start();
	}

	/**
	 *	Main entry point for PApplet (from your sketch).
	 *
	 *	@param papplet Your sketch
	 */
	public static Interactive make ( PApplet papplet )
	{
		manager = new Interactive( papplet );
		return manager;
	}

	/**
	 *	Get the actual manager instance.
	 *
	 *	@return the Interactive instance which is the actual manager, or null
	 */
	public static Interactive get ()
	{
		if ( manager == null )
		{
			System.err.println( "You need to initialize me first with ...\n\n\tInteractive.make(this);\n" );
		}
		return manager;
	}

	// ------------------------------------------
	//	static methods, states, utils
	// ------------------------------------------

	/**
	 * Tell the Guido system which styles of event callbacks to use.
	 * 
	 * @param flags
	 *					Any bitwise combination of {@link #CALLBACKS_POSITION},
	 *					{@link #CALLBACKS_NO_POSITION}, {@link #CALLBACKS_DELTA}, and
	 *					{@link #CALLBACKS_NO_DELTA}.
	 */
	public static void setCallbackStyle(final int flags)
	{
		if (get() != null)
		{
			get().callbackStyle = flags;
		}
	}
	
	/**
	 *	Activate or deactivate and interface element.
	 *
	 *	@param element  the interface element
	 *	@param state	the state: true for active, false to deactivate
	 */
	public static void setActive ( Object element, boolean state )
	{
		if ( manager != null )
		{
			for ( AbstractActiveElement interActiveElement : manager.interActiveElements )
			{
				if ( interActiveElement.getClass().equals( ReflectiveActiveElement.class ) )
				{
					if ( ((ReflectiveActiveElement)interActiveElement).listener == element )
					{
						interActiveElement.setActive( state );
					}
				}
				else if ( interActiveElement == element )
				{
					interActiveElement.setActive( state );
				}
			}
		}
	}

	/**
	 *	A utility function to do a simple box test.
	 *
	 *	@param	x 		left coordinate
	 *	@param	y		top coordinate
	 *	@param	width 	width of the rectangle
	 *	@param	height 	height of the rectangle
	 *	@param	mx		x of point to test
	 *	@param	my		y of point to test
	 *  @return 		true if the point is inside the rectangle
	 */
	public static boolean insideRect ( float x, float y, float width, float height, float mx, float my )
	{
		return mx >= x && mx <= x+width && my >= y && my <= y+height;
	}

	/**
	 *	Add an element to the manager to be managed.
	 *
	 *	<p>The easiest way to use this is inside your elements constructor:
	 *  <code>
	 *	public class YourElement {
	 *		YourElement () {
	 *			Interactive.add( this );
	 *		}
	 *	}
	 *  </code></p>
	 *
	 *	@param element the element to be managed
	 *	@return AbstractActiveElement the internal listener
	 */
	public static AbstractActiveElement add ( Object element )
	{
		if ( manager == null) {
			System.err.println( "You need to call Interactive.make() first." );
			return null;
		}

		Class klass = element.getClass();
		while ( klass != null ) {
			if ( klass == AbstractActiveElement.class ) return null;
			klass = klass.getSuperclass();
		}

		// this adds itself to Interactive in constructor
		ReflectiveActiveElement rae = new ReflectiveActiveElement( element );
		return rae;
	}

	public static void add ( Object[] elements )
	{
		for ( Object e : elements )
		{
			Interactive.add( e );
		}
	}

	/**
	 *	Remove an element from the manager.
	 *
	 *	@param element the element to stop managing
	 */
	public static void remove ( Object element )
	{
		if ( Interactive.get().interActiveElements != null )
		{
			for ( Object e : Interactive.get().interActiveElements )
			{
				if (((ReflectiveActiveElement) e).getListener() == element)
				{
					Interactive.get().interActiveElements.remove(e);
					break;
				}
			}
		}
	}

	public static void remove ( Object[] elements )
	{
		for ( Object e : elements )
		{
			Interactive.remove( e );
		}
	}


	/**
	 *	Trying to set a field in an object to a given value.
	 */
	public static void set ( Object obj, String fieldName, Object value )
	{
		if ( obj == null || fieldName == null || value == null )
		{
			System.err.println( "Interactive.set() ... a value is null!" );
		}
		Field field = Interactive.getField( obj, fieldName, value );
		if ( field != null )
		{
			try {
	            field.set( obj, value );
	        } catch ( Exception e ) {
	            e.printStackTrace();
	        }
		}
	}

	/**
	 *	Get a field from an object by looking at the fields name and value signature.
	 */
	public static Field getField ( Object obj, String fieldName, Object value )
	{
		Class valueClass = value.getClass();

		Field[] fields = obj.getClass().getDeclaredFields();
		for ( Field f : fields )
		{
			if ( f.getType().isAssignableFrom( valueClass ) && f.getName().equals( fieldName ) )
			{
				return f;
			}
		}

		// what to do here?

		System.err.println( "Interactive.set() ... unable to find a field with that name in given target" );
		return null;
	}

	public static void on ( String eventName, Object targetObject, String targetMethodName )
	{
		Interactive.onImpl( null, eventName, targetObject, targetMethodName );
	}

	public static void on ( Object[] emitterObjects, String eventName, Object targetObject, String targetMethodName )
	{
		if ( emitterObjects != null ) {
			for ( Object e : emitterObjects ) {
				Interactive.onImpl( e, eventName, targetObject, targetMethodName );
			}
		} else {
			System.err.println( "Err: no emitter events available" );
		}
	}

	public static void on ( Object emitterObject, String eventName, Object targetObject, String targetMethodName )
	{
		Interactive.onImpl( emitterObject, eventName, targetObject, targetMethodName );
	}

	private static void onImpl ( Object emitterObject, String eventName, Object targetObject, String targetMethodName )
	{
		Method targetMethod = null;
		try {
			Method[] meths = targetObject.getClass().getDeclaredMethods();
			for ( Method m : meths )
			{
				if ( m.getName().equals( targetMethodName ) )
				{
					targetMethod = m;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		if ( targetMethod != null )
		{
			String bindingId = EventBinding.getIdFor( emitterObject, eventName );
			EventBinding binding = new EventBinding( eventName, targetObject, targetMethod );
			if ( eventBindings == null ) eventBindings = new HashMap<String, ArrayList<EventBinding>>();
			ArrayList<EventBinding> list = eventBindings.get( bindingId );
			if ( list == null )
			{
				list = new ArrayList<EventBinding>();
				eventBindings.put( bindingId, list );
			}
			list.add( binding );
		}
		else
		{
			System.err.println( "Sorry, that method named " + targetMethodName + " could not be found ... " );
		}
	}

	public static void send ( String eventName, Object...eventValues )
	{
		if ( emitMethod == null ) {
			try {
				emitMethod = Interactive.class.getDeclaredMethod( "send", new Class[]{
					Object.class, String.class, Object[].class
				});
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		try {
			emitMethod.invoke( null, new Object[]{ null, eventName, eventValues } );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void send ( Object emitterObject, String eventName, Object...eventValues )
	{
		if ( eventBindings == null )
		{
			System.err.println( "No bindings exist at the moment" );
			return;
		}
		String bindingId = EventBinding.getIdFor( emitterObject, eventName );
		ArrayList<EventBinding> list = eventBindings.get( bindingId );
		if ( list != null )
		{
			for ( EventBinding binding : list )
			{
				if ( binding != null )
				{
					binding.send( eventValues );
				}
			}
		} else {
			System.out.println( "No bindings found for: " + bindingId );
		}
	}

	public static boolean isActive ()
	{
		if ( manager != null ) {
			return manager.isEnabled();
		}
		return false;
	}

	public static void toggle ()
	{
		if ( manager != null ) {
			manager.setEnabled( manager.isEnabled() ? false : true );
		}
	}

	public static void deactivate ()
	{
		if ( manager != null ) {
			manager.setEnabled( false );
		}
	}

	public static void activate ()
	{
		if ( manager != null ) {
			manager.setEnabled( true );
		}
	}

	public static void setActive ( boolean tf )
	{
		if ( manager != null ) {
			manager.setEnabled( tf );
		}
	}

	// ------------------------------------------
	//	instance methods
	// ------------------------------------------


	public boolean isEnabled ()
	{
		return enabled;
	}

	public void setEnabled ( boolean tf )
	{
		enabled = tf;
	}

	/**
	 *	Callback for Component.addMouseWheelListener()
	 *
	 *	@param e the mouse wheel (scroll) event
	 *  @see java.awt.Component#addMouseWheelListener(java.awt.event.MouseWheelListener)
	 */
	public void mouseWheelMoved ( java.awt.event.MouseWheelEvent e )
	{
		if ( !enabled ) return;
		if ( interActiveElements == null ) return;

		mouseWheelMovedImpl( e.getWheelRotation() );
	}

	private void mouseWheelMovedImpl ( float amount )
	{
		if ( !enabled ) return;
		if ( interActiveElements == null ) return;

		updateListenerList();

		for ( AbstractActiveElement interActiveElement : interActiveElementsList )
		{
			if ( !interActiveElement.isActive() ) continue;

			interActiveElement.mouseScrolled( amount );

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

		clearListenerList();
	}

	private void updateListenerList ()
	{
		if ( interActiveElements == null ) {
			System.err.println( "Trying to build event-list but source list is empty" );
			return;
		}
		if ( interActiveElementsList == null ) {
			interActiveElementsList = new ArrayList<AbstractActiveElement>( interActiveElements.size() );
			interActiveElementsList.addAll( interActiveElements );
		}
	}

	private void clearListenerList ()
	{
		interActiveElementsList = null;
	}

	/**
	 *	Add an element to the manager, mainly used internally.
	 *
	 *	@param activeElement the element to manage
	 */
	public void addElement ( AbstractActiveElement activeElement )
	{
		if ( interActiveElements == null )
			interActiveElements = new ArrayList<AbstractActiveElement>();
		if ( !interActiveElements.contains(activeElement) )
			interActiveElements.add( activeElement );
	}

	/**
	 *	Callback for PApplet.registerMethod("draw")
	 *
	 *	@see <a href="http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core/processing/core/PApplet.html#registerDraw(java.lang.Object)">PApplet.registerDraw( Object obj )</a>
	 */
	public void draw ()
	{
		if ( !enabled ) return;

		if ( interActiveElements == null ) return;

		updateListenerList();

		for ( AbstractActiveElement interActiveElement : interActiveElementsList )
		{
			if ( !interActiveElement.isActive() ) continue;
			interActiveElement.draw();
		}

		clearListenerList();
	}

	/**
	 *	Callback for PApplet.registerMethod("mouseEvent")
	 *
	 *	@see <a href="http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core/processing/core/PApplet.html#registerMouseEvent(java.lang.Object)">PApplet.registerMouseEvent(Object obj)</a>
	 */
	public void mouseEvent ( processing.event.MouseEvent evt )
	{
		if ( !enabled ) return;

		if ( interActiveElements == null ) return;

		updateListenerList();

		switch ( evt.getAction() )
		{
			case processing.event.MouseEvent.ENTER:
				mouseEntered( evt );
				break;
			case processing.event.MouseEvent.MOVE:
				mouseMoved( evt );
				break;
			case processing.event.MouseEvent.PRESS:
				mousePressed( evt );
				break;
			case processing.event.MouseEvent.DRAG:
				mouseDragged( evt );
				break;
			case processing.event.MouseEvent.RELEASE:
				mouseReleased( evt );
				break;
			case processing.event.MouseEvent.CLICK:
				//mousePressed( evt );
				break;
			case processing.event.MouseEvent.EXIT:
				mouseExited( evt );
				break;
			case processing.event.MouseEvent.WHEEL:
				mouseWheelMovedImpl( evt.getCount() );
				break;
		}

		clearListenerList();
	}

	private void mouseEntered ( processing.event.MouseEvent evt ) {
	}

	private void mouseMoved ( processing.event.MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();

		for ( AbstractActiveElement interActiveElement : interActiveElementsList )
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

	private void mousePressed ( processing.event.MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();

		for ( AbstractActiveElement interActiveElement : interActiveElementsList )
		{
			if ( !interActiveElement.isActive() ) continue;

			if ( interActiveElement.hover )
				interActiveElement.mousePressedPre( mx, my );
		}
	}

	private void mouseDragged ( processing.event.MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();

		for ( AbstractActiveElement interActiveElement : interActiveElementsList )
		{
			if ( !interActiveElement.isActive() ) continue;

			if ( interActiveElement.hover )
				interActiveElement.mouseDraggedPre( mx, my );
		}
	}

	private void mouseReleased ( processing.event.MouseEvent evt )
	{
		int mx = evt.getX();
		int my = evt.getY();

		for ( AbstractActiveElement interActiveElement : interActiveElementsList )
		{
			if ( !interActiveElement.isActive() ) continue;

			if ( !interActiveElement.hover ) continue;

			interActiveElement.mouseReleasedPre( mx, my );
			interActiveElement.mouseReleasedPost( mx, my );
		}
	}

	private void mouseExited ( processing.event.MouseEvent evt ) {
	}
}
