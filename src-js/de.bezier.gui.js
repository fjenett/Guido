//
//	Taking a whole hearted stab at including a js lib alongside Java.
//
//	fjenett - 2012-04
//  fjenett 20121219
//

if ( !Interactive ) {
var Interactive = (function(){
	
    // -----------------------------------------
    //   private variables and functions
    // -----------------------------------------

    var interactiveInstance;

	// this is taken from processing.js
	// -------------------------------------
	var stylePaddingLeft, stylePaddingTop, styleBorderLeft, styleBorderTop;
	var setStyleValues = function ( curElement ) {
		if ( document.defaultView && document.defaultView.getComputedStyle ) {
			var style = document.defaultView.getComputedStyle(curElement, null);
		    stylePaddingLeft = parseInt( style['paddingLeft'], 10 ) || 0;
		    stylePaddingTop = parseInt( style['paddingTop'], 10 ) || 0;
		    styleBorderLeft = parseInt( style['borderLeftWidth'], 10 ) || 0;
		    styleBorderTop = parseInt( style['borderTopWidth'], 10 ) || 0;
		}
	}
	function calculateOffset(curElement, event) {
      var element = curElement,
        offsetX = 0,
        offsetY = 0;

      // Find element offset
      if (element.offsetParent) {
        do {
          offsetX += element.offsetLeft;
          offsetY += element.offsetTop;
        } while (!!(element = element.offsetParent));
      }

      // Find Scroll offset
      element = curElement;
      do {
        offsetX -= element.scrollLeft || 0;
        offsetY -= element.scrollTop || 0;
      } while (!!(element = element.parentNode));

      // Add padding and border style widths to offset
      offsetX += stylePaddingLeft;
      offsetY += stylePaddingTop;

      offsetX += styleBorderLeft;
      offsetY += styleBorderTop;

      // Take into account any scrolling done
      offsetX += window.pageXOffset;
      offsetY += window.pageYOffset;

		return {X:offsetX, Y:offsetY};
    }
	// -------------------------------------

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
    //   class Interactive
    // -----------------------------------------

    var Interactive = function () {

        var opts = arguments[0];

        this.target = opts.target;
		setStyleValues( this.target );
		
        this.listeners = [];
		
		if ( opts.papplet && 'draw' in opts.papplet ) {
			var drawStored = opts.papplet.draw;
			opts.papplet.draw = (function(ia,pa,ds){
				return function(){
					ia.preDraw(pa);
					ds.apply(pa);
					ia.postDraw(pa);
				};
			})(this, opts.papplet, drawStored);
		}

        var events = [
            "mousemove", "mousedown", "mouseup", "click", "dblclick", 
            "mouseover", "mouseout", "mouseenter", "mouseleave", "mousewheel",
			"DOMMouseScroll"
        ];
        var eventDests = {
            "mousemove": "mouseMoved",
            "mousedown": "mousePressed",
            "dblclick": "mouseDoubleClicked",
            "mouseup": "mouseReleased",
			"mousewheel": "mouseScrolled",
			"DOMMouseScroll": "mouseScrolled"
        };
        for ( var e in events ) {
            (function(manager, target, event, meth){
                addEventListenerImpl( target, event, function( evt ){
//                    console.log( event );
//                    console.log( evt );
					
					var offset = calculateOffset( target, evt );
					
                    for ( var l in manager.listeners ) {
						if ( !( manager.listeners[l].isActive() ) ) continue;
                        if ( meth in manager.listeners[l] ) {
							if ( meth == 'mouseScrolled' )
								manager.listeners[l][meth]( evt.detail ? evt.detail * -1 : evt.wheelDelta / 40,
									 						evt.pageX - offset.X, 
															evt.pageY - offset.Y );
							else
								manager.listeners[l][meth]( evt.pageX - offset.X, 
															evt.pageY - offset.Y );
						}
                    }
                });
            })(this, this.target, events[e], eventDests[events[e]]);
        }

        this.add = function( listener ) {
            this.listeners.push( listener );
        }

		this.preDraw = function ( papplet ) {
			//console.log('pre');
		}
		
		this.postDraw = function ( papplet ) {
			//console.log('post');
			if ( this.listeners ) {
				for ( var l in this.listeners ) {
					if ( 'draw' in this.listeners[l] ) {
						this.listeners[l].draw( papplet );
					}
				}
			}
		}
    }

    // -----------------------------------------
    //   static
    // -----------------------------------------

    Interactive.make = function ( t ) {
        interactiveInstance = new Interactive({
            target: t.externals.canvas,
			papplet: t
        });
    }

    Interactive.add = function ( oneOrMoreListeners ) {
        var ae = null;
        if ( interactiveInstance ) {
            if ( Object.prototype.toString.call( oneOrMoreListeners ) === '[object Array]' ) {
                for ( var i = 0, k = oneOrMoreListeners.length; i < k; i++ ) {
                    interactiveInstance.add( new ActiveElement( oneOrMoreListeners[i] ) );
                }
                return null;
            } else {
                ae = new ActiveElement( oneOrMoreListeners );
                interactiveInstance.add( ae );
            }
        }
        return ae;
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

	Interactive.insideRect = function ( x, y, width, height, mx, my ) {
		return mx >= x && mx <= x+width && my >= y && my <= y+height;
	}

    Interactive.on = function () {
        if ( interactiveInstance ) {
            var ia = interactiveInstance;
            if ( !ia.eventBindings ) {
                ia.eventBindings = [];
            }
            var source, event, target, method;
            if ( arguments.length < 3 ) {
                throw( "Interactive.on() ... not enough arguments" );
            } else if (arguments.length == 3 ) {
                source = null;
                event = arguments[0];
                target = arguments[1];
                method = arguments[2];
            } else if ( arguments.length == 4) {
                source = arguments[0];
                event = arguments[1];
                target = arguments[2];
                method = arguments[3];
            }
            var binding = ia.eventBindings[event];
            if ( !binding ) {
                binding = [];
                ia.eventBindings[event] = binding;
            }
            binding.push({callback: function(){ target[method].apply(target, arguments) },source: source});
        }
    }

    Interactive.send = function () {
        if ( interactiveInstance ) {
            var ia = interactiveInstance;
            var source, event, args = [];
            if ( arguments.length < 2 ) {
                throw( "Interactive.send() ... not enough arguments" );
            } else if ( typeof arguments[0] === 'object' ) {
                source = arguments[0];
                event = arguments[1];
                for ( var i = 2, k = arguments.length; i < k; i++ ) {
                    args.push( arguments[i] );
                }
            } else {
                source = null;
                event = arguments[0];
                for ( var i = 1, k = arguments.length; i < k; i++ ) {
                    args.push( arguments[i] );
                }
            }
            var binding = ia.eventBindings[event];
            if ( binding ) {
                for ( var i = 0, k = binding.length; i < k; i++ ) {
                    if ( binding[i] && binding[i].source === source ) {
                        binding[i].callback.apply(null, args);
                    }
                }
            }
        }
    }

    // -----------------------------------------
    //   class ActiveElement
    // -----------------------------------------
	
    var ActiveElement = function () {

        this.listener = arguments[0];
        if ( !( 'isInside' in this.listener ) ) {
			if ( ('x' in this.listener) && ('y' in this.listener) && 
			     ('width' in this.listener) && ('height' in this.listener) ) {
				this.listener.isInside = function ( mx, my ) {
					return Interactive.insideRect( this.x, this.y, 
												   this.width, this.height, 
												   mx, my );
				}
			} else {
	        	alert( "Interactive: listener must implement\n" +
		   			   "\tpublic boolean isInside (float mx, float my)\n" +
		 			   "or must have fields\n" +
		  			   "\tfloat x, y, width, height;" );
			}
        }

        this.pressed = this.dragged = this.hover = false, this.activated = true;
        this.clickedMouseX = 0, this.clickedMouseY = 0;
        this.clickedPositionX = 0, this.clickedPositionY = 0;
        this.draggedDistX = 0, this.draggedDistY = 0;
        this.lastPressed = 0;
		this.activated = true;
        this.debug = false;

        this.setDebug = function ( tf ) {
            this.debug = tf ? true : false;
        }

        this.mousePressed = function ( mx, my ) {
            if ( !(this.activated) ) return;
			
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
            if ( !(this.activated) ) return;

            if ( this.listener.isInside( mx, my ) ) {
                if ( 'mouseDoubleClicked' in this.listener )
                    this.listener.mouseDoubleClicked( mx, my );
            }
        }

        this.mouseMoved = function ( mx, my ) {
            if ( !(this.activated) ) return;

            this.dragged = this.pressed;
            if ( this.dragged ) {
                this.draggedDistX = this.clickedMouseX - mx;
                this.draggedDistY = this.clickedMouseY - my;
                if ( 'mouseDragged' in this.listener )
                    this.listener.mouseDragged( mx, my, 
											    this.clickedPositionX - this.draggedDistX, 
											    this.clickedPositionY - this.draggedDistY );
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
                } else if ( nowInside ) {
                    if ( 'mouseMoved' in this.listener ) {
                        this.listener.mouseMoved( mx, my );
                    }
                }

                this.hover = nowInside;
            }
        }

        this.mouseReleased = function ( mx, my ) {
            if ( !(this.activated) ) return;

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

		this.mouseScrolled = function ( step, mx, my ) {
            if ( !(this.activated) ) return;
			//console.log( step );

			if ( this.listener.isInside( mx, my ) ) {
				if ( 'mouseScrolled' in this.listener ) {
					this.listener.mouseScrolled( step );
					return;
				}
			}
		}

		this.setActive = function ( state ) {
			this.activated = state;
			if ( this.listener ) {
				if ( 'setActive' in this.listener ) {
					this.listener.setActive( state );
					return;
				}
			}
		}
		
		this.isActive = function () {
			if ( this.listener ) {
				if ( 'isActive' in this.listener ) {
					return this.listener.isActive();
				}
			}
			return this.activated;
		}
		
		this.draw = function ( papplet ) {
			if ( !(this.activated) ) return;
			if ( this.listener ) {
				if ( 'draw' in this.listener ) {
					return this.listener.draw( papplet );
				}
			}
		}
    }

    return Interactive;

})();
} // check already imported