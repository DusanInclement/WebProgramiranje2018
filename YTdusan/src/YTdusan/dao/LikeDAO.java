package YTdusan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

import YTdusan.model.Comment;
import YTdusan.model.Like;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.Video.Visibility;
import YTdusan.tools.DateConverter;
import YTdusan.dao.ConnectionManager;
import YTdusan.dao.UserDAO;


public class LikeDAO {
	
	public static boolean addVideoLike(String myNewDate,String owner,String videoId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		
		try {
			
			String query = "INSERT INTO likedislike (liked,likeDate,owner,deleted)VALUES (true,?,?,false)";
		    String query2 = "INSERT INTO likedislikevideo (likeId,videoId,deleted)VALUES (last_insert_id(),?,false)";
		    String query3 = "UPDATE video SET numberOfLikes = numberOfLikes + 1 where id = ?";
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,myNewDate);
			pstmt.setString(2,owner);
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,videoId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.setString(1,videoId);
			pstmt2.executeUpdate();
			
			
			
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				pstmt1.close();
				pstmt2.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}; 
	
	public static boolean removeVideoLike(String owner,String videoId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		
		try {
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
		    String query2 = "DELETE a.*,b.* FROM likedislike as a  INNER JOIN likedislikevideo as b ON a.id= b.likeId WHERE a.owner = ? and b.videoId = ?";
		    String query3 = "SET FOREIGN_KEY_CHECKS=1";
		    String query4 = "UPDATE video SET numberOfLikes = numberOfLikes - 1 where id = ?";
		    
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,owner);
			pstmt1.setString(2,videoId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.executeUpdate();
			
			pstmt3 = conn.prepareStatement(query4);
			pstmt3.setString(1,videoId);
			pstmt3.executeUpdate();
			
			
			
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				pstmt1.close();
				pstmt2.close();
				pstmt3.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	};
	
	
	public static boolean updateVideoLikeToLike(String owner,String videoId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		
		try {
			
			String query = "UPDATE likedislike as a  INNER JOIN likedislikevideo as b ON a.id= b.likeId SET a.liked = true WHERE a.owner = ? and b.videoId = ?";
		    String query1 = "UPDATE video SET numberOfDislikes = numberOfDislikes - 1 , numberOfLikes = numberOfLikes + 1  WHERE id = ?";
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,owner);
			pstmt.setString(2,videoId);
			pstmt.executeUpdate();	
			
			pstmt1 = conn.prepareStatement(query1);			
			pstmt1.setString(1,videoId);
			
			pstmt1.executeUpdate();
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				pstmt1.close();
				
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}; 
	
	public static boolean addVideoDisLike(String myNewDate,String owner,String videoId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		
		try {
			
			String query = "INSERT INTO likedislike (liked,likeDate,owner,deleted)VALUES (false,?,?,false)";
		    String query2 = "INSERT INTO likedislikevideo (likeId,videoId,deleted)VALUES (last_insert_id(),?,false)";
		    String query3 = "UPDATE video SET numberOfDislikes = numberOfDislikes + 1 where id = ?";
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,myNewDate);
			pstmt.setString(2,owner);
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,videoId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.setString(1,videoId);
			pstmt2.executeUpdate();
			
			
			
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				pstmt1.close();
				pstmt2.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}; 
	
	public static boolean removeVideoDisLike(String owner,String videoId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		
		try {
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
		    String query2 = "DELETE a.*,b.* FROM likedislike as a  INNER JOIN likedislikevideo as b ON a.id= b.likeId WHERE a.owner = ? and b.videoId = ?";
		    String query3 = "SET FOREIGN_KEY_CHECKS=1";
		    String query4 = "UPDATE video SET numberOfDislikes = numberOfDislikes - 1 where id = ?";
		    
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,owner);
			pstmt1.setString(2,videoId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.executeUpdate();
			
			pstmt3 = conn.prepareStatement(query4);
			pstmt3.setString(1,videoId);
			pstmt3.executeUpdate();
			
			
			
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				pstmt1.close();
				pstmt2.close();
				pstmt3.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	};
	
	public static boolean updateVideoLikeToDisLike(String owner,String videoId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		
		try {
			
			String query = "UPDATE likedislike as a  INNER JOIN likedislikevideo as b ON a.id= b.likeId SET a.liked = false WHERE a.owner = ? and b.videoId = ?";
		    String query1 = "UPDATE video SET numberOfLikes = numberOfLikes - 1 , numberOfDislikes = numberOfDislikes + 1  WHERE id = ?";
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,owner);
			pstmt.setString(2,videoId);
			pstmt.executeUpdate();	
			
			pstmt1 = conn.prepareStatement(query1);			
			pstmt1.setString(1,videoId);
			
			pstmt1.executeUpdate();
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				pstmt1.close();
				
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}; 
	
	
	public static ArrayList<Like> getLikesUser(String userName){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Like> likes = new ArrayList<Like>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT a.id,a.liked,a.likeDate , b.videoId, a.owner, a.deleted FROM likedislike AS a JOIN likedislikevideo AS b ON a.Id = b.LikeId WHERE a.owner = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				
				
				int index = 1;				
				int id = rset.getInt(index++);				
				boolean  likeOrDislike = rset.getBoolean(index++);				
				Date d = rset.getDate(index++);				
				String likeDate=DateConverter.dateToString(d);					
				String videoId = rset.getString(index++);
				Video video = VideoDAO.get(videoId);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Like like = new Like(id, likeOrDislike, likeDate, video, null, owner, deleted);
				likes.add(like);
				
				
				
				
				
			
			}
				pstmt.close();
				rset.close();
				
				return likes;
				
				
			
			
			
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
		
		
	};
	
	public static ArrayList<Like> getLikeComm(String videoId){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Like> likeComm = new ArrayList<Like>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT b.id , b.liked, b.likeDate, a.videoId, c.commentId, b.owner, a.deleted FROM comment AS a inner join likedislike AS b inner join likedislikecomment AS c ON a.id = c.commentId and b.id = c.likeId WHERE a.videoid = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, videoId);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				
				
				int index = 1;				
				int id = rset.getInt(index++);				
				boolean  likeOrDislike = rset.getBoolean(index++);				
				Date d = rset.getDate(index++);				
				String likeDate=DateConverter.dateToString(d);					
				String videoIdu = rset.getString(index++);
				Video video = VideoDAO.get(videoIdu);
				String commentId = rset.getString(index++);
				Comment comment = CommentDAO.get(commentId);				
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);
				boolean deleted = rset.getBoolean(index++);
				Like like = new Like(id, likeOrDislike, likeDate, video, comment, owner, deleted);
				likeComm.add(like);
				
				
				
				
				
			
			}
			return likeComm;
			
			
			
			
			
		}catch (Exception ex) {
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
		
		
		
		
	
	};
	
}