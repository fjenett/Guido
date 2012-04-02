package de.bezier.guido;

public class ActiveElement extends AbstractActiveElement
{			
	public ActiveElement ( float x, float y, float width, float height ) {super(x,y,width,height);}
	public void mousePressed ( ){};
	public void mousePressed ( float mx, float my ){};
	public void mouseDoubleClicked ( ){};
	public void mouseDoubleClicked ( float mx, float my ){};
	public void mouseDragged ( float mx, float my ){};
	public void mouseDragged ( float mx, float my, float dx, float dy ){};
	public void mouseReleased ( ){};
	public void mouseReleased ( float mx, float my ){};
}

