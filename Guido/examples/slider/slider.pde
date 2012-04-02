/**
 *    A slider
 *
 *    .. works with JavaScript mode since Processing 2.0a5
 */

import de.bezier.gui.*;

Slider slider;

void setup ()
{
    size(400, 400);
    
    // make the manager
    
    Interactive.make( this );
    
    // create a slider
    
    slider = new Slider(0, 0, width, 20);
    
    // register it
    
    Interactive.add( slider );
}

void draw ()
{
    background( 0 );

    slider.draw();
    
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
    }
    
    // called from manager
    public void mouseDragged ( float mx, float my )
    {
        valueX = mx - height/2;
        
        if ( valueX < 0 ) valueX = 0;
        if ( valueX > width-height ) valueX = width-height;
        
        value = map( valueX, 0, width-height, 0, 1 );
    }

    void draw () 
    {
        noStroke();
        
        fill( 100 );
        rect(x, y, width, height);
        
        fill( 120 );
        rect( valueX, y, height, height );
    }
    
    // called from manager
    public boolean isInside ( float mx, float my ) 
    {
        return mx >= x && mx <= x+width && my >= y && my <= y+height;
    }
}

