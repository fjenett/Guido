// not in a package so it can access other classes
// in the Java "default package".

import java.lang.reflect.*;

public class DeBezierGuiGuiObject
{
    static public void set ( Object obj, Object args )
    {
        if ( obj == null || args == null ) return;
        
		Class klass = args.getClass();
        Class clazz = obj.getClass();

        while ( clazz != null )
        {
			//System.out.println(clazz);
            
			Field[] fields = clazz.getDeclaredFields();
            for ( Field field : fields )
            {
                field.setAccessible(true);
                Field argField = null;
                try {
                    argField = klass.getDeclaredField(field.getName());
                } catch ( NoSuchFieldException nsfe ) {
                    // ignore
	                //nsfe.printStackTrace();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                if ( argField != null )
                {
                    try {
                        field.set(obj, argField.get(args) );
                    } catch ( IllegalAccessException iae ) {
                        // ignore
						//iae.printStackTrace();
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

	static public void set ( Object obj, Object[] args )
    {
        if ( obj == null || args == null || (args.length % 2) != 0 ) return;
        
    	Class clazz = null;
		
		for ( int i = 0; i < args.length; i+=2 )
		{
			Object fieldNameObj = Array.get(args,i);
			if ( fieldNameObj.getClass() != String.class ) continue;
			
			String fieldName = (String)fieldNameObj;
			Object arg = Array.get(args,i+1);
			
			Class klass = arg.getClass();
			clazz = obj.getClass();
			
	        while ( clazz != null )
	        {
				//System.out.println(clazz);
            
				Field[] fields = clazz.getDeclaredFields();
	            for ( Field field : fields )
	            {
	                field.setAccessible(true);
	                
	                if ( field.getName().equals(fieldName) && 
						 field.getType().isAssignableFrom(klass) )
	                {
	                    try {
	                        field.set(obj, arg );
	                    } catch ( IllegalAccessException iae ) {
	                        // ignore
							//iae.printStackTrace();
	                    } catch ( Exception e ) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	            clazz = clazz.getSuperclass();
	        }	
		}
    }
}