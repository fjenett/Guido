package de.bezier.guido;

import java.lang.reflect.Method;
import java.lang.ref.WeakReference;

public class EventBinding
{
	String eventName;
	WeakReference<Object> targetWeakReference;
	Method targetMethod;

	public static String getIdFor ( Object object, String event )
	{
		if ( object == null ) return "<null>" + "." + event;
		return object.getClass().getName() + "@" + object.hashCode() + "." + event;
	}

	public EventBinding ( String eventName, Object targetObject, Method targetMethod )
	{
		this.eventName = eventName;
		targetWeakReference = new WeakReference<Object>(targetObject);
		this.targetMethod = targetMethod;
	}

	public void send ( Object[] values )
	{
		if ( targetWeakReference != null )
		{
			Object target = targetWeakReference.get();
			if ( target != null )
			{
				if ( !targetMethod.isAccessible() )
				{
					try {
						targetMethod.setAccessible( true );
					} catch ( SecurityException se ) {
						se.printStackTrace();
					}
				}
				try {
					targetMethod.invoke( target, values );
				} catch ( Exception e ) {
					e.printStackTrace();

					System.err.println(String.format(
						"EventBinding.send():\n\t-> %s\n\t() %s\n\t.. %s",
						target.toString(),
						targetMethod.toString(),
						values.toString()
					));
				}	
			}
		}
	}
}