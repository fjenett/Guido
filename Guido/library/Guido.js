if(!Interactive)var Interactive=function(){var f,j,k,l,m,n=function(a){document.defaultView&&document.defaultView.getComputedStyle&&(a=document.defaultView.getComputedStyle(a,null),j=parseInt(a.paddingLeft,10)||0,k=parseInt(a.paddingTop,10)||0,l=parseInt(a.borderLeftWidth,10)||0,m=parseInt(a.borderTopWidth,10)||0)},p=function(a,b,c){if("addEventListener"in a)a.addEventListener(b,c);else{var i=a["on"+b];a["on"+b]=function(b){var o=c.apply(b.target,[b]);i&&i.apply(a,[b]);return o}}},d=function(a){this.target=
a.target;n(this.target);this.listeners=[];a.papplet&&"draw"in a.papplet&&(a.papplet.draw=function(b,a,c){return function(){b.preDraw(a);c();b.postDraw(a)}}(this,a.papplet,a.papplet.draw));var a="mousemove,mousedown,mouseup,click,dblclick,mouseover,mouseout,mouseenter,mouseleave,mousewheel,DOMMouseScroll".split(","),b={mousemove:"mouseMoved",mousedown:"mousePressed",dblclick:"mouseDoubleClicked",mouseup:"mouseReleased",mousewheel:"mouseScrolled",DOMMouseScroll:"mouseScrolled"},c;for(c in a)(function(b,
a,c,d){p(a,c,function(c){var e,g;e=a;var h=0;g=0;if(e.offsetParent){do{h=h+e.offsetLeft;g=g+e.offsetTop}while(e=e.offsetParent)}e=a;do{h=h-(e.scrollLeft||0);g=g-(e.scrollTop||0)}while(e=e.parentNode);h=h+j;g=g+k;h=h+l;g=g+m;h=h+window.pageXOffset;g=g+window.pageYOffset;e=h;for(var f in b.listeners)if(b.listeners[f].isActive()&&d in b.listeners[f])if(d=="mouseScrolled")b.listeners[f][d](c.detail?c.detail*-1:c.wheelDelta/40,c.pageX-e,c.pageY-g);else b.listeners[f][d](c.pageX-e,c.pageY-g)})})(this,this.target,
a[c],b[a[c]]);this.add=function(b){this.listeners.push(b)};this.preDraw=function(){};this.postDraw=function(){if(this.listeners)for(var b in this.listeners)"draw"in this.listeners[b]&&this.listeners[b].draw()}};d.make=function(a){f=new d({target:a.externals.canvas,papplet:a})};d.add=function(a){f.add(new q(a))};d.setActive=function(a,b){if(f)for(var c in f.listeners){var i=f.listeners[c];i.listener==a&&i.setActive(b)}};d.insideRect=function(a,b,c,i,d,f){return d>=a&&d<=a+c&&f>=b&&f<=b+i};var q=function(a){this.listener=
a;!1 in this.listener&&alert("Interactive: listener must implement\npublic boolean isInside (float mx, float my)");this.pressed=this.dragged=this.hover=!1;this.activated=!0;this.clickedMouseX;this.clickedMouseY;this.clickedPositionX;this.clickedPositionY;this.draggedDistX;this.draggedDistY;this.lastPressed=0;this.activated=!0;this.mousePressed=function(b,a){if(this.activated&&(this.pressed=this.listener.isInside(b,a)))this.clickedPositionX=this.listener.x,this.clickedPositionY=this.listener.y,this.clickedMouseX=
b,this.clickedMouseY=a,"mousePressed"in this.listener&&this.listener.mousePressed(b,a)};this.mouseDoubleClicked=function(b,a){this.activated&&this.listener.isInside(b,a)&&"mouseDoubleClicked"in this.listener&&this.listener.mouseDoubleClicked(b,a)};this.mouseMoved=function(b,a){if(this.activated)if(this.dragged=this.pressed)this.draggedDistX=this.clickedMouseX-b,this.draggedDistY=this.clickedMouseY-a,"mouseDragged"in this.listener&&this.listener.mouseDragged(b,a,this.clickedPositionX-this.draggedDistX,
this.clickedPositionY-this.draggedDistY);else{var d=this.listener.isInside(b,a);!d&&this.hover?"mouseExited"in this.listener&&this.listener.mouseExited(b,a):d&&!this.hover?"mouseEntered"in this.listener&&this.listener.mouseEntered(b,a):d&&"mouseMoved"in this.listener&&this.listener.mouseMoved(b,a);this.hover=d}};this.mouseReleased=function(a,c){this.activated&&(this.dragged&&(this.draggedDistX=this.clickedMouseX-a,this.draggedDistY=this.clickedMouseY-c),this.pressed&&"mouseReleased"in this.listener&&
this.listener.mouseReleased(a,c),this.pressed=this.dragged=!1)};this.mouseScrolled=function(a,c,d){this.activated&&this.listener.isInside(c,d)&&"mouseScrolled"in this.listener&&this.listener.mouseScrolled(a)};this.setActive=function(a){this.listener&&"setActive"in this.listener?this.listener.setActive(a):this.activated=a};this.isActive=function(){return this.listener&&"isActive"in this.listener?this.listener.isActive():this.activated};this.draw=function(){if(this.listener&&"draw"in this.listener)return this.listener.draw()}};
return d}();