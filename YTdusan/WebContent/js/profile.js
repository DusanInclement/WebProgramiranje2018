$(document).ready(function(e){ 
		
	var userName = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	      var videosDiv = $('#tab1');
	      var profileDiv = $('#profile');
	      var channelDiv = $('#popularChannels');
	      var infoDiv = $('#info')
	      
	      
	  	$.get('GetMineVideos',{"userName":userName},function(data){
	  		
	  		if(data.users.blocked == true){
	  			if(data.loggedInUser == null){
	  				alert("KORISNIK JE BLOKIRAN")
	  				window.location.replace("index.html")
	  			}else if(data.loggedInUser != null){
	  				if(data.loggedInUser.userName == userName){
	  					alert("BLOKIRANI STE OD STANE ADMINA")
	  					
	  				}else if(data.loggedInUser.userType != "ADMIN"){
	  					alert("KORISNIK JE BLOKIRAN")
		  				window.location.replace("index.html")
	  					
	  				}else if (data.loggedInUser.userType == "ADMIN"){
	  					alert("BLOKIRALI STE OVOG KORISNIKA")
	  				}
	  				
	  				
	  			}
	  		}
	  		
	  		videosDiv.empty();
	  		for(it in data.videos){
	  			var videoName = data.videos[it].videoName;	              
	             if(data.loggedInUser == null){
	            	 var str = ''; 
	             }
	  			else if(data.loggedInUser.userType == "ADMIN"){
	            	if(data.videos[it].blocked == false){
	            		var str = '<div id="videoDugmici">'+
			            '<button onclick ="editVideo(this.id)" id="editVideo='+data.videos[it].id+'" class="btn btn-default">EDIT</button>'+
			            '<button onclick="blockVideo(this.id)" id="blockVideo='+data.videos[it].id+'"  style="background-color:#f49842" class="btn btn-default">BLOCK</button>'+
			              '<button onclick="deleteVideo(this.id)" id="deleteVideo='+data.videos[it].id+'" style="background-color: #bc3838;" class="btn btn-default">DELETE</button>'+
			              
			              '</div>'  				
			              }else if(data.videos[it].blocked == true){
			            	  var str = '<div id="videoDugmici">'+
					            '<button onclick ="editVideo(this.id)" id="editVideo='+data.videos[it].id+'" class="btn btn-default">EDIT</button>'+
					            '<button onclick="unBlockVideo(this.id)" id="unBlockVideo='+data.videos[it].id+'"  style="background-color:#359332" class="btn btn-default">UNBLOCK</button>'+
					              '<button onclick="deleteVideo(this.id)" id="deleteVideo='+data.videos[it].id+'" style="background-color: #bc3838;" class="btn btn-default">DELETE</button>'+
					              
					              '</div>'  
			            	  
			              }
	            }else if(userName == data.loggedInUser.userName ){
            		
            		var str = '<div id="videoDugmici">'+
		            '<button onclick ="editVideo(this.id)" id="editVideo='+data.videos[it].id+'" class="btn btn-default">EDIT</button>'+
		              '<button onclick="deleteVideo(this.id)" id="deleteVideo='+data.videos[it].id+'" class="btn btn-default">DELETE</button>'+
						'</div>'	
            	}
	  			else{
	            	var str = '';
	            }
	  			
	  			
	  			videosDiv.append(
	  					'<div class="col-md-4" style="margin-bottom: 20px">'+
	  					'<a href="watch.html?id='+data.videos[it].id+'" target="_self">'+
	  					'<img id="videoImage" src="'+data.videos[it].pictureUrl+'" alt="video" style="width:100%">'+
	  					'<div class="caption">'+
	  						'<p id="titleBar">'+videoName+'</p>'+
	  					'</div>'+
	  					'<p id="videoInfo"><a href="profile.html?channel='+data.videos[it].owner.userName+'" id="channelName">'+data.videos[it].owner.userName+
	  					'</a><br>'+data.videos[it].views+' views<br>'+data.videos[it].numberOfLikes+' likes  |  '+data.videos[it].numberOfDislikes+' dislikes'+'<br>Posted on: '+data.videos[it].datePosted+'</p>'+
	  					'</a>'+str +	  					
	  					'</div>'
	  					);
	  			
	  		}
	  		
	  		$.get('SubServlet',{},function(data2){
	  			if(data2.logged != null){	
	  				
	  				if(userName==data2.logged){
  						var str = "";
  						
  					}
  					else{
  						
  						if(data2.subs != ""){
  							
  		  				for(sub in data2.subs){	
  						if(data2.subs[sub].ime == userName){
  							
	  						var str = '<button class="btn btn-default" onclick="Unsubscribe()" id="subscribe" style="background-color:#bc3838"><b>SUBSCRIBED</b></button>';	
	  						break;
  						} else{
  							
	  					var str = '<button class="btn btn-default" onclick="subscribe()" id="subscribe" ><b>SUBSCRIBE</b></button>';		  						
	  					}  	
  		  				}
  						}else{
  							
  							var str = '<button class="btn btn-default" onclick="subscribe()" id="subscribe" ><b>SUBSCRIBE</b></button>';
  							
  						}
  					}
	  					  				
	  			}else{
	  				
	  				var str = '<button class="btn btn-default" onclick="subscribe()" id="subscribe" ><b>SUBSCRIBE</b></button>';
	  				
	  			}	 
	  			
	  			
	  		profileDiv.empty();
	  		
	  			profileDiv.append(
	  			'<center>'+
	  	        '<a><img src="'+ data.users.profileUrl +
	  	        '" name="aboutme" width="140" height="140" class="img-circle"></a>' +
	  	        '<h3>' + data.users.firstName + ' ' + data.users.lastName + '</h3>' +
	  	        '<h4>' + data.numOfSubs + ' subers </h4>'+
	  	        '<h6>' + data.users.registrationDate+'</h6>'+	  	        
	  	        '<em>' + data.users.email +'</em>' + '<br>'+str +
	  			'</center>'
	  	        );
	  		});	
	  		infoDiv.empty();
	  		
	  			infoDiv.append(
	  					
	  					'<div class="col-sm-5 col-xs-6 tital " >First Name:</div><div class="col-sm-7 col-xs-6 ">'+data.users.firstName +' </div>' +
	  				    ' <div class="clearfix"></div>' +
	  				'<div class="bot-border"></div>'+

	  				'<div class="col-sm-5 col-xs-6 tital " >Last Name:</div><div class="col-sm-7">' + data.users.lastName +'</div>'+
	  				 ' <div class="clearfix"></div>'+
	  				'<div class="bot-border"></div>'+

	  				'<div class="col-sm-5 col-xs-6 tital " >Email:</div><div class="col-sm-7">' + data.users.email + '</div>'+
	  				'  <div class="clearfix"></div>'+
	  				'<div class="bot-border"></div>'+

	  				'<div class="col-sm-5 col-xs-6 tital " >User Type:</div><div class="col-sm-7">' + data.users.userType +'</div>' +

	  				  '<div class="clearfix"></div>' +
	  				'<div class="bot-border"></div>'+

	  				'<div class="col-sm-5 col-xs-6 tital " >Registration Date:</div><div class="col-sm-7">' + data.users.registrationDate +  '</div>'+

	  				 ' <div class="clearfix"></div>'+
	  				'<div class="bot-border"></div>'+

	  				'<div class="col-sm-5 col-xs-6 tital " >User Name:</div><div class="col-sm-7">' +data.users.userName +' </div>'		
	  			
	  			
	  			
	  			
	  			
	  			
	  			
	  			
	  			)
	  			
	  			
	  			
	  			
	  			
	  			
	  				
	  			channelDiv.empty();
	  			for(t in data.subs){
	  					
	  					channelDiv.append(
	  							'<div class="col-md-4 col-sm-4 col-xs-6">'+
	  							'<center>'+
	  							'<a href="profile.html?channelName='+data.subs[t].userName+'"><img src="'+data.subs[t].profileUrl+'" name="aboutme" width="140" height="140" class="img-circle"></a>'+
	  							
	  							'<a href="profile.html?channelName='+data.subs[t].userName+'" target="_self"><p id="profileName">'+data.subs[t].userName+'</p></a>'+
		  						 
		  						'<div class="caption">'+
		  						'<p id="email">'+data.subs[t].email+'</p>'+
		  						'</div>'+
		  						'<center>'+	 
		  						'</div>'
		  						);		
	  		
	  				}
	  		
	  	});
  
	  
	     
  
	  	
	  	
	  	
	  $.get('LoginServlet',{},function(data){
		  
		  $.get('ChangeUser',{"userName":userName},function(data2){
		  
		  $("#firstNameEdit").val(data2.user.firstName);		
		 $("#lastNameEdit").val( data2.user.lastName);
		  $("#usernameEdit").val(data2.user.userName);
		  $("#passwordEdit").val(data2.user.password);
		  $("#emailEdit").val(data2.user.email);
		  $("#profileEdit").val(data2.user.profileUrl);
		  
		  });
		     
		  if (userName == data.user.userName || data.user.userType == "ADMIN") {
			  document.getElementById("edit").style.display = "block";
			  
			  
		}
		  else {
			  document.getElementById("edit").style.display = "none";
		}
		  
		  event.preventDefault()
		  
	  });
	  	
	  
	  $('#editSubmit').on('click', function(event) {
		  
		  var fname = document.getElementById("firstNameEdit").value.trim();
          var lname = document.getElementById("lastNameEdit").value.trim();
          var userN = document.getElementById("usernameEdit").value.trim();
          var passP = document.getElementById("passwordEdit").value.trim();
          var email = document.getElementById("emailEdit").value.trim();
          var profileUrl = document.getElementById("profileEdit").value.trim();
          
        
          
         $.post('ChangeUser',{'userName':userN,'password':passP,'firstName':fname,'lastName':lname,
  			'email':email,'profileUrl':profileUrl},function(data){
  				
  				
  				window.location.reload();
  				
  			});
		  
		  
		 
		  
		 event.preventDefault()
		  
	  });
	  
	  $(".btn-pref .btn").click(function () {
		    $(".btn-pref .btn").removeClass("btn-primary").addClass("btn-default");
		    // $(".tab").addClass("active"); // instead of this do the below 
		    $(this).removeClass("btn-default").addClass("btn-primary");   
		});
	
});

function subscribe(){
	$.get('LoginServlet',{},function(data){
		 var mainUser = window.location.search.slice(1).split('&')[0].split('=')[1];
		if(data.user != null){
			 var subscriber = data.user.userName; 
			 }else{
				 var subscriber = null;
			 }			
		if(data.user == null){
			 $('#myModal').modal('show');			
		}
		else{
			
			$.get('LoginServlet',{},function(data3){
				if(data3.user.blocked==true){
					alert("BLOKIRANI STE");
					
				}else{
					$.post('SubServlet',{"mainUser":mainUser,"subscriber":subscriber},function(data2){	
						window.location.reload();
					});	
				
				}
				});
			
			
						
		}
		
		
		
		
	});
	
}
function Unsubscribe(){
	$.get('LoginServlet',{},function(data){
		 var mainUser = window.location.search.slice(1).split('&')[0].split('=')[1];
		if(data.user != null){
			 var subscriber = data.user.userName; 
			 }else{
				 var subscriber = null;
			 }	
		
		
		$.get('LoginServlet',{},function(data3){
			if(data3.user.blocked==true){
				alert("BLOKIRANI STE");
				
			}else{
		
		
		$.post('UnSubServlet',{"mainUser":mainUser,"subscriber":subscriber},function(data2){	
			window.location.reload();
		});
			}
			
		});
		
		
		
	});
	
	
}

function editVideo(clicked_id){
	
	
	var editId = clicked_id;
	var videoId = editId.slice(1).split('=')[1];
	
	$.get('VideoSevlet',{"videoId":videoId},function(data){
		 $("#videoUrlEdit").val(data.video.videoUrl);
		 $("#pictureUrlEdit").val(data.video.pictureUrl);
		 $("#videoNameEdit").val(data.video.videoName);
		 $("#descriptionEdit").val(data.video.description);
		 if(data.video.visibility == "PUBLIC"){
			 document.getElementById("publicRadioEdit").checked = true;
			 }
		 else if(data.video.visibility == "PRIVATE"){
			 document.getElementById("privateRadioEdit").checked = true;
		 }
		 else{
			 document.getElementById("unlistedRadioEdit").checked = true;
		 }
		 
		 if(data.video.commentsEnabled == true){
			 document.getElementById("commYesEdit").checked = true;
		 }else{
			 document.getElementById("commNoEdit").checked = true;
		 }
		 
		 if(data.video.ratingEnabled == true){
			 document.getElementById("ratYesEdit").checked = true;
		 }else{
			 document.getElementById("ratNoEdit").checked = true;
		 }
		 
		 $('#modalEditVideo').modal('show');
		 
		 $('#editVideoPost').on('click', function(event) {
				
			 var videoUrl = $('#videoUrlEdit').val();
		    	var pictureUrl = $('#pictureUrlEdit').val();
		    	var videoName = $('#videoNameEdit').val();
		    	var description = $('#descriptionEdit').val();
		    	if(document.getElementById("publicRadioEdit").checked ==true){
		    		var visibility = "PUBLIC";
		    	}else if (document.getElementById("privateRadioEdit").checked ==true){
		    		var visibility = "PRIVATE";
		    	}else if(document.getElementById("unlistedRadioEdit").checked ==true){
		    		var visibility = "UNLISTED";
		    	}
		    	
		    	if(document.getElementById("commYesEdit").checked ==true){
		    		var comments = "Yes";
		    	}else if (document.getElementById("commNoEdit").checked ==true){
		    		var comments = "No";
		    	}
		    	
		    	if(document.getElementById("ratYesEdit").checked ==true){
		    		var rating = "Yes";
		    	}else if(document.getElementById("ratNoEdit").checked ==true){
		    		var rating = "No";
		    	}
		    	
		    	if(videoUrl == ''){
		    		alert("Morate popuniti Video URL:");
		    		
		    	}else if(pictureUrl == ''){
		    		alert("Morate popuniti Picture URL:");
		    		
		    	}else if(videoName == ''){
		    		alert("Morate popuniti Video Name");
		    		
		    	}else if(description == ''){
		    		alert("Morate popuniti Description");
		    		
		    	}else{
		    		$.post('EditVideoServlet',{'videoId':videoId,'videoUrl':videoUrl,'pictureUrl':pictureUrl,'videoName':videoName,'description':description,
		        		'visibility':visibility,'comments':comments,'rating':rating},function(data){
		        			document.getElementById('videoUrlEdit').value = '';
		        			document.getElementById('pictureUrlEdit').value = '';
		        			document.getElementById('videoNameEdit').value = '';
		        			document.getElementById('descriptionEdit').value = '';
		        			location.reload();
		        			
		        			
		        		})
		        		 event.preventDefault()
		        		

		    		
		    		
		    	}
				
				
				
				
			});
		
	});
	
}


function deleteVideo(clicked_id){
	var deleteId = clicked_id;
	var videoId = deleteId.slice(1).split('=')[1];
	$.post('WatchServlet',{"commentId":commentId},function(data){
		
		window.location.reload();
		
		
	});
}

function blockVideo(clicked_id){
	
	
	var blockId = clicked_id;	
	var videoId = blockId.slice(1).split('=')[1];
	var block = "Yes";
	
	$.get('EditVideoServlet',{"videoId":videoId,"block":block},function(data){
		
		window.location.reload();
		
	});
	
	
}

function unBlockVideo(clicked_id){
	
	
	var blockId = clicked_id;	
	var videoId = blockId.slice(1).split('=')[1];
	var block = "No";
	
	$.get('EditVideoServlet',{"videoId":videoId,"block":block},function(data){
		
		window.location.reload();
		
	});
	
	
	
	
	
}

function deleteVideo(clicked_id){
	var delId = clicked_id;	
	var videoId = delId.slice(1).split('=')[1];
	alert(videoId);
	
	
	
	$.get('UpdateCommentServlet',{"videoId":videoId},function(data){
		
		window.location.reload();
	});
	
}





    

        