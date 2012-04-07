/**
 *    A list
 *
 *    .. works with JavaScript mode since Processing 2.0a5
 *
 *    Make sure to try your scroll wheel!
 */

import de.bezier.guido.*;

Listbox listbox;
Object lastItemClicked;

void setup ()
{
    size(400, 400);
    
    // make the manager
    
    Interactive.make( this );
    
    // create a list box
    
    listbox = new Listbox( 20, 60, width-40, height-80 );
    for ( int i = 0, r = int(10+random(100)); i < r; i++ )
    {
        listbox.addItem( "Item " + i );
    }
}

void draw ()
{
    background( 20 );
    
    if ( lastItemClicked != null )
    {
        fill( 255 );
        text( "Clicked " + lastItemClicked.toString(), 30, 35 );
    }
}

public void itemClicked ( int i, Object item )
{
    lastItemClicked = item;
}

public class Listbox
{
    float x, y, width, height;
    
    ArrayList items;
    int itemHeight = 20;
    int listStartAt = 0;
    int hoverItem = -1;
    
    float valueY = 0;
    boolean hasSlider = false;
    
    Listbox ( float xx, float yy, float ww, float hh ) 
    {
        x = xx; y = yy;
        valueY = y;
        
        width = ww; height = hh;
        
        // register it
        Interactive.add( this );
    }
    
    public void addItem ( String item )
    {
        if ( items == null ) items = new ArrayList();
        items.add( item );
        
        hasSlider = items.size() * itemHeight > height;
    }
    
    public void mouseMoved ( float mx, float my )
    {
        if ( hasSlider && mx > width-20 ) return;
        
        hoverItem = listStartAt + int((my-y) / itemHeight);
    }
    
    public void mouseExited ( float mx, float my )
    {
        hoverItem = -1;
    }
    
    // called from manager
    void mouseDragged ( float mx, float my )
    {
        if ( !hasSlider ) return;
        if ( mx < x+width-20 ) return;
        
        valueY = my-10;
        valueY = constrain( valueY, y, y+height-20 );
        
        update();
    }
    
    // called from manager
    void mouseScrolled ( float step )
    {
        valueY += step;
        valueY = constrain( valueY, y, y+height-20 );
        
        update();
    }
    
    void update ()
    {
        float totalHeight = items.size() * itemHeight;
        float itemsInView = height / itemHeight;
        float listOffset = map( valueY, y, y+height-20, 0, totalHeight-height );
        
        listStartAt = int( listOffset / itemHeight );
    }
    
    public void mousePressed ( float mx, float my )
    {
        if ( hasSlider && mx > width-20 ) return;
        
        int item = listStartAt + int( (my-y) / itemHeight);
        itemClicked( item, items.get(item) );
    }

    void draw ()
    {
        noStroke();
        fill( 100 );
        rect( x,y,this.width,this.height );
        if ( items != null )
        {
            for ( int i = 0; i < int(height/itemHeight) && i < items.size(); i++ )
            {
                stroke( 80 );
                fill( (i+listStartAt) == hoverItem ? 200 : 120 );
                rect( x, y + (i*itemHeight), this.width, itemHeight );
                
                noStroke();
                fill( 0 );
                text( items.get(i+listStartAt).toString(), x+5, y+(i+1)*itemHeight-5 );
            }
        }
        
        if ( hasSlider )
        {
            stroke( 80 );
            fill( 100 );
            rect( x+width-20, y, 20, height );
            fill( 120 );
            rect( x+width-20, valueY, 20, 20 );
        }
    }
}

