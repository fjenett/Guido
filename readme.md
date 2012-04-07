***GUIDO***
	
This is a simple cross mode GUI library for the Processings.

What does cross mode mean and why "the Processings"?
The future 2.0 Processing will have ["modes"](http://wiki.processing.org/w/JavaScript) which are additional language flavours to write and use Processing in. One is the then included JavaScript mode which is based upon [Processing.js](https://github.com/processing-js/processing-js). Guido is an attempt at writing a GUI library that supports both Java and JavaScript, so any sketch using it should run on both modes.
	
***Installing***

As of Processing 2.0 you should use the "Library Manager" built into the PDE. From the menu:
Sketch > Import Library ... > Add Library ...

Select "GUI" from the filter menu at top, or type guido into search.

Select "Guido" in the list and click "Install" button on the right.

***Getting started***

Guido wants you to write your own interface elements, it does not provide them by default as other great GUI libs do. It comes with examples of standard elements which should you get started. If you come up with something cool, consider sharing it to be included as example.

Guido is mainly an event manager that collects and distributes events to interface elements you create. A simple example:

	import de.bezier.guido.*;

	void setup ()
	{
	    size ( 200, 200 );
    
	    Interactive.make( this ); // start GUIDO
    
	    MyButton mb = new MyButton( 20, 20, 50, 50 ); // create an instance of you element
	}

	void draw ()
	{
		background(255);
		
	    // GUIDO calls "element.draw()" of every element registered with it after
	    // this draw for the elements to be drawn on top.
	}

	public class MyButton
	{
	    float x,y,width,height;
	    boolean on;
    
	    MyButton ( float xx, float yy, float ww, float hh ) 
	    {
	        x = xx; y = yy; width = ww; height = hh;
        
	        Interactive.add( this ); // add this to GUIDO
	    }
    
	    void mousePressed ()
	    {
	        // called when the button has been pressed
        
	        on = !on;
	    }
    
	    void draw ()
	    {
	        // called by GUIDO after PApplet.draw() has finished
        
	        fill( on ? 80 : 140 );
	        rect( x, y, width, height );
	    }
	}
