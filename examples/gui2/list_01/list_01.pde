import de.bezier.gui.*;

void setup ()
{
    size( 640, 480 );
    
    GuiList menu = new GuiList(this);
    menu.set(new Object(){
        PVector position = new PVector(10, 10);
        PVector size = new PVector(200, 20);
        String label = "My Menu List";
    });
    for ( int i = 0; i < 25; i++ )
    {
        menu.addItem("My Item #"+i, null);
    }
    
}

void draw ()
{
    background( 120 );
}
