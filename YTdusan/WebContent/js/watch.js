$(document).ready(function(e){ 
		
	var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	
	$.get('WatchServlet',{"videoId":videoId},function(data){
		var player = $('#player');		
		var naslov = $('#naslov');		
		var brojPregledaDiv = $('#brojPregledaDiv');		
		var likeDisLike = $('#likeDisLike');
		var pictureProfile = $('#pictureProfile');
		var owner = $('#owner');
		var dateVideo = $('#dateVideo');
		var bottom_row = $('#bottom-row');
		var imgUploadComm = $('#imgUploadComm');
		var komentar = $('#komentar');
		var konetejnerSadrzaj = $('#videoKonetejner');
		var subDiv = $('#subDiv');
		var simpleBox = $('#simpleBox');
		var loggedUser = null;
		if(data.video.visibility == "PRIVATE"){
			if(data.loggedInUser == null){
				alert("VIDEO JE PRIVATE");
				window.location.replace("index.html");
			}else if( data.video.owner.userName != data.loggedInUser.userName){
				if(data.loggedInUser.userType != "ADMIN"){
					alert("VIDEO JE PRIVATE");
					window.location.replace("index.html");
				}
			} 
			
		}
		if(data.loggedInUser == null){
			if(data.video.owner.blocked == true){
				alert("VLASNIK OVOG VIDEO JE BLOKIRAN PRIJAVITE SE DA VIDITE SADRZAJ");
				window.location.replace("index.html");
				}
		}else{
			if(data.video.owner.blocked == true){
				alert("VLASNIK OVOG VIDEO JE BLOKIRAN");
				/*window.location.replace("index.html");*/
			}
		}
		
		
		if(data.video.blocked == true){
			if(data.loggedInUser == null){
				alert("VIDEO JE BLOKIRAN");
				window.location.replace("index.html");
				
			}else if(data.loggedInUser.userType != "ADMIN"){
				if (data.video.owner.userName == data.loggedInUser.userName){
					alert("VAS VIDEO JE BLOKIRAN");
					
				}else if(data.video.owner.userName != data.loggedInUser.userName){
					alert("VIDEO JE BLOKIRAN");
					window.location.replace("index.html");
				}				
			}		
		}
		if(data.loggedInUser == null){
			
		}
		else{
			loggedUser = data.loggedInUser;
		}
		
		
		
		player.append(
			'<iframe width="100%" height="400px" src="'+
			data.video.videoUrl+
			'" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>'
		);
		naslov.append(
			'<h3>'+data.video.videoName+'</h3>'
		);
		brojPregledaDiv.append(
			'<h4 id="brojPregleda">'+data.video.views+' views</h4>'
		);
		
		likeDisLike.append(
				'<button id="likeVideo" onclick="likeVideo()" class="btn btn-default">Like</button>'+
	            '<p id="numLikesVideo">'+data.video.numberOfLikes+'</p>'+
	         '<button id="disLikeVideo" onclick="disLikeVideo()" class="btn btn-default">Dislike</button>'+
	         '<p id="numDisLikesVideo">'+data.video.numberOfDislikes+'</p>'
			);
		
		
		 $.get('LikeServlet',{},function(data1){
			 for(lk in data1.like){
				 if(data1.like[lk].video.id == videoId){
					 if(data1.like[lk].likeOrDislike == true){
						 likeDisLike.empty();
						 likeDisLike.append(
									'<button id="likeVideo" onclick="likeVideo()" class="btn btn-default" style="background-color:#60c7c1;color:white">Liked</button>'+
						            '<p id="numLikesVideo">'+data.video.numberOfLikes+'</p>'+
						         '<button id="disLikeVideo" onclick="disLikeVideo()" class="btn btn-default">Dislike</button>'+
						         '<p id="numDisLikesVideo">'+data.video.numberOfDislikes+'</p>'
								);			 
					 }else{
						 likeDisLike.empty();
						 likeDisLike.append(
									'<button id="likeVideo" onclick="likeVideo()" class="btn btn-default">Like</button>'+
						            '<p id="numLikesVideo">'+data.video.numberOfLikes+'</p>'+
						         '<button id="disLikeVideo" onclick="disLikeVideo()" class="btn btn-default"  style="background-color:#bc3838;color:white">Disliked</button>'+
						         '<p id="numDisLikesVideo">'+data.video.numberOfDislikes+'</p>'
								);
					 }				 
				 }				 
				 else{
					
				 }			 
				 
			 }	 
	 });
		 if(data.video.ratingEnabled == false){
				if(data.loggedInUser == null){
					likeDisLike.empty()
				}else if(data.loggedInUser !=null){
					if(data.video.owner.userName != data.loggedInUser.userName){
						likeDisLike.empty()
					}else if(data.loggedInUser.userType != "ADMIN"){
						likeDisLike.empty()
					}
				}
				
				
			}
		 
		
		 
		 subDiv.append(
					'<button class="btn btn-default" onclick="subscribe()" id="subscribe" ><b>SUBSCRIBE</b></button>'
				);
		

			$.get('SubServlet',{},function(data2){
				for (ll in data2){
					if(data.ownerVideo.userName !=  data2.logged){
						
						for (ll in data2.subs){
						if(data.ownerVideo.userName == data2.subs[ll].ime){
							
							subDiv.empty();
							subDiv.append(
									'<button class="btn btn-default" onclick="Unsubscribe()" id="subscribe" style="background-color:#bc3838"><b>SUBSCRIBED</b></button>'
								);
							
						}else{
		
						}
						}
					}else{
						subDiv.empty();						
					}					
				}
				
				 
			var logged = data2.logged;	 
				
			});
		
			/*subDiv.append(
					'<button class="btn btn-default" id="subscribe" ><b>SUBSCRIBE</b></button>'
				);*/
		
			
		
		
		pictureProfile.append(
		'<a href="profile.html?channel='+data.ownerVideo.userName+'"><img id="profilnaSlika" src="'+data.ownerVideo.profileUrl+'"></a>'	
		);		
		owner.append(
		'<a href="profile.html?channel='+data.ownerVideo.userName+'"><h5 id="ownerName"><b id="ownerNameB">'+data.ownerVideo.userName+'</b></h5></a>'		
		);
		dateVideo.append(
		'<h5 id="postDate">Published: '+data.video.datePosted+'</h5>'
		);
		bottom_row.append(
		'<h4>Description:'+
         '</h4>'+
         '<textarea id="description" readonly="true" disabled="yes" name="text" cols="100" rows="1">'+
         data.video.description+'</textarea>'
		);
		
		
		if(loggedUser == null){
			
		}
		else {
			imgUploadComm.append(
					'<img id="profilnaKomnetatora" src="'+loggedUser.profileUrl+'">'		
					);
		}
		
		
		
		komentar.empty()
		
		 /**/
		/*alert(loggedUser.userType)*/
		for(kom in  data.comments){
		if(loggedUser != null){
			if(data.comments[kom].user.userName == loggedUser.userName || loggedUser.userType == "ADMIN"){
				var editDel = '<div id="komentarDugmici">'+
                '<button onclick ="editKomentar(this.id)" id="editKomentar='+data.comments[kom].id+'" class="editKomentar btn btn-default">EDIT</button>'+
                '<button onclick="deleteKomentar(this.id)" id="deleteKomentar='+data.comments[kom].id+'" class="deleteKomentar btn btn-default">DELETE</button>'+
              '</div>'
			}
			else{
				var editDel ='';
			}
		};
			
			var str = '<button onclick="likeKomentar(this.id)" id="likeKomentar='+data.comments[kom].id+'" class="likeKomentar btn btn-default">Like</button>';
			var str2 = '<button onclick="disLikeKomentar(this.id)" id="disLikeKometar='+data.comments[kom].id+'" class="disLikeKometar btn btn-default">Dislike</button>';
	if(loggedUser != null){		
			
		for(knn in data.likeComm)	{
			if(loggedUser.userName == data.likeComm[knn].owner.userName){
				if(data.comments[kom].id == data.likeComm[knn].comment.id){
					if(data.likeComm[knn].likeOrDislike == true){
						str = '<button onclick="likeKomentar(this.id)" id="likeKomentar='+data.comments[kom].id+'" class="likeKomentar btn btn-default" style="background-color:#60c7c1">Liked</button>';
					}
					else{
						str2 = '<button onclick="disLikeKomentar(this.id)" id="disLikeKometar='+data.comments[kom].id+'" class="disLikeKometar btn btn-default" style="background-color:#bc3838">Disliked</button>';
					}
					
				}
				
			}
			
		}
		komentar.append(               
				'<div id="Kometar'+data.comments[kom].id+'" class="komentarContent">'+
                '<div id="slikaKomentaraDiv">'+
                  '<a href="profile.html?channel='+data.comments[kom].user.userName+'"><img id="slikaKometara" src="'+data.comments[kom].user.profileUrl+'"></a>'+
                '</div>'+
                '<div id="mianKomentar">'+
                  '<div id="headerKomentar">'+
                    '<div id="ImeIdAtum">'+
                      '<a href="profile.html?channel='+data.comments[kom].user.userName+'"><p id="ime"><b>'+data.comments[kom].user.userName+'</b></p></a>'+
                      '<p id="datum">'+data.comments[kom].datePosted+'</p>'+
                    '</div>'+
                    '<div id="sadrzajKomentara">'+
                      '<p id="komentarSadrzajSlova">'+data.comments[kom].text+'</p>'+
                    '</div>'+
                  '</div>'+
                  
                 
                  '<div id="buttonKomentar">'+
                  '<div id="likeDislikeDugmici">'+ str  +
                  '<p id="numLikes">'+data.comments[kom].likeNumber+'</p>'+ str2 +
                      '<p id="numDisLikes">'+data.comments[kom].dislikeNumber+'</p>'+
                   '</div>'+editDel
                    +
                  '</div>'+
                '</div>'+
              '</div>'
		);
		}else{		
		
		komentar.append(               
				'<div id="Kometar'+data.comments[kom].id+'" class="komentarContent">'+
                '<div id="slikaKomentaraDiv">'+
                  '<a href="profile.html?channel='+data.comments[kom].user.userName+'"><img id="slikaKometara" src="'+data.comments[kom].user.profileUrl+'"></a>'+
                '</div>'+
                '<div id="mianKomentar">'+
                  '<div id="headerKomentar">'+
                    '<div id="ImeIdAtum">'+
                      '<a href="profile.html?channel='+data.comments[kom].user.userName+'"><p id="ime"><b>'+data.comments[kom].user.userName+'</b></p></a>'+
                      '<p id="datum">'+data.comments[kom].datePosted+'</p>'+
                    '</div>'+
                    '<div id="sadrzajKomentara">'+
                      '<p id="komentarSadrzajSlova">'+data.comments[kom].text+'</p>'+
                    '</div>'+
                  '</div>'+
                  
                 
                  '<div id="buttonKomentar">'+
                  '<div id="likeDislikeDugmici">'+ str  +
                  '<p id="numLikes">'+data.comments[kom].likeNumber+'</p>'+ str2 +
                      '<p id="numDisLikes">'+data.comments[kom].dislikeNumber+'</p>'+
                   '</div>'+
                    
                  '</div>'+
                '</div>'+
              '</div>'
		);
		};		
		};
		if(data.video.commentsEnabled == false){
			if(data.loggedInUser == null){
				komentar.empty();
				simpleBox.empty();
			}else if(data.loggedInUser !=null){
				if(data.video.owner.userName != data.loggedInUser.userName){
					komentar.empty();
					simpleBox.empty();
				}else if(data.loggedInUser.userType != "ADMIN"){
					komentar.empty();
					simpleBox.empty();
				}
			}
			
			
		}
		if(data.video.owner.blocked == true){
			if(data.loggedInUser.userType != "ADMIN" ){
				subDiv.empty()
			}
			
		}
		
		for(vd in data.videoDesno){
			
		
		konetejnerSadrzaj.append(
				
				'<div id="konetejnerSadrzaj"> '+
			'<div id="slikaVideo">'+
                '<a href="watch.html?id='+data.videoDesno[vd].id+'"><img id="slika" src="'+data.videoDesno[vd].pictureUrl+'"></a>'+
              '</div>'+
              '<div id="infoVideo">'+
                '<div id="nazivVidea">'+
                  '<a href="watch.html?id='+data.videoDesno[vd].id+'"><h4><b>'+data.videoDesno[vd].videoName+'</b></h4></a>'+
               ' </div>'+
                '<div id="imeKanala">'+
                  '<a href="profile.html?id='+data.videoDesno[vd].owner.userName+'"><h4>'+data.videoDesno[vd].owner.userName+'</h4></a>'+
               ' </div>'+
                '<div id="numOfViews">'+
                  '<h6>'+data.videoDesno[vd].views+' views</h6>'+
                '</div>'+
              '</div>'+
              '</div>'
              	
		
		);
		}
		 event.preventDefault()	;	
		
		 
		
		
		
	});
	

	 /*$.get('CommentLikeDislikeServlet',{"videoIdU":videoId},function(data3){
		 var buttonKomentar = $('#buttonKomentar');
		 for(lkk in data3.likeComm){
			 var idKom = "likeKomentar"+data3.like.Comm.id
			 
			 if (button.id == idKom){
				 
				 alert("")
			 }
			 
			
		 }
		 
	 });
		 */

		
	 
	
	 	
	
});

function postKomentar() {	
	$.get('LoginServlet',{},function(data){
		 var text = document.getElementById("komentarSadrzaj").value.trim();
		 if(data.user != null){
		 var owner = data.user.userName; 
		 }else{
			 var owner = null;
		 }		
		 var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];
		 var prazno = "";
		 
		
		if(data.user == null){
		
			 $('#myModal').modal('show');
		
		}else{
			if(data.user.blocked == true){
				alert("BLOKIRANI SE NE MOZETE DA POSTAVITE KOMENTARE")
			}else{
			
			if (text == "") {
				alert("Morate popuniti komentar polje!!!")
			}else{
				
				$.post('CommentServlet',{"text":text,"owner":owner,"videoId":videoId},function(data2){
					
					
					document.getElementById('komentarSadrzaj').value = "";
					window.location.reload();
				});			
			}
			
			}
			
		}	
			
	});	
}

function subscribe(){
	$.get('LoginServlet',{},function(data){
		 var mainUser = $("#ownerName").text();
		if(data.user != null){
			 var subscriber = data.user.userName; 
			 }else{
				 var subscriber = null;
			 }			
		if(data.user == null){
			 $('#myModal').modal('show');			
		}
		else{
			if(data.user.blocked == true){
				alert("BLOKIRANI SE NE MOZETE DA SUBSCRIBUJETE KORISNIKE")
			}else{
			$.post('SubServlet',{"mainUser":mainUser,"subscriber":subscriber},function(data2){	
				window.location.reload();
			});		
			}
		}
		
		
		
		
	});
	
}
function Unsubscribe(){
	$.get('LoginServlet',{},function(data){
		 var mainUser = $("#ownerName").text();
		if(data.user != null){
			 var subscriber = data.user.userName; 
			 }else{
				 var subscriber = null;
			 }	
		
		if(data.user.blocked == true){
			alert("BLOKIRANI SE NE MOZETE DA UNSUBSCRIBUJETE KORISNIKE")
		}else{
		
		
		
		$.post('UnSubServlet',{"mainUser":mainUser,"subscriber":subscriber},function(data2){	
			window.location.reload();
		});
		
		}
		
		
	});
	
	
}

function likeVideo(){
	
	$.get('LoginServlet',{},function(data){
		var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];
		 var likeText = $("#likeVideo").text();
		 var disLikeText = $("#disLikeVideo").text();
		 
		 if(data.user != null){
			 var owner = data.user.userName; 
			 }else{
				 var owner = null;
			 }	
		 
		if(data.user == null){
			 $('#myModal').modal('show');			
		}
		else{
			
			if(data.user.blocked == true){
				alert("BLOKIRANI SE NE MOZETE DA LIKUJETE VIDEO KLIPOVE")
			}else{
			if(likeText == "Like" && disLikeText == "Dislike"){
				
				$.post('LikeServlet',{"owner":owner,"videoId":videoId,"primer":'1'},function(data){
					
					window.location.reload();
				});			
				
			}else if ( likeText == "Liked" && disLikeText == "Dislike"){
					$.post('LikeServlet',{"owner":owner,"videoId":videoId,"primer":'2'},function(data){
					
					window.location.reload();
				});
			}else if (likeText == "Like" && disLikeText == "Disliked"){
				$.post('LikeServlet',{"owner":owner,"videoId":videoId,"primer":'3'},function(data){
					
					window.location.reload();
				});				
			}
			
			
			}
			
			
		}		
	});
}

function disLikeVideo(){
	$.get('LoginServlet',{},function(data){
		var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];
		 var likeText = $("#likeVideo").text();
		 var disLikeText = $("#disLikeVideo").text();
		 
		 if(data.user != null){
			 var owner = data.user.userName; 
			 }else{
				 var owner = null;
			 }	
		 
		if(data.user == null){
			 $('#myModal').modal('show');			
		}
		else{
			if(data.user.blocked == true){
				alert("BLOKIRANI SE NE MOZETE DA DISLIKUJETE VIDEO KLIPOVE")
			}else{
			
			if(likeText == "Like" && disLikeText == "Dislike"){
				
				$.post('LikeServlet',{"owner":owner,"videoId":videoId,"primer":'4'},function(data){
					
					window.location.reload();
				});		
				
			}else if ( likeText == "Like" && disLikeText == "Disliked"){
					$.post('LikeServlet',{"owner":owner,"videoId":videoId,"primer":'5'},function(data){
					
					window.location.reload();
				});
			}else if (likeText == "Liked" && disLikeText == "Dislike"){
				$.post('LikeServlet',{"owner":owner,"videoId":videoId,"primer":'6'},function(data){
					
					window.location.reload();
				});				
			}	
			
			
			
			
			}
			
		}
		
		
		
		
	});


}
function likeKomentar(clicked_id){
	var idLike = clicked_id;	
	var commentId = idLike.slice(1).split('=')[1];
	var idDisLike = "disLikeKometar="+commentId;	
	var likeText = document.getElementById(idLike).textContent;
	var disLikeText = document.getElementById(idDisLike).textContent;	
	$.get('LoginServlet',{},function(data){
		 if(data.user != null){
			 var owner = data.user.userName; 
			 }else{
				 var owner = null;
			 }	
		 
		if(data.user == null){
			 $('#myModal').modal('show');			
		}else{
			if(data.user.blocked == true){
				alert("BLOKIRANI SE NE MOZETE DA LIKUJETE KOMENTARE")
			}else{
				if(likeText == "Like" && disLikeText == "Dislike"){
				
				$.post('LikeCommServlet',{"owner":owner,"commentId":commentId,"primer":'1'},function(data){
					
					window.location.reload();
				});	
			}else if ( likeText == "Liked" && disLikeText == "Dislike"){
					$.post('LikeCommServlet',{"owner":owner,"commentId":commentId,"primer":'2'},function(data){
					
					window.location.reload();
				});
			}else if (likeText == "Like" && disLikeText == "Disliked"){
				$.post('LikeCommServlet',{"owner":owner,"commentId":commentId,"primer":'3'},function(data){
					
					window.location.reload();
				});				
			}}
			
		};
	});
};

function disLikeKomentar(clicked_id){
	var idDisLike = clicked_id;	
	var commentId = idDisLike.slice(1).split('=')[1];	
	var idLike = "likeKomentar="+commentId;
	
	
	var disLikeText = document.getElementById(idDisLike).textContent;
	var likeText = document.getElementById(idLike).textContent;
	
	
	
	
	
	$.get('LoginServlet',{},function(data){
		 if(data.user != null){
			 var owner = data.user.userName; 
			 }else{
				 var owner = null;
			 }	
		 
		if(data.user == null){
			 $('#myModal').modal('show');			
		}else{
			if(data.user.blocked == true){
				alert("BLOKIRANI SE NE MOZETE DA DISLIKUJETE KOMENTARE")
			}else{
				if(likeText == "Like" && disLikeText == "Dislike"){
					
					$.post('LikeCommServlet',{"owner":owner,"commentId":commentId,"primer":'4'},function(data){
						
						window.location.reload();
					});		
					
				}else if ( likeText == "Like" && disLikeText == "Disliked"){
						$.post('LikeCommServlet',{"owner":owner,"commentId":commentId,"primer":'5'},function(data){
						
						window.location.reload();
					});
				}else if (likeText == "Liked" && disLikeText == "Dislike"){
					$.post('LikeCommServlet',{"owner":owner,"commentId":commentId,"primer":'6'},function(data){
						
						window.location.reload();
					});				
				}		
			}
			
			
			
			
			
			
			
		}
		
		
		
		
		

		
	});
}


function editKomentar(clicked_id){
	
	
	var editId = clicked_id;
	var commentId = editId.slice(1).split('=')[1];
	$.get('CommentServlet',{"commentId":commentId},function(data){
		 $("#editKomentText").val(data.comment.text);
		 $('#editModal').modal('show');
		
	});
	$('#editSubmit').on('click', function(event) {
		var text = document.getElementById("editKomentText").value.trim();
		$.get('LoginServlet',{},function(data){
			if(data.user.blocked==true){
				alert("BLOKIRANI STE")
				$('#editModal').modal('hide');
			}else{
				$.post('UpdateCommentServlet',{"commentId":commentId,"text":text},function(data){
					
					
					window.location.reload();
					
				});
			}
		});
		
		
		
		
	});
}


function deleteKomentar(clicked_id){
	var deleteId = clicked_id;
	var commentId = deleteId.slice(1).split('=')[1];
	
	
	$.get('LoginServlet',{},function(data){
		if(data.user.blocked==true){
			alert("BLOKIRANI STE");
			
		}else{
			$.post('WatchServlet',{"commentId":commentId},function(data){
				
				window.location.reload();
				
				
			});
		}
	});
	
	
	
	
	
	
	
}





