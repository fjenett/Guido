package de.bezier.guido.test;

import de.bezier.guido.*;

import processing.core.*;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GuidoTests
{
	@Test
	public void testSecondConstructor ()
	{
		PApplet papplet = new PApplet();

		Interactive ia1 = Interactive.make(papplet);
		Interactive ia2 = Interactive.make(papplet, false);
		Interactive ia3 = Interactive.make(papplet, true);

		assertSame( ia1, ia2 );
		assertNotSame( ia1, ia3 );
	}

	@Test
	public void testIsInsideBasic2DElement ()
	{
		Basic2DElement b2d = new Basic2DElement( 100, 100, 200, 200 );

		assertTrue( b2d.isInside(150,150) );

		assertTrue( b2d.isInside(100,150) );
		assertTrue( b2d.isInside(150,100) );

		assertFalse( b2d.isInside(99,150) );
		assertFalse( b2d.isInside(150,99) );
		
		assertTrue( b2d.isInside(300,150) );
		assertTrue( b2d.isInside(150,300) );
		
		assertFalse( b2d.isInside(301,150) );
		assertFalse( b2d.isInside(150,301) );

		assertFalse( b2d.isInside(500,500) );
	}
}