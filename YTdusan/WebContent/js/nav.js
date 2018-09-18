

$(document).ready(function() { // izvršava se nakon što se izgradi DOM stablo HTML dokumenta
    // keširanje referenci da se ne bi ponavljale pretrage kroz DOM stablo
    var userNameInput = $('#usernameLogin');
    var passwordInput = $('#passwordLogin');

    var messageParagraph = $('#messageParagraph');

    $('#logInSubmit').on('click', function(event) { // izvršava se na klik na dugme
        var userName = userNameInput.val();
        var password = passwordInput.val();
        console.log('userName: ' + userName);
        console.log('password: ' + password);
        
        if (userName == '' || password == '') {
            alert("Morate popuniti polja username i password")
        }
        else {
            $.post('LoginServlet',{'userName': userName, 'password': password},function(data){
            
                if(data.status == "false"){
                alert("Neispravna lozinka ili username")
                } else {               
                  window.location.reload();
                
                }   
        
            });
        }
       
        event.preventDefault();
  
    });
    
    $('#registerSubmit').on('click',function(event){
       
    	var fname = document.getElementById("firstNameRegister").value.trim();
            var lname = document.getElementById("lastNameRegister").value.trim();
            var userN = document.getElementById("usernameRegister").value.trim();
            var passP = document.getElementById("passwordRegister").value.trim();
            var email = document.getElementById("emailRegister").value.trim();
            var rlol = false;
            var profileUrl = document.getElementById("profileUrlRegister").value.trim();
            
            $.post('RegisterServlet',{'userName':userN,'password':passP,'firstName':fname,'lastName':lname,
            			'email':email,'profileUrl':profileUrl,'lol':rlol},function(data){
            				
            				if (data.status == "taken") {
								alert("Postoji username")
							}
            				else {
								location.reload();
							}
            				
            				
            			}
            		)
            		event.preventDefault();	
           
    });
    
 
    $('#Serach').on('click', function(event) {
	      var parameter = document.getElementById("searchParameter").value.trim();
	      
	      var pretragaDiv = $('#recommendedDiv');
	      var channelDiv = $('#userSearch')
	      
	      
	  	$.get('SearchServlet',{"parameter":parameter},function(data){
	  		if (document.getElementById("videoChecboxSerach").checked ==true && document.getElementById("userChecboxSerach").checked == true) {		
	  			
	  		pretragaDiv.empty();
	  		for(it in data.videos){
	  			var videoName = data.videos[it].videoName;
	  			pretragaDiv.append(
	  					'<div class="col-md-4">'+
	  					'<a href="watch.html?id='+data.videos[it].id+'" target="_self">'+
	  					'<img id="videoImage" src="'+data.videos[it].pictureUrl+'" alt="video" style="width:100%">'+
	  					'<div class="caption">'+
	  						'<p id="titleBar">'+videoName+'</p>'+
	  					'</div>'+
	  					'<p id="videoInfo"><a href="profile.html?channel='+data.videos[it].owner.userName+'" id="channelName">'+data.videos[it].owner.userName+'</a><br>'+data.videos[it].views+' views<br>Posted on: '+data.videos[it].datePosted+'</p>'+
	  					'</a>'+
	  					'</div>'
	  					);
	  		 	
	  		}
	  		
  			channelDiv.empty();
	  		for(t in data.users){
				
					channelDiv.append(
							'<div class="col-md-4 col-sm-4 col-xs-6">'+
							'<center>'+
							'<a href="profile.html?channelName='+data.users[t].userName+'"><img src="'+data.users[t].profileUrl+'" name="aboutme" width="140" height="140" class="img-circle"></a>'+
							
							'<a href="profile.html?channelName='+data.users[t].userName+'" target="_self"><p id="profileName">'+data.users[t].userName+'</p></a>'+
  						 
  						'<div class="caption">'+
  						'<p id="email">'+data.users[t].email+'</p>'+
  						'</div>'+
  						'<center>'+	 
  						'</div>'
  						);		
		
				}
	  		
	  		
	  		}
	  		else if (document.getElementById("videoChecboxSerach").checked ==true && document.getElementById("userChecboxSerach").checked == false) {
	  			
	  			
	  			pretragaDiv.empty();
	  			channelDiv.empty();
		  		for(it in data.videos){
		  			var videoName = data.videos[it].videoName;
		  			pretragaDiv.append(
		  					'<div class="col-md-4">'+
		  					'<a href="watch.html?id='+data.videos[it].id+'" target="_self">'+
		  					'<img id="videoImage" src="'+data.videos[it].pictureUrl+'" alt="video" style="width:100%">'+
		  					'<div class="caption">'+
		  						'<p id="titleBar">'+videoName+'</p>'+
		  					'</div>'+
		  					'<p id="videoInfo"><a href="profile.html?channel='+data.videos[it].owner.userName+'" id="channelName">'+data.videos[it].owner.userName+'</a><br>'+data.videos[it].views+' views<br>Posted on: '+data.videos[it].datePosted+'</p>'+
		  					'</a>'+
		  					'</div>'
		  					);
		  		 	
		  		}
	  			
	  			
				
			}
	  		
	  		else if (document.getElementById("videoChecboxSerach").checked ==false && document.getElementById("userChecboxSerach").checked == true) {
	  			pretragaDiv.empty();
	  			channelDiv.empty();
		  		for(t in data.users){
					
						channelDiv.append(
								'<div class="col-md-4 col-sm-4 col-xs-6">'+
								'<center>'+
								'<a href="profile.html?channelName='+data.users[t].userName+'"><img src="'+data.users[t].profileUrl+'" name="aboutme" width="140" height="140" class="img-circle"></a>'+
								
								'<a href="profile.html?channelName='+data.users[t].userName+'" target="_self"><p id="profileName">'+data.users[t].userName+'</p></a>'+
	  						 
	  						'<div class="caption">'+
	  						'<p id="email">'+data.users[t].email+'</p>'+
	  						'</div>'+
	  						'<center>'+	 
	  						'</div>'
	  						);		
			
					}
				
			}
	  		
	  		else {
				alert("Morate otkaciti bar jedan checkBox za pretragu");
				pretragaDiv.empty();
	  			channelDiv.empty();
			}	  		
	  		
	  	
	  	 	
	  	});
    
	  
	  	event.preventDefault()
	  	
    
    
});
    $(document).on('click', '.dropdown-menu', function (e) {
    	  e.stopPropagation();
    	
    	  event.preventDefault()
    	 
    	    });
    
    $('#uploadVideoPost').on('click', function(event) {
    	
    	
    	
    	
    	var videoUrl = $('#videoUrlUpload').val();
    	var pictureUrl = $('#pictureUrlUpload').val();
    	var videoName = $('#videoNameUpload').val();
    	var description = $('#descriptionUpload').val();
    	if(document.getElementById("publicRadio").checked ==true){
    		var visibility = "PUBLIC";
    	}else if (document.getElementById("privateRadio").checked ==true){
    		var visibility = "PRIVATE";
    	}else if(document.getElementById("unlistedRadio").checked ==true){
    		var visibility = "UNLISTED";
    	}
    	
    	if(document.getElementById("commYes").checked ==true){
    		var comments = "Yes";
    	}else if (document.getElementById("commNo").checked ==true){
    		var comments = "No";
    	}
    	
    	if(document.getElementById("ratYes").checked ==true){
    		var rating = "Yes";
    	}else if(document.getElementById("ratNo").checked ==true){
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
    		$.get('LoginServlet',{},function(data){
    			if(data.user.blocked==true){
    				alert("BLOKIRANI STE");
    				$('#modalUpload').modal('hide');
    				}else{
    					$.post('VideoSevlet',{'videoUrl':videoUrl,'pictureUrl':pictureUrl,'videoName':videoName,'description':description,
    		        		'visibility':visibility,'comments':comments,'rating':rating},function(data){
    		        			document.getElementById('videoUrlUpload').value = '';
    		        			document.getElementById('pictureUrlUpload').value = '';
    		        			document.getElementById('videoNameUpload').value = '';
    		        			document.getElementById('descriptionUpload').value = '';
    		        			location.reload();
    		        			
    		        			
    		        		})
    					
    					
    					
    				}
    			
    		
    		
    		
    		});
    		
    		
        		 event.preventDefault()
        		

    		
    		
    	}
    	
    	
    	
    		
    	
    });
    
    
    
    
    
    
    
    
});



	
	
	

	
	
/*function proba(){
	
	$('#Serach').on('click', function(event) {
	      var parameter = document.getElementById("searchParameter").value.trim();
	      var videosDiv = $('#search');
	      
	  	$.get('SearchServlet',{"parameter":parameter},function(data){	
	  	
	  		for(v in data.videos){
	  			var videoId = data.videos[1].id;
	  			
	  			videosDiv.append(
	  					'<p>'+videoId+'</p>'
	  			
	  			);
	  			
	  			
	  		}
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		for(it in data.videos){
	  			var videoName = data.videos[it].videoName;
	  			videosDiv.append(
	  					'<div class="col-md-4">'+
	  					'<a href="videoPage.html?id='+data.videos[it].id+'" target="_self">'+
	  					'<img id="videoImage" src="'+data.videos[it].pictureUrl+'" alt="video" style="width:100%">'+
	  					'<div class="caption">'+
	  						'<p id="titleBar">'+videoName+'</p>'+
	  					'</div>'+
	  					'<p id="videoInfo"><a href="channelPage.html?channel='+data.videos[it].owner.userName+'" id="channelName">'+data.videos[it].owner.userName+'</a><br>'+data.videos[it].views+' views<br>Posted on: '+data.videos[it].datePosted+'</p>'+
	  					'</a>'+
	  					'</div>'
	  					);
	  			counter++;
	  		}		
	  		
	  		
	  	});
	      
	      });
	    
	    
	
	
	
	
}*/

/*function search(){
	var text = $('#searchParameter').val().trim();
	if(text!=""){
		alert(text);
		document.location.href = "search.html?result_for="+text;
		
		
	}
	event.preventDefault()
}*/



function logout(){
    $.ajax
    ({
        type: "GET",
        url: "LogoutServlet",
        success: function(data,status){
            
            window.location.replace("index.html");
        }
    })
    
};

window.onload = function() {
    //GET LOGIN
    $.ajax
    ({
        type: "GET",
        url: "LoginServlet",
        dataType: "json",
        success: function(data,status){
            if(data.user == null){
                document.getElementById("navbarLoggedIn").style.display = "none";
                document.getElementById("navbarNotLoggedIn").style.display = "block";
                

            }else{
            	var rola = data.user.userType;
            	if (rola == "ADMIN") {
            		document.getElementById("adminPanel").style.display = "block";
            		
            		var adminPanel = $("#adminTable").find('tbody');
            		$.get('getAllUsersServlet',{},function(data2){
            			for(us in data2.users){
            				
            				if(data2.users[us].userName == data.user.userName){
            					
            				}
            				else{
            				if(data2.users[us].blocked == true){
            					var str = "Yes"
            				}else{
            					var str = "No"
            				};
            			adminPanel.append(
            					'<tr class = "item">'+
            					'<td>'+'<a href="profile.html?channelName='+data2.users[us].userName+'"><b>'+data2.users[us].userName+'</b></a></td>'+
                                '<td>'+data2.users[us].firstName+'</td>'+
                                '<td>'+data2.users[us].lastName+'</td>'+
                                '<td>'+data2.users[us].userType+'</td>'+
                                '<td>'+str+'</td>'+
                                '<td class="text-center">'+
                                '<button id="adminBlock='+data2.users[us].userName+'" class="btn btn-info btn-xs" onclick="adminBlock(this.id)"><span class="glyphicon glyphicon-alert"></span> Block</button>'+
                                '<button id="adminType='+data2.users[us].userName+'" class="btn btn-info btn-xs" onclick="adminType(this.id)"><span class="glyphicon glyphicon-transfer"></span> Type</button>'+
                                '<button id="adminDelete='+data2.users[us].userName+'" class="btn btn-danger btn-xs" onclick="adminDelete(this.id)"><span class="glyphicon glyphicon-remove"></span> Delete</button>'+
                                '</td>'+
                           '</tr>'	
            					
            					
            			
            			
            			);
            			}
            			
            			
            			}
            			
            			
            		});
            		
            		
				}else {
					document.getElementById("adminPanel").style.display = "none";
					
				}
            	var name = data.user.userName;
            	var tempLink = 'profile.html?channel='+data.user.userName;
    			$('#myChannel').attr("href", tempLink);
    			/*var temp = '<span class="glyphicon glyphicon-user"></span>'+" "+data.user.userName+" "+'<span class="caret"></span>';*/
    			$('#myChannel').text(name);
    			
    			
                document.getElementById("navbarLoggedIn").style.display = "block";
                document.getElementById("navbarNotLoggedIn").style.display = "none";
            }
            
            
            event.preventDefault()
        }
    })
}


function adminBlock(id){
	var userName = id.slice(1).split('=')[1];
	$.get('ChangeUser',{"userName":userName},function(data){
		var blocked ="";
		
		if(data.user.blocked == false){
			blocked = "true";
			$.post('AdminServlet',{"userName":userName,"primer":"1","blocked":blocked},function(data){
				
				window.location.reload();
				 $('#adminPanelModal').modal('show');
				
			});
		}else{
			blocked = "false";
			$.post('AdminServlet',{"userName":userName,"primer":"1","blocked":blocked},function(data){
				
				window.location.reload();
				 $('#adminPanelModal').modal('show');
				
			});
		}
		
		
	})
	 
}
function adminType(id){
	var userName = id.slice(1).split('=')[1];
	$.get('ChangeUser',{"userName":userName},function(data){
		var userType ="";
		var blocked ="";
		
		if(data.user.userType == "USER"){
			userType = "ADMIN";
			$.post('AdminServlet',{"userName":userName,"primer":"2","userType":userType,"blocked":blocked},function(data){
				
				window.location.reload();
				
				
			});
		}else{
			userType = "USER";
			$.post('AdminServlet',{"userName":userName,"primer":"2","userType":userType,"blocked":blocked},function(data){
				
				
				window.location.reload();
				
			});
			
			
		}
		
	});
}
function adminDelete(id){
	var userName = id.slice(1).split('=')[1];
	var userType ="";
	var blocked = "";
	
	$.post('AdminServlet',{"userName":userName,"primer":"3","userType":userType,"blocked":blocked},function(data){
		
		
		window.location.reload();
		
	});
}


function serachSher(){
 var owner = $('#userNameSerach').val().trim();
 var videoName = $('#videoNameSerach').val().trim();
 var numberOfViews = $('#viewsSearch1').val().trim();
 var date = $('#dateSearch1').val().trim();
 var pretragaDiv = $('#recommendedDiv');
 var channelDiv = $('#userSearch');
 
 if(document.getElementById("viewsSearch").checked ==true){
		var sortBy = "views";
	}else if(document.getElementById("dateSearch").checked ==true){
		var sortBy = "datePosted";
	}else if(document.getElementById("ownerSearch").checked ==true){
		var sortBy = "owner";
	}else if(document.getElementById("videoSearch").checked ==true){
		var sortBy = "videoName";
	}
 
 if(document.getElementById("descSearch").checked ==true){
		var how = "DESC";
	}else if(document.getElementById("ascSearch").checked ==true){
		var how = "ASC";
	}
 $.get('SherSearchServlet',{"owner":owner,"videoName":videoName,"numberOfViews":numberOfViews,"date":date,"sortBy":sortBy,"how":how},function(data){
	 channelDiv.empty();
	 pretragaDiv.empty();
		for(it in data.videos){
			var videoName = data.videos[it].videoName;
			pretragaDiv.append(
					'<div class="col-md-4">'+
					'<a href="watch.html?id='+data.videos[it].id+'" target="_self">'+
					'<img id="videoImage" src="'+data.videos[it].pictureUrl+'" alt="video" style="width:100%">'+
					'<div class="caption">'+
						'<p id="titleBar">'+videoName+'</p>'+
					'</div>'+
					'<p id="videoInfo"><a href="profile.html?channel='+data.videos[it].owner.userName+'" id="channelName">'+data.videos[it].owner.userName+'</a><br>'+data.videos[it].views+' views<br>Posted on: '+data.videos[it].datePosted+'</p>'+
					'</a>'+
					'</div>'
					);
		 	
		}
		
	});
 

	


$('#SearchModal').modal('hide');
document.getElementById('userNameSerach').value = '';
document.getElementById('videoNameSerach').value = '';
document.getElementById('viewsSearch1').value = '';
document.getElementById('dateSearch1').value = '';


event.preventDefault()
}


    
    
