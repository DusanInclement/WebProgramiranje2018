package YTdusan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

import YTdusan.model.Like;
import YTdusan.model.Sub;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.Video.Visibility;
import YTdusan.tools.DateConverter;
import YTdusan.dao.ConnectionManager;
import YTdusan.dao.UserDAO;


public class SubDAO {
	
	
	
	public static boolean subscribe(String mainUser, String subscriber){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "INSERT INTO subscribe(mainUser,subscriber) VALUES(?,?);";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,mainUser);
			pstmt.setString(2,subscriber);
			
			
			
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
	
	
	public static boolean Unsubscribe(String mainUser, String subscriber){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "DELETE from subscribe where mainUser = ? and subscriber = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,mainUser);
			pstmt.setString(2,subscriber);
			
			
			
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
	
	
	
	
	
	
	
	public static ArrayList<Sub> get(String userName){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Sub> subovani = new ArrayList<Sub>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM subscribe where subscriber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				String ime = rset.getString(index++);
				String who = rset.getString(index++);
				
				Sub newSub = new Sub(ime);
				subovani.add(newSub);
				
				
			}
			return subovani;
			
			
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
	}