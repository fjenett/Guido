/**
 *    This example shows how to use the event sending / callback mechanism.
 */

 import de.bezier.guido.*;
 
 Slider slider;
 MultiSlider mSlider;
 
 float arcRadius = 100, arcStart = 0, arcEnd = TWO_PI;
 
 void setup ()
 {
     size( 500, 500 );
     
     Interactive.make(this);
     
     slider = new Slider ( 10, height-35, width-20, 10 );
     
     // set radiusChanged method as listener to valueChanged event from slider
     Interactive.on( slider, "valueChanged", this, "radiusChanged" );
     
     mSlider = new MultiSlider( 10, height-20, width-20, 10 );
     
     Interactive.on( mSlider, "leftValueChanged",  this, "leftChanged" );
     Interactive.on( mSlider, "rightValueChanged", this, "rightChanged" );
 }
 
 void draw ()
 {
     background( 0 );
     
     arc( width/2, height/2, arcRadius, arcRadius, arcStart, arcEnd );
 }
 
 void radiusChanged ( float v )
 {
     arcRadius = map( v, 0, 1, 1, width/2-20 );
 }
 
 void leftChanged ( float v )
 {
     arcStart = map( v, 0, 1, 0, TWO_PI );
 }
 
 void rightChanged ( float v )
 {
     arcEnd = map( v, 0, 1, 0, TWO_PI );
 }
