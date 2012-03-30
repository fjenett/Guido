package de.bezier.gui2;

import processing.core.*;
import java.awt.event.*;
import java.util.*;

public class GuiList extends GuiItem
{
    LinkedHashMap<GuiItem,Object> items;
    
    int itemHeight = 20;
    GuiListSlider slider;
	GuiListItem root;
    
    public GuiList ( Object args )
    {
        super( args );
		root = addItem( label, null );
        setSize( 100, itemHeight );
    }

	protected void postSet ()
	{
		root.setLabel(label);
		root.setPosition(position.x,position.y);
		root.setSize(size.x,itemHeight);
	}
    
    public GuiListItem addItem ( String label, Object value )
    {
        GuiListItem b = new GuiListItem( papplet );
        b.addListener(new GuiListener(){
            public void bang ( GuiEvent evt ) {
                listItemEvent( evt );
            }
        });
        
        if ( items == null ) items = new LinkedHashMap<GuiItem,Object>();
        items.put( b, value );
        
        b.setSize( size.x, itemHeight );
        b.setPosition( position.x, position.y+(items.size() - 1)*itemHeight );
        b.setLabel( label );
        
        return b;
    }

	public GuiItem setLabel ( String label )
	{
		super.setLabel( label );
		root.setLabel( label );
		return this;
	}
	
	public GuiItem setPosition ( float x, float y )
	{
		super.setPosition(x,y);
		root.setPosition(x,y);
		return this;
	}

    public void listItemEvent ( GuiEvent evt )
    {
    }
    
    public void sliderChanged ( float value )
    {
        if ( value == 0 || items == null || items.size() < (size.y/itemHeight) ) return;
        int totalHeight = items.size();
        int visibleHeight = (int)(size.y/itemHeight);
        int offset = (int)(papplet.map(value, 0, 1, 0, totalHeight-visibleHeight));
        int i = 0;
        for ( Map.Entry entry : items.entrySet()  )
        {
            GuiItem item = (GuiItem)entry.getKey();
            item.setPosition( position.x, position.y+(i-offset)*itemHeight );
            i++;
        }
    }
    
    public Object getValueForItem ( GuiItem item )
    {
        return items.get( item );
    }
    
    public GuiItem setSize ( float x, float y )
    {
        itemHeight = (int)y;
        super.setSize( x, itemHeight );
		root.setSize(x,y);
        
        if ( items != null )
        {
            for ( GuiItem item : items.keySet() )
            {
                item.setSize( x-10, item.size.y );
            }
        }
        return this;
    }
    
    public void mouseEntered ( MouseEvent evt )
    {
		if ( items.size() > 0 )
		{
        	size.y = items.size()*itemHeight;
			if ( position.y + size.y > papplet.height )
			{
				size.y = (int)((papplet.height - position.y) / itemHeight) * itemHeight;
			}
			
			if ( slider == null )
			{
				slider = new GuiListSlider( papplet );
	            slider.setSize(10,size.y);
	            slider.setPosition(position.x+size.x-10, position.y);
	            slider.setOrientation(2);
	            slider.addListener(new GuiListener(){
	                public void changed ( GuiEvent evt ) {
	                    sliderChanged( ((GuiSlider)evt.item).valueNormalized() );
	                }
	            });
			}
		}
    }
    
    public void mouseExited ( MouseEvent evt )
    {
        size.y = itemHeight;
    }
    
    public GuiItem hide ()
    {
        super.hide();
        if ( items != null )
        {
            for ( GuiItem item : items.keySet() )
            {
                item.hide();
            }
        }
        if ( slider != null )
            slider.hide();
        return this;
    }
    
    public GuiItem show ()
    {
        super.show();
        if ( items != null )
        {
            for ( GuiItem item : items.keySet() )
            {
                item.show();
            }
        }
        if ( slider != null )
            slider.show();
        return this;
    }
    
    public void draw ( PApplet p )
    {
        style.drawRect(p,this, position.x,position.y,size.x,size.y);
        boolean withSlider = hover && slider != null;
        if ( items != null )
        {
            for ( GuiItem item : items.keySet() )
            {
                boolean isLast = item.position.y+item.size.y >= position.y+size.y;
                if ( item.position.y+item.size.y > position.y && item.position.y < position.y+size.y )
                {
                    style.drawRect( p, item, item.position.x, item.position.y, item.size.x, item.size.y );
                    style.drawLabel( p, item, item.label, item.position.x, item.position.y, item.size.x, item.size.y );
                    if ( !isLast )
                    {
                        p.pushStyle();
                        p.stroke(style.labelColor);
                        p.strokeWeight(1);
                        p.line(item.position.x, item.position.y+item.size.y-1, item.position.x+item.size.x, item.position.y+item.size.y-1);
                        p.popStyle();
                    }
                }
            }
        }
        if ( withSlider && slider != null )
        {
            slider.drawSlider(p);
        }
    }
}