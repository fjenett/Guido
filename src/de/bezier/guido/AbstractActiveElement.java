package de.bezier.guido;

public abstract class AbstractActiveElement extends Basic2DElement
{
	protected float clickedMouseX, clickedMouseY;
	protected float clickedPositionX, clickedPositionY;
	protected float draggedDistX, draggedDistY;
	
	public boolean pressed, dragged, hover, activated = true;

	protected boolean debug = false;

	long lastPressed = 0;
	
	public AbstractActiveElement ()
	{	
		super();
		Interactive.get().addElement( this );
	}
	
	public AbstractActiveElement ( float x, float y, float width, float height ) 
	{
		super(x,y,width,height);
		Interactive.get().addElement( this );
	}

	public void setDebug ( boolean tf )
	{
		debug = tf;
	}

	public void setActive ( boolean yesNo ) 
	{
		activated = yesNo;
	}
	
	public boolean isActive ()
	{
		return activated;
	}

	public void mouseEntered ( ) { }
	public void mouseEntered ( float mx, float my ) { }
	
	public void mouseMoved ( ) { }
	public void mouseMoved ( float mx, float my ) { }
	
	public void mouseExited ( ) { }
	public void mouseExited ( float mx, float my ) { }
	
	public void mousePressedPre ( float mx, float my )
	{
		if ( !isActive() ) return;
		
		pressed = isInside( mx, my );
		if ( pressed )
		{
			clickedPositionX = x;
			clickedPositionY = y;
			clickedMouseX = mx;
			clickedMouseY = my;
			long now = System.currentTimeMillis();
			long lp = lastPressed;
			lastPressed = now;
			if ( now - lp < 200 )
			{
				mouseDoubleClicked( );
				mouseDoubleClicked( mx, my );
			}
			else
			{
				mousePressed( );
				mousePressed( mx, my );
			}
		}
	}
	
	abstract public void mousePressed ( );
	abstract public void mousePressed ( float mx, float my );
	abstract public void mouseDoubleClicked ( );
	abstract public void mouseDoubleClicked ( float mx, float my );
	
	public void mouseDraggedPre ( float mx, float my )
	{
		if ( !isActive() ) return;

		dragged = pressed;
		if ( dragged ) 
		{
			draggedDistX = clickedMouseX - mx;
			draggedDistY = clickedMouseY - my;
			mouseDragged( mx, my );
			mouseDragged( mx, my, clickedPositionX - draggedDistX, clickedPositionY - draggedDistY );
		}
	}
	
	abstract public void mouseDragged ( float mx, float my );
	abstract public void mouseDragged ( float mx, float my, float dx, float dy );
	
	public void mouseReleasedPre ( float mx, float my )
	{
		if ( !isActive() ) return;

		if ( dragged ) 
		{
			draggedDistX = clickedMouseX - mx;
			draggedDistY = clickedMouseY - my;
		}
		
		if ( pressed )
			mouseReleased( );
			mouseReleased( mx, my );
	}
	
	abstract public void mouseReleased ( );
	abstract public void mouseReleased ( float mx, float my );
	
	public void mouseReleasedPost ( float mx, float my )
	{
		pressed = false;
		dragged = false;
	}
	
	public void mouseScrolled ( float step ) {
		
	}
	
	public void draw ()
	{
		
	}
}
