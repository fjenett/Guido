/**
 *    Example checkboxes
 */

import de.bezier.guido.*;

void setup ()
{
    size( 300, 300 );
    
    Interactive.make(this);
    
    for ( int i = 0; i < height-60; i+= 37 ) {
        new CheckBox( "Checkbox #"+(i+1), 30, 30+i, 10, 10 );
    }
}

void draw ()
{
    background( 50 );
}

public class CheckBox
{
    boolean checked;
    float x, y, width, height;
    String label;
    float padx = 7;
    
    CheckBox ( String l, float xx, float yy, float ww, float hh )
    {
        label = l;
        x = xx; y = yy; width = ww; height = hh;
        Interactive.add( this );
    }
    
    void mouseReleased ()
    {
        checked = !checked;
    }
    
    void draw ()
    {
        noStroke();
        fill( 200 );
        rect( x, y, width, height );
        if ( checked )
        {
            fill( 80 );
            rect( x+2, y+2, width-4, height-4 );
        }
        fill( 255 );
        textAlign( LEFT );
        text( label, x+width+padx, y+height );
    }
    
    // this is a special inside test that includes the label text
    
    boolean isInside ( float mx, float my )
    {
        return Interactive.insideRect( x,y,width+padx+textWidth(label), height, mx, my );
    }
}
