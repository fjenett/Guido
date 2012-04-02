/**
 *    Example checkbox
 */

import de.bezier.gui.*;

ArrayList<CheckBox> checkboxes;

void setup ()
{
    size( 300, 300 );
    
    Interactive.make(this);
    
    checkboxes = new ArrayList();
    
    for ( int i = 0; i < height-60; i+= 37 ) {
        CheckBox c = new CheckBox( "Example checkbox", 30, 30+i, 10, 10 );
        Interactive.add( c );
        checkboxes.add( c );
    }
}

void draw ()
{
    background( 90, 80, 70 );
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
    
    boolean isInside ( float mx, float my )
    {
        return Interactive.insideRect( x,y,width+padx+textWidth(label), height, mx, my );
    }
}
