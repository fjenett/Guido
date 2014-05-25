package de.bezier.guido;

public class ActiveElement extends AbstractActiveElement
{			
	public ActiveElement ( float x, float y, float width, float height ) {super(x,y,width,height);}
	
	// mouseEntered, mouseMoved, mouseExited missing?
	
	public void mousePressed ( float mx, float my ){};
	public void mouseDoubleClicked ( float mx, float my ){};
	public void mouseDragged ( float mx, float my, float dx, float dy ){};
	public void mouseReleased ( float mx, float my ){};

	// deprecated since 0.1.0
	//public void mousePressed ( ){};
	//public void mouseDoubleClicked ( ){};
	//public void mouseDragged ( float mx, float my ){};
	//public void mouseReleased ( ){};
}

