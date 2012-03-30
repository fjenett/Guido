package de.bezier.gui2;

import java.lang.reflect.*;

public class GuiObject
{
    public GuiObject ( Object args )
    {
        set( args );
    }
    
	// see why: DeBezierGuiGuiObject
    public GuiObject set ( Object args )
    {
        if ( args == null ) return this;
        
		Class klass = null;
		try {
			klass = getClass().forName("DeBezierGuiGuiObject");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Method meth = null;
		try {
			meth = klass.getMethod("set", new Class[]{ Object.class, Object.class });
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			meth.invoke(null,new Object[]{this,args});
		} catch (Exception e) {
			e.printStackTrace();
		}
		postSet();
        return this;
    }
	
	// see why: DeBezierGuiGuiObject
	public GuiObject set ( Object... args )
    {
        if ( args == null ) return this;
        
		Class klass = null;
		try {
			klass = getClass().forName("DeBezierGuiGuiObject");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Method meth = null;
		try {
			meth = klass.getMethod("set", new Class[]{ Object.class, Object[].class });
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			meth.invoke(null,new Object[]{this,args});
		} catch (Exception e) {
			e.printStackTrace();
		}
		postSet();
        return this;
    }

	protected void postSet ()
	{
	}
}