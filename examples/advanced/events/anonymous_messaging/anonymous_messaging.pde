/**
 *    Inside guido Java there are two "hacks" at work to access anonymous inner classes 
 *    and classes in the "default" package (no package).
 */

import de.bezier.guido.*;

void setup ()
{
    size( 200, 200 );

    Interactive.make(this);
    
    Interactive.on( this, "send-test", this, "test" );
    Interactive.on( this, "send-test", new TestClass(), "test" );
    Interactive.on( this, "send-test", new Object(){
        void test ()
        {
            println( "Called anonymous inner Object test()" );
        }
    }, "test" );
    
    Interactive.send( this, "send-test" ); // send it!
}

void draw () {
}

void test () {
    println( "Called sketch test()" );
}

public class TestClass {
    void test ()
    {
        println( "Called TestClass.test()" );
    }
}
