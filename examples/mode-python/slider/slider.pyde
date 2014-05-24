"""
Slider example
"""

add_library('Guido')

# global slider variable
slider = None

class SimpleSlider(ActiveElement):
    
    def __init__(self, x, y, w, h ):
        super( SimpleSlider, self ).__init__(x,y,w,h)
        self.hover = False
        self.valueX = x
        self.value = 0
    
    def mouseDraggedFromTo(self, mx, my, dx, dy ):
        self.valueX = mx - self.height/2
        
        if ( self.valueX < self.x ):
            self.valueX = self.x
        
        if ( self.valueX > self.x+self.width-self.height ):
            self.valueX = self.x+self.width-self.height
        
        self.value = map( self.valueX, self.x, self.x+self.width-self.height, 0, 1 )
        
    def draw(self):
        noStroke()
        fill( 210 if self.hover else 150 )
        rect( self.x, self.y, self.width, self.height )
        fill( 235 )
        rect( self.valueX, self.y, self.height, self.height )

def setup():
    size( 200, 220 )
    
    Interactive.make(this, True)
    
    global slider
    slider = SimpleSlider( 10, height-20, width-20, 10 )


def draw():
    background( 255/2 )
    
    fill( 255 - (slider.value * 255) )
    ellipse( width/2, height/2, 150, 150 )
    fill( slider.value * 255 )
    ellipse( width/2, height/2, 70, 70 )

