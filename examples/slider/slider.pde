/**
 *    A slider
 *
 *    .. works with JavaScript mode since Processing 2.0a5
 */

import de.bezier.guido.*;

Slider slider;

void setup ()
{
    size(400, 400);
    
    // make the manager
    
    Interactive.make( this );
    
    // create a slider
    
    slider = new Slider( 2, 2, width-4, 16 );
}

void draw ()
{
    background( 0 );
    
    fill( 255 - (slider.value * 255) );
    ellipse( width/2, height/2, 150, 150 );
    fill( slider.value * 255 );
    ellipse( width/2, height/2, 70, 70 );
}

public class Slider
{
    float x, y, width, height;
    float valueX = 0, value;
    
    Slider ( float xx, float yy, float ww, float hh ) 
    {
        x = xx; 
        y = yy; 
        width = ww; 
        height = hh;
        
        valueX = x;
    
        // register it
        Interactive.add( this );
    }
    
    // called from manager
    void mouseDragged ( float mx, float my )
    {
        valueX = mx - height/2;
        
        if ( valueX < x ) valueX = x;
        if ( valueX > x+width-height ) valueX = x+width-height;
        
        value = map( valueX, x, x+width-height, 0, 1 );
    }

    void draw () 
    {
        noStroke();
        
        fill( 100 );
        rect(x, y, width, height);
        
        fill( 120 );
        rect( valueX, y, height, height );
    }
}

