"""
Simple button,
ported by Jonathan Feinberg, thanks!
"""

add_library('Guido')


class SimpleButton(ActiveElement):

    def __init__(self, x, y, w, h):
        super(SimpleButton, self).__init__(x, y, w, h)
        self.on = False

    def mousePressed(self):
        self.on = not self.on

    def draw(self):
        if self.hover:
            stroke(255)
        else:
            noStroke()

        fill(210 if self.on else 150)
        rect(self.x, self.y, self.width, self.height)


def setup():
    size(200, 200)

    # start interactive "manager"
    Interactive.make(this, True)

    # create button instance
    SimpleButton( width/2-10, height/2-10, 20, 20)


def draw():
    background(0)

