import de.bezier.gui.*;

void setup ()
{
    size( 640, 480 );
    
    GuiButton button = new GuiButton(this);
    button.set(new Object(){
        PVector position = new PVector(10,10);
        PVector size = new PVector(200,20);
        String label = "Button 1";
    });
    
    GuiButton button2 = new GuiButton(this);
    button2.set(
        "label", "My Button 2",
        "position", new PVector(10, 40),
        "size", new PVector(200,20)
    );
    
    GuiButton button3 = new GuiButton(this);
    button3.setPosition(10, 70)
           .setSize(200,20)
           .setLabel("A 3rd Button");
}

void draw ()
{
    background( 120 );
}
