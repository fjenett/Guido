//
//	Taking a whole hearted stab at including a js lib alongside Java.
//
//	fjenett - 2012-03
//

if ( !Interactive ) {
var Interactive = (function(){

    var interactiveInstance;

    // x-browser (?) addEventListener implementation
    var addEventListenerImpl = function( target, event, callback ) {
        if ( 'addEventListener' in target ) {
            target.addEventListener( event, callback );
        } else {
            var originalEvent = target["on"+event];
            target["on"+event] = function ( evt ) {
                var bubble = callback.apply( evt.target, [evt] );
                if ( originalEvent ) originalEvent.apply( target, [evt] );
                return bubble;
            };
        }
    };

    // -----------------------------------------
    //   class definition
    // -----------------------------------------

    var Interactive = function () {

        var opts = arguments[0];

        this.target = opts.target;
        this.listeners = [];

        var events = [
            "mousemove", "mousedown", "mouseup", "click", "dblclick", 
            "mouseover", "mouseout", "mouseenter", "mouseleave" 
        ];
        var eventDests = {
            "mousemove": "mouseMoved",
            "mousedown": "mousePressed",
            "dblclick": "mouseDoubleClicked",
            "mouseup": "mouseReleased"
        };
        for ( var e in events ) {
            (function(manager, target, event, meth){
                addEventListenerImpl( target, event, function( evt ){
//                    console.log( event );
//                    console.log( evt );
                    for ( var l in manager.listeners ) {
						if ( !( manager.listeners[l].isActive() ) ) continue;
                        if ( meth in manager.listeners[l] )
                            manager.listeners[l][meth]( evt.offsetX, evt.offsetY );
                    }
                });
            })(this, this.target, events[e], eventDests[events[e]]);
        }

        this.add = function( listener ) {
            this.listeners.push( listener );
        }
    }

    // -----------------------------------------
    //   static
    // -----------------------------------------

    Interactive.make = function ( t ) {
        interactiveInstance = new Interactive({
            target: t.externals.canvas
        });
    }

    Interactive.add = function ( listener ) {
        interactiveInstance.add( new ActiveElement( listener ) );
    }

    Interactive.setActive = function ( listener, state ) {
        if ( interactiveInstance ) {
			for ( var l in interactiveInstance.listeners ) {
				var ll = interactiveInstance.listeners[l];
				if ( ll.listener == listener ) {
					// console.log( "I.setActive" );
					ll.setActive( state );
				}
			}
		}
    }

    var ActiveElement = function () {

        this.listener = arguments[0];
        if ( !'isInside' in this.listener ) {
            alert( "Interactive: listener must implement\n\public boolean isInside (float mx, float my)" );
        }

        this.pressed = this.dragged = this.hover = false, this.activated = true;
        this.clickedMouseX, this.clickedMouseY;
        this.clickedPositionX, this.clickedPositionY;
        this.draggedDistX, this.draggedDistY;
        this.lastPressed = 0;
		this.activated = true;

        this.mousePressed = function ( mx, my ) {
            if ( !this.activated ) return;

            this.pressed = this.listener.isInside( mx, my );
            if ( this.pressed ) {
                this.clickedPositionX = this.listener.x;
                this.clickedPositionY = this.listener.y;
                this.clickedMouseX = mx;
                this.clickedMouseY = my;
                if ( 'mousePressed' in this.listener )
                    this.listener.mousePressed ( mx, my );
            }
        }

        this.mouseDoubleClicked = function ( mx, my ) {
            if ( !this.activated ) return;

            if ( this.listener.isInside( mx, my ) ) {
                if ( 'mouseDoubleClicked' in this.listener )
                    this.listener.mouseDoubleClicked( mx, my );
            }
        }

        this.mouseMoved = function ( mx, my ) {
            if ( !this.activated ) return;

            this.dragged = this.pressed;
            if ( this.dragged ) {
                this.draggedDistX = this.clickedMouseX - mx;
                this.draggedDistY = this.clickedMouseY - my;
                if ( 'mouseDragged' in this.listener )
                    this.listener.mouseDragged( mx, my, this.clickedPositionX - this.draggedDistX, this.clickedPositionY - this.draggedDistY );
            } else {
                var nowInside = this.listener.isInside( mx, my );

                if ( !nowInside && this.hover ) {
                    if ( 'mouseExited' in this.listener ) {
                        this.listener.mouseExited( mx, my );
                    }
                } else if ( nowInside && !this.hover ) {
                    if ( 'mouseEntered' in this.listener ) {
                        this.listener.mouseEntered( mx, my );
                    }
                } else {
                    if ( 'mouseMoved' in this.listener ) {
                        this.listener.mouseMoved( mx, my );
                    }
                }

                this.hover = nowInside;
            }
        }

        this.mouseReleased = function ( mx, my ) {
            if ( !this.activated ) return;

            if ( this.dragged ) {
                this.draggedDistX = this.clickedMouseX - mx;
                this.draggedDistY = this.clickedMouseY - my;
            }

            if ( this.pressed ) {
                if ( 'mouseReleased' in this.listener )
                    this.listener.mouseReleased( mx, my );
            }

            this.pressed = this.dragged = false;
        }

		this.setActive = function ( state ) {
			if ( this.listener ) {
				if ( 'setActive' in this.listener ) {
					this.listener.setActive( state );
					return;
				}
			}
			this.activated = state;
		}

		this.isActive = function () {
			if ( this.listener ) {
				if ( 'isActive' in this.listener ) {
					return this.listener.isActive();
				}
			}
			return this.activated;
		}
    }

    return Interactive;

})();
} // check already imported