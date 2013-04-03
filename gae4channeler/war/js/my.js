
$(document).ready(function() {
  
  
  
  $(".fancybox").fancybox();
  
  $("img.postImg").lazyload({
 	 effect : "fadeIn"
  	});
  	
  	$("img.postMsg").lazyload({
 	 effect : "fadeIn"
  	});
  	
  	
  	
  	$(".postMsg").tooltip({
      position: {
        my: "center bottom-20",
        at: "center top",
        using: function( position, feedback ) {
          $( this ).css( position );
          $( "<div>" )
            .addClass( "arrow" )
            .addClass( feedback.vertical )
            .addClass( feedback.horizontal )
            .appendTo( this );
        }
      }
    });
    
    $(".postImg").tooltip({
      position: {
        my: "center bottom-20",
        at: "center top",
        using: function( position, feedback ) {
          $( this ).css( position );
          $( "<div>" )
            .addClass( "arrow" )
            .addClass( feedback.vertical )
            .addClass( feedback.horizontal )
            .appendTo( this );
        }
      }
    });
  	/*
  	//parte solo bindata in debug
  	$("img.postImg").bind('inview', function (event, visible) {
  		if (visible == true) {
    		$("img.postImg").trigger("sporty");
 			 } else {
    		// element has gone out of viewport
    		}
  		});
  		
  		*/
  		
  	$("#continue").click(function () {
      $(this).fadeOut();
      
      $(this).after(function(){
      	 $.get('page?page=h', function(data) {
  			alert('Load was performed.');
  			return data;
		});
      
      });
      });
     
      //$.get('page?page=h', function(data) {
 		//  $("#continue").after(data);
  			//alert('Load was performed.');
		//});
      //$(this).after("yeah");
    
});




