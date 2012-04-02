package de.bezier.guido;

public class Basic2DElement
{
	public float x, y;
	public float width, height;
	
	public Basic2DElement () {}
	
	public Basic2DElement ( float x, float y, float width, float height ) 
	{
		this.x = x; 
		this.y = y; 
		this.width = width; 
		this.height = height;
	}
	
	public boolean isInside ( float tx, float ty ) 
	{
		return (tx >= x && tx <= x+width && ty >= y && ty <= y+height);
	}
}

