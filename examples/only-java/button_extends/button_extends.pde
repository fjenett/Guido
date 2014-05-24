/**
 *    Simple button.
 *
 *    Does not work with JavaScript mode!
 */

import de.bezier.guido.*;

SimpleButton button;

void setup ()
{
    size(400, 400);
    
    // start interactive "manager"
    Interactive.make( this );
    
    // create button instance
    button = new SimpleButton(20, 20, 20, 20);
}

void draw ()
{
    background( 0 );
}

class SimpleButton extends ActiveElement
{
    boolean on;
    
    SimpleButton ( float x, float y, float w, float h ) 
    {
        // this registers this button with the "manager" and sets "hot area"
        
        super( x,y,w,h );
    }
    
    // one possible callback, automatically called 
    // by manager when button clicked
    
    void mousePressed () 
    {
        on = !on;
    }

    void draw () 
    {
        if ( hover ) stroke( 255 );
        else noStroke();
        
        if ( on ) fill( 200 );
        else fill( 100 );
        rect(x, y, width, height);
    }
}

