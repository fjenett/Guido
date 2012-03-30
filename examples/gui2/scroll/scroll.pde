
import de.bezier.gui.*;

MouseCircle circle;

void setup() 
{
    size(400,400);
    Interactive.make( this );
    circle = new MouseCircle(width/2,height/2,20,20);
}

void draw() 
{
    background( 200 );
    circle.draw();
}

class MouseCircle extends ActiveElement
{
    float r;
    
    MouseCircle ( float x, float y, float w, float h ) 
    {
        super( x,y,w,h );
        r = w;
    }
    
    void mouseScrolled( int s ) 
    {
        r += s;
    }
    
    void draw () 
    {
        if (pressed) fill(0,200,100);
        else if ( hover ) fill( 220 );
        else fill( 255 );
        
        ellipse( x,y,r,r );
    }
    
    boolean isInside ( float mx, float my )
    {
        return dist( x,y,mx,my ) < r/2;
    }
}
