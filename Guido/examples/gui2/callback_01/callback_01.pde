import de.bezier.gui.*;

void setup ()
{
    size( 640, 480 );
    
    GuiButton button1 = new GuiButton(this);
    button1.set(
        "label", "Click to bang",
        "position", new PVector(10, 10),
        "size", new PVector(200,20)
    );
    button1.addListener("bang","clickedButton1");
    
    GuiButton button2 = new GuiButton(this);
    button2.set(
        "label", "My Button 2",
        "position", new PVector(10, 40),
        "size", new PVector(200,20)
    );
    button2.addListener(new GuiListener(){
        public void bang ( GuiEvent evt ) {
            println( "BANG!" );
            rectMode(CENTER);
            fill(random(255),180,220);
            float w = random(10,200);
            rect(random(width), random(height),w,w);
        }
    });
    
    noStroke();
    colorMode(HSB);
    background(120);
}

void draw ()
{
}

void clickedButton1 ( GuiEvent evt )
{
    println( "PAHDAUZ!" );
    fill(random(255),180,100);
    float w = random(10,200);
    ellipse(random(width), random(height),w,w);
}
