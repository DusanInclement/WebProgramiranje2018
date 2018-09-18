package YTdusan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

import YTdusan.model.Comment;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.User.UserType;
import YTdusan.model.Video.Visibility;
import YTdusan.tools.DateConverter;
import YTdusan.dao.ConnectionManager;
import YTdusan.dao.UserDAO;


public class CommentDAO {
	
	public static boolean addComment(String text, String owner, String videoId, String date){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted) VALUES(?,?,?,?,0,0,false)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,text);
			pstmt.setString(2,owner);
			pstmt.setString(3,videoId);
			pstmt.setString(4,date);
			
			
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
	
	public static ArrayList<Comment> searchMine(String videosId) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Comment> comments = new ArrayList<Comment>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			
			String query = "SELECT * FROM comment WHERE videoId = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,videosId);
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String text = rset.getString(index++);
				String user = rset.getString(index++);
				User owner = UserDAO.get(user);				
				String videoId = rset.getString(index++);
				Video video = VideoDAO.get(videoId);
				Date d = rset.getDate(index++);
				String datePosted=DateConverter.dateToString(d);
				int likeNumber = rset.getInt(index++);
				int dislikeNumber = rset.getInt(index++);
				boolean deleted = rset.getBoolean(index++);
				Comment newComment = new Comment(id, text, datePosted, owner, video, likeNumber, dislikeNumber, deleted);
				comments.add(newComment);
			}
			
			return comments;

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
	
	public static Comment get(String commentId) {

		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			String query = "SELECT * FROM comment WHERE id = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, commentId);
			pstmt.setBoolean(2, false);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {		
				
				int index = 1;				
				int id = rset.getInt(index++);				
				String text = rset.getString(index++);	
				String userId = rset.getString(index++);	
				User user = UserDAO.get(userId);
				String videoId = rset.getString(index++);	
				Video video = VideoDAO.get(videoId);
				Date date= rset.getDate(index++);				
				String datePosted = DateConverter.dateToString(date);
				
				
				int likeNumber = rset.getInt(index++);	
				int dislikeNumber = rset.getInt(index++);							
				boolean deleted = rset.getBoolean(index++);				
						
				
				Comment comment = new Comment(id, text, datePosted, user, video, likeNumber, dislikeNumber, deleted);
				
				pstmt.close();
				rset.close();
				
				return comment;
				
				
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static boolean addCommLike(String myNewDate,String owner,String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		
		try {
			
			String query = "INSERT INTO likedislike (liked,likeDate,owner,deleted)VALUES (true,?,?,false)";
		    String query2 = "INSERT INTO likedislikecomment (likeId,commentId,deleted)VALUES (last_insert_id(),?,false)";
		    String query3 = "UPDATE comment SET likeNumber = likeNumber + 1 where id = ?";
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,myNewDate);
			pstmt.setString(2,owner);
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,commentId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.setString(1,commentId);
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
	
	
	
	
	public static boolean removeCommentLike(String owner,String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		
		try {
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
		    String query2 = "DELETE a.*,b.* FROM likedislike as a  INNER JOIN likedislikecomment as b ON a.id= b.likeId WHERE a.owner = ? and b.commentId = ?";
		    String query3 = "SET FOREIGN_KEY_CHECKS=1";
		    String query4 = "UPDATE comment SET likeNumber = likeNumber - 1 where id = ?";
		    
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,owner);
			pstmt1.setString(2,commentId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.executeUpdate();
			
			pstmt3 = conn.prepareStatement(query4);
			pstmt3.setString(1,commentId);
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
	
	public static boolean updateCommentLikeToLike(String owner,String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		
		try {
			
			String query = "UPDATE likedislike as a  INNER JOIN likedislikecomment as b ON a.id= b.likeId SET a.liked = true WHERE a.owner = ? and b.commentId = ?";
		    String query1 = "UPDATE comment SET dislikeNumber = dislikeNumber - 1 , likeNumber = likeNumber + 1  WHERE id = ?";
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,owner);
			pstmt.setString(2,commentId);
			pstmt.executeUpdate();	
			
			pstmt1 = conn.prepareStatement(query1);			
			pstmt1.setString(1,commentId);
			
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
	
	public static boolean addCommentDisLike(String myNewDate,String owner,String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		
		try {
			
			String query = "INSERT INTO likedislike (liked,likeDate,owner,deleted)VALUES (false,?,?,false)";
		    String query2 = "INSERT INTO likedislikecomment (likeId,commentId,deleted)VALUES (last_insert_id(),?,false)";
		    String query3 = "UPDATE comment SET dislikeNumber = dislikeNumber + 1 where id = ?";
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,myNewDate);
			pstmt.setString(2,owner);
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,commentId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.setString(1,commentId);
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
	
	public static boolean removeCommentDisLike(String owner,String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		
		try {
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
		    String query2 = "DELETE a.*,b.* FROM likedislike as a  INNER JOIN likedislikecomment as b ON a.id= b.likeId WHERE a.owner = ? and b.commentId = ?";
		    String query3 = "SET FOREIGN_KEY_CHECKS=1";
		    String query4 = "UPDATE comment SET dislikeNumber = dislikeNumber - 1 where id = ?";
		    
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.executeUpdate();
			
			pstmt1 = conn.prepareStatement(query2);
			pstmt1.setString(1,owner);
			pstmt1.setString(2,commentId);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(query3);
			pstmt2.executeUpdate();
			
			pstmt3 = conn.prepareStatement(query4);
			pstmt3.setString(1,commentId);
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
	
	
	public static boolean updateCommentLikeToDisLike(String owner,String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		
		try {
			
			String query = "UPDATE likedislike as a  INNER JOIN likedislikecomment as b ON a.id= b.likeId SET a.liked = false WHERE a.owner = ? and b.commentId = ?";
		    String query1 = "UPDATE comment SET likeNumber = likeNumber - 1 , dislikeNumber = dislikeNumber + 1  WHERE id = ?";
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,owner);
			pstmt.setString(2,commentId);
			pstmt.executeUpdate();	
			
			pstmt1 = conn.prepareStatement(query1);			
			pstmt1.setString(1,commentId);
			
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
	
	public static boolean updateComment(String text,String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		
		try {
			
			String query = "UPDATE comment SET text = ? WHERE id = ?";
		    
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,text);
			pstmt.setString(2,commentId);
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
	
	
	public static boolean deleteComment(String commentId){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		
		try {
			String query = "SET FOREIGN_KEY_CHECKS=0";
			String query1 = "DELETE FROM comment WHERE id = ?";
			String query2 = "SET FOREIGN_KEY_CHECKS=1";
		    
			System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.executeUpdate();
			
		    System.out.println(query1);
			pstmt1 = conn.prepareStatement(query1);			
			pstmt1.setString(1,commentId);			
			pstmt1.executeUpdate();	
			
			System.out.println(query2);
			pstmt2 = conn.prepareStatement(query2);			
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
	
}
