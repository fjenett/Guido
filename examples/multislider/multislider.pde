/**
 *    Multi-slider
 */

import de.bezier.guido.*;

MultiSlider slider;

void setup ()
{
    size(400, 400);
    Interactive.make( this );
    
    slider = new MultiSlider( 10, height-20, width-20, 10 );
    Interactive.add( slider );
}

void draw ()
{
    background( 60, 50, 40 );
    
    for ( int i = 0; i < width; i++ )
    {
        float f = map( i, 0, width, 0, 1 );
        if ( f < slider.values[0] )
            stroke( map(f, 0, slider.values[0], 0, 255) );
        else if ( f < slider.values[1] )
            stroke( map(f, slider.values[0], slider.values[1], 255, 0) );
        else
            stroke( map(f, slider.values[1], 1, 0, 255) );
            
        line( i, 0, i, height );
    }
    
    slider.draw();
}
