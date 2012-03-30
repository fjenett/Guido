package de.bezier.gui2;

import processing.core.*;
import java.awt.event.*;

public class GuiBase extends GuiObject
{
    PApplet papplet;
    int pressedX, pressedY;
    int draggedX, draggedY;
    
    GuiStyle style;
    
    GuiBase ( Object args )
    {
        super( Object[].class.isInstance(args) ? args : null );
        if ( PApplet.class.isInstance(args) )
            init( (PApplet)args );
    }
    
    GuiBase ( Object args, PApplet papplet )
    {
        super( args );
        init( papplet );
    }
    
    private void init ( PApplet papplet )
    {
        this.papplet = papplet;
        this.papplet.registerPre(this);
        this.papplet.registerDraw(this);
        this.papplet.registerPost(this);
        this.papplet.registerMouseEvent(this);
        this.papplet.registerKeyEvent(this);
        
        style = GuiStyle.getStyle(papplet);
    }
    
    public void pre () {}
    public void draw () {}
    public void post () {}
    public void mouseEvent ( MouseEvent evt )
    {
        switch ( evt.getID() )
        {
            case MouseEvent.MOUSE_ENTERED:
                this.mouseEnteredRaw( evt );
                break;
            case MouseEvent.MOUSE_MOVED:
                this.mouseMovedRaw( evt );
                break;
            case MouseEvent.MOUSE_PRESSED:
                pressedX = evt.getX();
                pressedY = evt.getY();
                this.mousePressedRaw( evt );
                break;
            case MouseEvent.MOUSE_DRAGGED:
                draggedX = evt.getX() - pressedX;
                draggedY = evt.getY() - pressedY;
                this.mouseDraggedRaw( evt );
                break;
            case MouseEvent.MOUSE_RELEASED:
                this.mouseReleasedRaw( evt );
                break;
            case MouseEvent.MOUSE_EXITED:
                this.mouseExitedRaw( evt );
                break;
            default:
                //papplet.println( evt );
        }
    }
    public void mouseMovedRaw ( MouseEvent evt ) {
        mouseMoved(evt);
    }
    public void mouseMoved ( MouseEvent evt ) {}
    public void mouseDraggedRaw ( MouseEvent evt ) {
        mouseDragged(evt);
    }
    public void mouseDragged ( MouseEvent evt ) {}
    public void mousePressedRaw ( MouseEvent evt ) {
        mousePressed(evt);
    }
    public void mousePressed ( MouseEvent evt ) {}
    public void mouseReleasedRaw ( MouseEvent evt ) {
        mouseReleased(evt);
    }
    public void mouseReleased ( MouseEvent evt ) {}
    public void mouseEnteredRaw ( MouseEvent evt ) {
        mouseEntered(evt);
    }
    public void mouseEntered ( MouseEvent evt ) {}
    public void mouseExitedRaw ( MouseEvent evt ) {
        mouseExited(evt);
    }
    public void mouseExited ( MouseEvent evt ) {}
    
    public void keyEvent ( KeyEvent evt )
    {
        switch ( evt.getID() )
        {
            case KeyEvent.KEY_PRESSED:
                this.keyPressed( evt );
                break;
            case KeyEvent.KEY_RELEASED:
                this.keyReleased( evt );
                break;
            case KeyEvent.KEY_TYPED:
                this.keyTyped( evt );
                break;
            default:
                papplet.println( evt );
        }
    }
    
    public  void keyPressed ( KeyEvent evt ) {}
    public  void keyReleased ( KeyEvent evt ) {}
    public  void keyTyped ( KeyEvent evt ) {}
}