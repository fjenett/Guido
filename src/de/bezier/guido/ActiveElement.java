package de.bezier.guido;

public class ActiveElement extends AbstractActiveElement
{			
	public ActiveElement ( float x, float y, float width, float height ) {super(x,y,width,height);}
	// mouseEntered, mouseMoved, mouseExited missing?
	public void mousePressed ( ){};
	public void mousePressedAt ( float mx, float my ){};
	public void mouseDoubleClicked ( ){};
	public void mouseDoubleClickedAt ( float mx, float my ){};
	public void mouseDraggedAt ( float mx, float my ){};
	public void mouseDraggedFromTo ( float mx, float my, float dx, float dy ){};
	public void mouseReleased ( ){};
	public void mouseReleasedAt ( float mx, float my ){};
}

