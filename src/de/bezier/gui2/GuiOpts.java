package de.bezier.gui2;

public class GuiOpts
{
	Object[] options;
	
	GuiOpts ( Object... args )
	{
		options = args;
	}
	
	public Object[] getOptions ()
	{
		return options;
	}
	
    static GuiOpts GuiOpts ( Object... args )
    {
        GuiOpts opts = null;
        try {
            java.lang.reflect.Constructor c = 
				GuiOpts.class.getConstructor(new Class[]{Object[].class});
            opts = (GuiOpts)c.newInstance(args);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return opts;
    }
}