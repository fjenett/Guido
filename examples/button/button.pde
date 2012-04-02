/**
 *    Simple button.
 *
 *    .. works with JavaScript mode since Processing 2.0a5
 */

import de.bezier.guido.*;

SimpleButton button;

void setup ()
{
    size(400, 400);
    
    // make the manager
    
    Interactive.make( this );
    
    // create a button
    
    button = new SimpleButton(20, 20, 20, 20);
    
    // register button
    
    Interactive.add( button );
}

void draw ()
{
    background( 0 );
}

public class SimpleButton
{
    float x, y, width, height;
    boolean on;
    
    SimpleButton ( float x, float y, float w, float h )
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }
    
    // called by manager
    
    void mousePressed () 
    {
        on = !on;
    }

    void draw () 
    {
        if ( on ) fill( 200 );
        else fill( 100 );
        
        rect(x, y, width, height);
    }
    
    // called by manager to see if mouse is over element
    
    boolean isInside ( float mx, float my ) 
    {
        return mx >= x && mx <= x+width && my >= y && my <= y+height;
    }
}

