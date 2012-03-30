package de.bezier.gui2;

import processing.core.*;

public class GuiStyle
{
    PFont fontLabel;
    
    final int TOP=0, RIGHT=1, BOTTOM=2, LEFT=3;
    final int ALIGN_TOP=0, ALIGN_RIGHT=1, ALIGN_BOTTOM=2, ALIGN_LEFT=4, ALIGN_CENTERED=8;
    
    int labelColor = 50;
    int fillColor = 255;
    int hoverColor = 220;
    int pressedColor = 190;
    int[] itemPadding = new int[]{
        5, 5, 5, 5
    };
    int[] itemMargin = new int[]{
        5, 5, 5, 5
    };

	private static GuiStyle style;
	public static GuiStyle getStyle ( PApplet papplet )
	{
		if ( style == null ) style = new GuiStyle( papplet );
		return style;
	}

	private GuiStyle () {}
	
    private GuiStyle ( PApplet papplet )
    {
		// try {
		//         	fontLabel = papplet.loadFont( "BitstreamVeraSansMono-Roman-11-BasicLatin.vlw" );
		// } catch ( Exception e ) {
		// 	
		// }
		if ( fontLabel == null )
		{
			fontLabel = papplet.createFont( "Verdana", 11 );
		}
    }
    
    void drawRect ( PApplet p, GuiItem item, float x, float y, float w, float h )
    {
        p.pushStyle();
		p.rectMode(p.CORNER);
        if ( item != null )
            p.fill( item.hover ? hoverColor : fillColor );
        else
            p.fill( fillColor );
        p.noStroke();
        p.rect( x, y, w, h );
        p.popStyle();
    }
    
    void drawLabel ( PApplet p, GuiItem item, String txt, float x, float y, float w, float h )
    {
        drawLabel(p,item,txt,x,y,w,h,ALIGN_CENTERED);
    }
    
    void drawLabel ( PApplet p, GuiItem item, String txt, float x, float y, float w, float h, int align )
    {
        p.pushStyle();
        p.fill( labelColor );
        p.textFont( fontLabel );
        float hl = h-itemPadding[TOP]-itemPadding[BOTTOM];
        float wl = w-itemPadding[LEFT]-itemPadding[RIGHT];
        String l = txt;
        while ( l.length() > 1 && p.textWidth(l) > wl ) {
            l = l.substring(0,l.length()-1);
        }
        if ( l.length() != txt.length() && l.length() > 3 )
        {
            l = l.substring(0,l.length()-3) + "...";
        }
        float yy = y+(itemPadding[TOP]+itemPadding[BOTTOM])/2+hl/2+fontLabel.getGlyph('J').height/2;
        switch ( align )
        {
            case ALIGN_CENTERED:
                p.textAlign( p.CENTER );
                p.text( l, x+(itemPadding[LEFT]+itemPadding[RIGHT])/2+wl/2, yy );
                break;
            case ALIGN_LEFT:
                p.textAlign( p.LEFT );
                p.text( l, x+itemPadding[LEFT], yy );
                break;
            case ALIGN_RIGHT:
                p.textAlign( p.RIGHT );
                p.text( l, x+w-itemPadding[RIGHT], yy );
                break;
        }
        p.popStyle();
    }
}