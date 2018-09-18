package YTdusan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.Video.Visibility;
import YTdusan.tools.DateConverter;
import YTdusan.dao.ConnectionManager;
import YTdusan.dao.UserDAO;


public class VideoDAO {

	public static ArrayList<Video> publicVideos() {
		
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE visibility = ? AND V.deleted = ? AND V.blocked = ? AND U.deleted = ? AND U.blocked = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			pstmt.setBoolean(4, false);
			pstmt.setBoolean(5, false);
			System.out.println(pstmt);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
				videos.add(newVideo);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return videos;
	}
	
	public static ArrayList<Video> allVideos() {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE V.deleted = ? AND U.deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
				videos.add(newVideo);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return videos;
	}
	
	
	public static ArrayList<Video> search(String parameter) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		System.out.println(parameter + " iz videoDAO");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			System.out.println(parameter + " ulazak u try videoDAO");
			String query = "SELECT * FROM video WHERE videoName LIKE  CONCAT('%', ? ,'%') AND visibility = 'PUBLIC'";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,parameter);
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);
				videos.add(newVideo);
				
				System.out.println(videos);
				
				
				
			}
			
			return videos;

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<Video> searchAll(String parameter) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		System.out.println(parameter + " iz videoDAO");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			System.out.println(parameter + " ulazak u try videoDAO");
			String query = "SELECT * FROM video WHERE videoName LIKE  CONCAT('%', ? ,'%')";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,parameter);
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);
				videos.add(newVideo);
				
				System.out.println(videos);
				
				
				
			}
			
			return videos;

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	public static ArrayList<Video> searchMine(String userName) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		System.out.println(userName + " iz videoDAO");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			
			String query = "SELECT * FROM video WHERE owner = ? ORDER BY datePosted DESC";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,userName);
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);
				videos.add(newVideo);
				
				
				
				
				
			}
			
			return videos;

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return null;
	}
	public static ArrayList<Video> searchMinePublic(String userName) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		System.out.println(userName + " iz videoDAO");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			
			String query = "SELECT * FROM video WHERE owner = ? AND visibility ='PUBLIC' ORDER BY datePosted DESC";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,userName);
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);
				videos.add(newVideo);
				
				
				
				
				
			}
			
			return videos;

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	public static Video get(String videosId){
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * from video where id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, videosId);
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);
				
				
				
				pstmt.close();
				rset.close();
				
				return newVideo;
				
				
			}
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return null;
		
	}
	
	
	
	public static boolean plus(String videosId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		
		try {
			
			String query = "UPDATE video SET views = views + 1 WHERE id =?";
		    
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,videosId);
			pstmt.executeUpdate();	
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				
				
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}; 
	
	
	
	public static boolean addVideo(Video uploadVideo) {
		Connection conn = ConnectionManager.getConnection();
		System.out.println(uploadVideo);
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO video (videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes,numberOfDislikes,views,datePosted,owner,deleted)VALUES(?, ?, ?, ?, ?,? ,? ,?,?,?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, uploadVideo.getVideoUrl());
			pstmt.setString(index++, uploadVideo.getPictureUrl());
			pstmt.setString(index++, uploadVideo.getVideoName());
			pstmt.setString(index++, uploadVideo.getDescription());
			pstmt.setString(index++, uploadVideo.getVisibility().toString());
			pstmt.setBoolean(index++, uploadVideo.isBlocked());
			pstmt.setBoolean(index++, uploadVideo.isCommentsEnabled());
			pstmt.setBoolean(index++, uploadVideo.isRatingEnabled());
			pstmt.setInt(index++, uploadVideo.getNumberOfLikes());
			pstmt.setInt(index++, uploadVideo.getNumberOfDislikes());
			pstmt.setInt(index++, uploadVideo.getViews());
			Date myDate=DateConverter.stringToDateForWrite(uploadVideo.getDatePosted());
			java.sql.Date date=new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++,date);
			pstmt.setString(index++, uploadVideo.getOwner().getUserName());
			pstmt.setBoolean(index++, uploadVideo.isDeleted());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}
	
	public static boolean editVideo(Video editVideo) {
		Connection conn = ConnectionManager.getConnection();
		System.out.println(editVideo);
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE video SET videoUrl = ?, pictureUrl=?, videoName=?, description=?, visibility=?, "
					+ "commentsEnabled=?, ratingEnabled=? WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, editVideo.getVideoUrl());
			pstmt.setString(index++, editVideo.getPictureUrl());
			pstmt.setString(index++, editVideo.getVideoName());
			pstmt.setString(index++, editVideo.getDescription());
			pstmt.setString(index++, editVideo.getVisibility().toString());
			pstmt.setBoolean(index++, editVideo.isCommentsEnabled());
			pstmt.setBoolean(index++, editVideo.isRatingEnabled());
			pstmt.setInt(index++, editVideo.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}
	
	public static int numbeOfSubs(String userName){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT COUNT(subscriber) FROM subscribe WHERE mainUser = ?";
		    
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,userName);
			rset = pstmt.executeQuery();	
			
			if (rset.next()) {
				
				int index = 1;
				int broj = rset.getInt(index++);
				
				return broj;
				
				}
			
			
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				
				
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return 0;
	}; 
	
	public static boolean blocked(String videoId,Boolean blocked){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		
		try {
			
			String query = "UPDATE video SET blocked = ? WHERE id = ?";
		    
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setBoolean(1,blocked);
			pstmt.setString(2,videoId);
			pstmt.executeUpdate();	
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				
				
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}; 
	
	
	public static boolean deleteVideo(String videoId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt0 = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		
		
		try {
			String query0 = "SET FOREIGN_KEY_CHECKS=0;";
			String query1 = "DELETE FROM video WHERE id = ?";
			
			String query2 = "DELETE FROM comment WHERE videoId = ?";
			
			String query3 = "SET FOREIGN_KEY_CHECKS=1";
			
			System.out.println(query0);
			pstmt0 = conn.prepareStatement(query0);			
			pstmt0.executeUpdate();
			
		   
			
			System.out.println(query1);
			pstmt1 = conn.prepareStatement(query1);			
			pstmt1.setString(1,videoId);
			pstmt1.executeUpdate();
			
			System.out.println(query2);
			pstmt2 = conn.prepareStatement(query2);			
			pstmt2.setString(1,videoId);
			pstmt2.executeUpdate();
			
			
			
			
			System.out.println(query3);
			pstmt3 = conn.prepareStatement(query3);			
			pstmt3.executeUpdate();
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt0.close();
				pstmt1.close();
				pstmt2.close();
				pstmt3.close();
				
				
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;

	}; 
	
	
public static ArrayList<Video> sherSearchpublic(String owner1,String videoName1,String numberOfViews,String date,String sortBy,String how) {
		
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		boolean veza = false;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE owner LIKE '%"+owner1+"%' AND videoName LIKE '%"+videoName1+"%' AND "
					+ "datePosted LIKE '%"+date+"%' AND views "+numberOfViews+"  AND visibility = 'PUBLIC'  "
							+ "ORDER BY "+sortBy+" "+how+";";
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);
			
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
				videos.add(newVideo);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return videos;
	}
	
public static ArrayList<Video> sherSearchAdmin(String owner1,String videoName1,String numberOfViews,String date,String sortBy,String how) {
	
	Connection conn = ConnectionManager.getConnection();
	ArrayList<Video> videos = new ArrayList<Video>();
	boolean veza = false;
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	try {
		String query = "SELECT * FROM video WHERE owner LIKE '%"+owner1+"%' AND videoName LIKE '%"+videoName1+"%' AND "
				+ "datePosted LIKE '%"+date+"%' AND views "+numberOfViews+"   "
						+ "ORDER BY "+sortBy+" "+how+";";
		pstmt = conn.prepareStatement(query);
		
		rset = pstmt.executeQuery();
		while (rset.next()) {
			int index = 1;
			int id = rset.getInt(index++);
			String videoUrl = rset.getString(index++);
			String pictureUrl = rset.getString(index++);
			String videoName = rset.getString(index++);
			String description = rset.getString(index++);
			Visibility visibility = Visibility.valueOf(rset.getString(index++));
			boolean blocked = rset.getBoolean(index++);
			boolean commentsEnabled = rset.getBoolean(index++);
			boolean ratingEnabled = rset.getBoolean(index++);
			int numberOfLikes = rset.getInt(index++);
			int numberOfDislikes = rset.getInt(index++);
			int views = rset.getInt(index++);
			Date d = rset.getDate(index++);
			String datePosted=DateConverter.dateToString(d);
			String user = rset.getString(index++);
			User owner = UserDAO.get(user);
			boolean deleted = rset.getBoolean(index++);
			Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
			videos.add(newVideo);
		}

	} catch (Exception ex) {
		System.out.println("Greska u SQL upitu!");
		ex.printStackTrace();
	} finally {
		try {
			pstmt.close();
		} catch (SQLException ex1) {
			ex1.printStackTrace();
		}
		try {
			rset.close();
		} catch (SQLException ex1) {
			ex1.printStackTrace();
		}
	}
	return videos;
}

	
}





