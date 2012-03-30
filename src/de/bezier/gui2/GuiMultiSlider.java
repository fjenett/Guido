package de.bezier.gui2;

import processing.core.*;
import java.awt.event.*;

public class GuiMultiSlider extends GuiSlider
{
	Float[] values;
	boolean[] locks;
	private int clickDistance = 20;
	private int currentValue;
	
	public GuiMultiSlider ( Object args )
	{
		super(args);
		value = null;
	}

    public GuiMultiSlider setMinMax ( float mi, float ma )
    {
		super.setMinMax( mi, ma );
        return this;
    }

    public GuiMultiSlider setValue ( float val )
    {
		super.setValue( val );
        return this;
    }

    public GuiMultiSlider setValue ( int index, float val )
    {
		if ( values != null )
			values[index] = val;
        return this;
    }

    public float value ()
    {
		if ( currentValue != -1 && values[currentValue] != null )
        	return values[currentValue].floatValue();
		else
			return Float.NaN;
    }

    public float value ( int which )
    {
        return values[which].floatValue();
    }
    
    public float valueNormalized ()
    {
		if ( currentValue != -1 && values[currentValue] != null )
        	return papplet.map( values[currentValue].floatValue(), minimum, maximum, 0, 1 );
		else
			return Float.NaN;
    }

    public float valueNormalized ( int which )
    {
		return papplet.map( values[which].floatValue(), minimum, maximum, 0, 1 );
    }

	public float[] values ()
	{
		if ( values == null ) return null;
		if ( values.length == 0 ) return new float[0];
		float[] vals = new float[values.length];
		for ( int i = 0, k = values.length; i < k; i++ )
		{
			vals[i] = values[i].floatValue();
		}
		return vals;
	}
	
	public float[] valuesNormalized()
	{
		if ( values == null ) return null;
		if ( values.length == 0 ) return new float[0];
		float[] vals = new float[values.length];
		for ( int i = 0, k = values.length; i < k; i++ )
		{
			vals[i] = papplet.map( values[i].floatValue(), minimum, maximum, 0, 1 );
		}
		return vals;
	}

	public GuiMultiSlider lock ( int... which )
	{
		setLocks(true, which);
		return this;
	}

	public GuiMultiSlider unlock ( int... which )
	{
		setLocks(false, which);
		return this;
	}
	

	private void setLocks ( boolean state, int[] which )
	{
		if ( which != null )
		{
			if ( locks == null )
				locks = new boolean[values.length];

			for ( int i = 0, m = which.length; i < m; i++ )
			{
				if ( which[i] >= 0 && which[i] < locks.length )
					locks[which[i]] = state;
			}
		}
	}
	
	public GuiMultiSlider setValues ( float... many )
	{
		if ( many != null )
		{
			currentValue = -1;
			
			values = new Float[many.length];
			locks = new boolean[many.length];
			
			for ( int i = 0, m = many.length; i < m; i++ )
			{
				values[i] = many[i];
			}
		}
		return this;
	}
	
	public void mousePressed ( MouseEvent evt )
	{
		if ( values != null )
		{
			float nValue = calcMousedValue(evt);
			System.out.println( nValue );
			int closest = -1;
			float vDist = Float.MAX_VALUE, d = 0;
			for ( int i = 0, n = values.length; i < n; i++ )
			{
				d = Math.abs( nValue - values[i].floatValue() );
				if ( d < vDist && !locks[i] )
				{
					vDist = d;
					closest = i;
				}
			}
			currentValue = closest;
		}
	}

    public void mouseDragged ( MouseEvent evt )
    {
		if ( values != null && currentValue != -1 && values[currentValue] != null )
		{
			float nValue = calcMousedValue(evt);
			values[currentValue] = papplet.constrain(nValue,minimum,maximum);
	        GuiEvent gvt = new GuiEvent( this );
	        for ( GuiListener l : listeners )
	        {
	            l.event( gvt );
	            l.changed( gvt );
	        }
		}
    }

    public void mouseReleased ( MouseEvent evt )
    {
		//currentValue = null;
	}
	
	public void draw ( PApplet papplet )
	{
		super.draw( papplet );
		
		if ( currentValue != -1 && values[currentValue] != null )
		{
	        style.drawLabel( papplet, this, values[currentValue].floatValue()+"", position.x, position.y, size.x, size.y, style.ALIGN_RIGHT );
	        drawHandle( papplet, minimum, values[currentValue].floatValue(), maximum );
		}
		
		if ( values != null && values.length > 0 )
		{
			for ( int i = 0, k = values.length; i < k; i++ )
				drawHandle( papplet, minimum, values[i].floatValue(), maximum );
		}
	}
}