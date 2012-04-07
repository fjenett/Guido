
public class MultiSlider
{
    float x,y,width,height;
    boolean on = false;
    
    SliderHandle left, right, activeHandle;
    
    float values[];
    
    MultiSlider ( float xx, float yy, float ww, float hh )
    {
        this.x = xx; this.y = yy; this.width = ww; this.height = hh;
        
        left  = new SliderHandle( x, y, height, height );
        right = new SliderHandle( x+width-height, y, height, height );
        
        values = new float[]{0,1};
    }
    
    void mouseEntered ()
    {
        on = true;
    }
    
    void mouseExited ()
    {
        on = false;
    }
    
    void mousePressed ( float mx, float my )
    {
        if ( left.isInside( mx, my ) ) activeHandle = left;
        else if ( right.isInside( mx, my ) ) activeHandle = right;
    }
    
    void mouseDragged ( float mx, float my )
    {
        if ( activeHandle == null ) return;
        
        float vx = mx - activeHandle.width/2;
        
        vx = constrain( vx, x, x+width-activeHandle.width );
        
        if ( activeHandle == left )
        {
            if ( vx > right.x - activeHandle.width ) vx = right.x - activeHandle.width;
            values[0] = map( vx, x, x+width-activeHandle.width, 0, 1 );
        }
        else
        {
            if ( vx < left.x + activeHandle.width ) vx = left.x + activeHandle.width;
            values[1] = map( vx, x, x+width-activeHandle.width, 0, 1 );
        }
        
        activeHandle.x = vx;
    }
    
    void draw ()
    {
        noStroke();
        fill( 120 );
        rect( x, y, width, height );
        fill( on ? 200 : 150 );
        rect( left.x, left.y, right.x-left.x+right.width, right.height );
    }
    
    public boolean isInside ( float mx, float my )
    {
        return left.isInside(mx,my) || right.isInside(mx,my);
    }
}

class SliderHandle
{
    float x,y,width,height;
    
    SliderHandle ( float xx, float yy, float ww, float hh )
    {
        this.x = xx; this.y = yy; this.width = ww; this.height = hh;
    }
    
    void draw ()
    {
        rect( x, y, width, height );
    }
    
    public boolean isInside ( float mx, float my )
    {
        return Interactive.insideRect( x, y, width, height, mx, my );
    }
}
