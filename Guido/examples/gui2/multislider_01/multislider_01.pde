import de.bezier.gui.*;

float[] circleValues;
color[] colors;

void setup ()
{
    size( 640, 480 );
    
    circleValues = new float[]{0.1,0.2,0.3,0.4,0.5,0.6};
    
    colorMode(HSB);
    colors = new color[circleValues.length];
    for ( int i = 0; i < colors.length; i++ ) colors[i] = color(random(255),170,220);
    
    GuiMultiSlider slider = new GuiMultiSlider(this);
    slider.setContext(0,2)
          .setValues(circleValues)
          .setPosition(10,10)
          .setSize(width-20,20)
          .addListener(new GuiListener(){
              public void changed ( GuiEvent evt ) {
                  circleValues = ((GuiMultiSlider)evt.item).values();
                  Arrays.sort(circleValues);
              }
          });
}

void draw ()
{
    background(100);
    noStroke();
    smooth();
    
    pushMatrix();
    translate(width/2, height/2);
    
    for ( int i = circleValues.length; i > 0; i-- )
    {
        float f = circleValues[i-1];
        fill(colors[i-1]);
        ellipse(0,0,width*f,width*f);
    }
    
    popMatrix();
}
