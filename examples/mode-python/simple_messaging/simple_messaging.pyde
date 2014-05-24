"""
Testing Guidos simple messaging system
"""

add_library('Guido')

class TestClass:
    
    def test(self):
        println("Called TestClass.test()")

def setup():
    
    size( 200, 200 )
    
    Interactive.make(this, True)
    
    #Interactive.on( this, "send-test", this, "test" )
    
    t = TestClass()
    #Interactive.on( this, "send-test", t, "test" )
    
    #Interactive.on( this, "send-test", this, … )
    
    Interactive.send( this, "send-test" )

def test():
    
    println( "Called sketch test()" )
