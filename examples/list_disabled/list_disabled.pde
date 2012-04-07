/**
 *    A list
 *
 *    .. works with JavaScript mode since Processing 2.0a5.
 *
 *    Make sure to try your scroll wheel!
 */

import de.bezier.guido.*;

Listbox listbox;
Object lastItemClicked;
boolean showItem;

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
    
    // this disables the item
    
    Interactive.setActive( listbox, showItem );
}

void draw ()
{
    background( 20 );
    fill( 255 );
    
    if ( lastItemClicked != null )
    {
        text( "Clicked " + lastItemClicked.toString(), 30, 35 );
    }
    else
    {
        text( "Item is disabled ... hence it's not visible.\nPress 'v' to show", 30, 35 );
    }
}

void keyPressed ()
{
    if ( key == 'v' )
    {
        showItem = !showItem;
        Interactive.setActive( listbox, showItem );
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
    boolean activated = true;
    
    Listbox ( float xx, float yy, float ww, float hh ) 
    {
        x = xx; y = yy;
        valueY = y;
        
        width = ww; height = hh;
    
        // register it
        Interactive.add( this );
    }
    
    boolean isActive ()
    {
        return activated;
    }
    
    void setActive ( boolean tf )
    {
        activated = tf;
    }
    
    void addItem ( String item )
    {
        if ( items == null ) items = new ArrayList();
        items.add( item );
        
        hasSlider = items.size() * itemHeight > height;
    }
    
    void mouseMoved ( float mx, float my )
    {
        if ( hasSlider && mx > width-20 ) return;
        
        hoverItem = listStartAt + int((my-y) / itemHeight);
    }
    
    void mouseExited ( float mx, float my )
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
    
    void mousePressed ( float mx, float my )
    {
        if ( hasSlider && mx > width-20 ) return;
        
        int item = listStartAt + int( (my-y) / itemHeight);
        itemClicked( item, items.get(item) );
    }

    void draw ()
    {
        noStroke();
        fill( activated ? 100 : 20 );
        rect( x,y,this.width,this.height );
        
        if ( items != null )
        {
            for ( int i = 0; i < int(height/itemHeight) && i < items.size(); i++ )
            {
                stroke( 80 );
                fill( (activated && (i+listStartAt) == hoverItem) ? 200 : 120 );
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

