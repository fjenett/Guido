package de.bezier.gui2;

import java.lang.reflect.*;
import java.util.HashMap;

public class GuiReflectionListener extends GuiListener
{
	private HashMap<String,MethodInstancePair[]> methods;
	
	public GuiReflectionListener ( Object instance, String methName )
	{
		this( instance, "event", methName );
	}
	
	public GuiReflectionListener ( Object instance, String triggerOn, String methName )
	{
		addCallback( instance, triggerOn, methName );
	}
	
	public GuiReflectionListener addCallback ( Object instance, String triggerOn, String methName )
	{
		if ( instance == null || triggerOn == null || methName == null ) return this;
		
		if ( methods == null ) methods = new HashMap<String,MethodInstancePair[]>();
		MethodInstancePair[] meths = methods.get(triggerOn);
		MethodInstancePair mipair = new MethodInstancePair(instance,methName);
		
		if ( meths == null )
		{
			meths = new MethodInstancePair[]{mipair};
		}
		else
		{
			MethodInstancePair[] methsTmp = new MethodInstancePair[meths.length+1];
			System.arraycopy(meths,0,methsTmp,0,meths.length);
			methsTmp[methsTmp.length-1] = mipair;
			meths = methsTmp;
		}
		
		methods.put(triggerOn, meths);
		
		return this;
	}
	
	public void event ( GuiEvent evt )
	{
		callMethods( "event", evt );
	}

	public void pressed ( GuiEvent evt )
	{
		callMethods( "pressed", evt );
	}

	public void released ( GuiEvent evt )
	{
		callMethods( "released", evt );
	}

	public void bang ( GuiEvent evt )
	{
		callMethods( "bang", evt );
	}

	public void changed ( GuiEvent evt )
	{
		callMethods( "changed", evt );
	}
	
	private void callMethods ( String triggerOn, GuiEvent evt )
	{
		MethodInstancePair[] meths = methods.get(triggerOn);
		if ( meths != null )
		{
			for ( MethodInstancePair pair : meths )
			{
				pair.invoke(evt);
			}
		}
	}
	
	private class MethodInstancePair
	{
		Method method;
		Object instance;
		
		MethodInstancePair ( Object instance, String methodName )
		{
			this( instance, findMethod( instance.getClass(), methodName ) );
		}
		
		MethodInstancePair ( Object instance, Method method )
		{
			this.instance = instance;
			this.method = method;
		}
		
		void invoke ( GuiEvent evt )
		{
			try {
				method.invoke(instance,new Object[]{evt});
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	private Method findMethod ( Class klass, String methodName )
	{
		Method meth = null;
		
		while ( klass != null )
		{
			try 
			{
				meth = klass.getMethod( methodName, new Class[]{ GuiEvent.class } );
			} catch ( Exception e ) {
				e.printStackTrace();
				break;
			}
			if ( meth == null )
			{
				try 
				{
					meth = klass.getDeclaredMethod( methodName, new Class[]{ GuiEvent.class } );
				} catch ( Exception e ) {
					e.printStackTrace();
					break;
				}
			}
			if ( meth != null )
			{
				break;
			}
			if ( meth == null )
			{
				klass = klass.getSuperclass();
			}
		}
		
		return meth;
	}
}