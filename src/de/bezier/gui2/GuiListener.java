package de.bezier.gui2;

public class GuiListener
{
    private Object[] values;
    
	public GuiListener () {}
    public GuiListener ( Object... vals ) { values = vals; }

	public Object[] values ()
	{
		return values;
	}
	
	public Object value ( int i )
	{
		return values[i];
	}
    
	public void event ( GuiEvent gvt ) {}
    public void bang ( GuiEvent gvt ) {}
    public void pressed ( GuiEvent gvt ) {}
    public void released ( GuiEvent gvt ) {}
    public void changed ( GuiEvent gvt ) {}
}