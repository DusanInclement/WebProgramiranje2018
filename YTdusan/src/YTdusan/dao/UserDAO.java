package YTdusan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import YTdusan.model.Sub;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.User.UserType;
import YTdusan.model.Video.Visibility;
import YTdusan.dao.ConnectionManager;
import YTdusan.tools.DateConverter;


public class UserDAO {
	public static User usernameTaken(String userName) {

		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE userName = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				UserType userType = UserType.valueOf(rset.getString(index++));
				Date date= rset.getDate(index++);
				String registrationDate = DateConverter.dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				String profileUrl = rset.getString(index++);
				boolean lol = rset.getBoolean(index++);
				User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate, blocked, null, null, null, userType,deleted,profileUrl,lol);
				pstmt.close();
				rset.close();
				return newUser;
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

	public static boolean addUser(User user) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (userName, password, firstName, lastName, email, channelDescription, userType, registrationDate, blocked,deleted,profileUrl,lol) VALUES (?, ?, ?, ?, ? ,? ,? , ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUserName());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getChannelDescription());
			pstmt.setString(index++, user.getUserType().toString());
			Date tempDate= DateConverter.stringToDateForWrite(user.getRegistrationDate());
			java.sql.Date date=new java.sql.Date(tempDate.getTime());
			pstmt.setDate(index++, date);
			pstmt.setBoolean(index++, user.getBlocked());
			pstmt.setBoolean(index++, user.getDeleted());
			pstmt.setString(index++, user.getProfileUrl());
			pstmt.setBoolean(index++, user.isLol());
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
	
	public static User get(String userName) {

		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			String query = "SELECT * FROM users WHERE userName = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setBoolean(2, false);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {		
				
				int index = 2;				
				String password = rset.getString(index++);				
				String firstName = rset.getString(index++);				
				String lastName = rset.getString(index++);				
				String email = rset.getString(index++);				
				String channelDescription = rset.getString(index++);				
				UserType userType = UserType.valueOf(rset.getString(index++));				
				Date date= rset.getDate(index++);				
				String registrationDate = DateConverter.dateToString(date);		
				boolean blocked = rset.getBoolean(index++);				
				boolean deleted = rset.getBoolean(index++);				
				String profileUrl = rset.getString(index++);				
				boolean lol = rset.getBoolean(index++);			
				
				User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate,
																		blocked, null, null, null, userType,deleted,profileUrl,lol);
				
				pstmt.close();
				rset.close();
				
				return newUser;
				
				
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
	public static ArrayList<User> getTopSixChannels() {
		Connection conn = ConnectionManager.getConnection();
		
		ArrayList<User> users=new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT DISTINCT(s.mainUser), COUNT(s.mainUser) AS broj, u.deleted FROM subscribe AS s INNER JOIN users AS u ON s.mainUser=u.userName\r\n" + 
					"WHERE u.deleted = ?\r\n" + 
					"GROUP BY s.mainUser\r\n" + 
					"ORDER BY broj DESC";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				if(users.size()==6)
					return users;
				int index = 1;
				String userName = rset.getString(index++);
				User newUser = get(userName);
				users.add(newUser);
			}
			return users;

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
	
	public static ArrayList<Sub> getSubs(String userName) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Sub> subs = new ArrayList<Sub>();
		System.out.println(userName + " iz videoDAO");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			System.out.println(userName + " ulazak u try videoDAO");
			String query = "SELECT * FROM subscribe WHERE subscriber = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,userName);
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				int index = 1;
				String ime = rset.getString(index++);
				
				
				Sub newSub = new Sub(ime);
				subs.add(newSub);
				
				System.out.println(subs);
				
				
				
			}
			
			return subs;

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
	
	
	
	
	
	public static String changeUser(String userName,String password,String firstName,String lastName,String email,String profileUrl) {
		String ok="ok";
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "UPDATE users SET firstName = ?, password = ?,lastName = ?,email = ?, profileUrl = ? WHERE userName = ?";
			pstmt = conn.prepareStatement(query);
			
			System.out.println(firstName + "iz daooooooooooo");
			
			pstmt.setString(1, firstName);
			pstmt.setString(2, password);
			pstmt.setString(3, lastName);
			pstmt.setString(4, email);
			pstmt.setString(5, profileUrl);
			pstmt.setString(6, userName);
			
			
			pstmt.executeUpdate();
			
			return ok;
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			
		}
		return null;
	}
	
	
	public static ArrayList<User> getUserSubs(ArrayList<Sub> subs) {
		System.out.println("eeeeeeeeeeeeeej" + subs);
		Connection conn = ConnectionManager.getConnection();
		ArrayList<User> userSubs = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM users WHERE userName = ?";
		try {
			
			
			for (Sub sub : subs) {
							
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, sub.getIme());
				
				
				rset = pstmt.executeQuery();
				
				if (rset.next()) {	
					int index = 1;
					
						
					String userName = rset.getString(index++);
					String password = rset.getString(index++);				
					String firstName = rset.getString(index++);				
					String lastName = rset.getString(index++);				
					String email = rset.getString(index++);				
					String channelDescription = rset.getString(index++);				
					UserType userType = UserType.valueOf(rset.getString(index++));				
					Date date= rset.getDate(index++);				
					String registrationDate = DateConverter.dateToString(date);		
					boolean blocked = rset.getBoolean(index++);				
					boolean deleted = rset.getBoolean(index++);				
					String profileUrl = rset.getString(index++);				
					boolean lol = rset.getBoolean(index++);			
					
					User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate,
																			blocked, null, null, null, userType,deleted,profileUrl,lol);
					
					userSubs.add(newUser);
					
					
					pstmt.clearParameters();
					
					
				
			}
				pstmt.close();
				rset.close();
			
				
				
				
				
			}
			
			
			
			return userSubs;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static ArrayList<User> search(String parameter) {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<User> users = new ArrayList<User>();
		System.out.println(parameter + " iz videoDAO");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			System.out.println(parameter + " ulazak u try videoDAO");
			String query = "SELECT * FROM users WHERE userName LIKE  CONCAT('%', ? ,'%')";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,parameter);
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				
					int index = 1;
					String userName = rset.getString(index++);
					String password = rset.getString(index++);
					String firstName = rset.getString(index++);
					String lastName = rset.getString(index++);
					String email = rset.getString(index++);
					String channelDescription = rset.getString(index++);
					UserType userType = UserType.valueOf(rset.getString(index++));
					Date date= rset.getDate(index++);
					String registrationDate = DateConverter.dateToString(date);
					boolean blocked = rset.getBoolean(index++);
					boolean deleted = rset.getBoolean(index++);
					String profileUrl = rset.getString(index++);
					boolean lol = rset.getBoolean(index++);
					User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate, blocked, null, null, null, userType,deleted,profileUrl,lol);
					users.add(newUser);
								
			}
			
			return users;

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
	
	public static ArrayList<User> getAll() {
		Connection conn = ConnectionManager.getConnection();
		ArrayList<User> users = new ArrayList<User>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			
			
			
			String query = "SELECT * FROM users";
			
			pstmt = conn.prepareStatement(query);			
			
			rset = pstmt.executeQuery();
			System.out.println(rset);
			while (rset.next()) {
				
					int index = 1;
					String userName = rset.getString(index++);
					String password = rset.getString(index++);
					String firstName = rset.getString(index++);
					String lastName = rset.getString(index++);
					String email = rset.getString(index++);
					String channelDescription = rset.getString(index++);
					UserType userType = UserType.valueOf(rset.getString(index++));
					Date date= rset.getDate(index++);
					String registrationDate = DateConverter.dateToString(date);
					boolean blocked = rset.getBoolean(index++);
					boolean deleted = rset.getBoolean(index++);
					String profileUrl = rset.getString(index++);
					boolean lol = rset.getBoolean(index++);
					User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate, blocked, null, null, null, userType,deleted,profileUrl,lol);
					users.add(newUser);
								
			}
			
			return users;

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
	
	public static boolean blocked(String userName,Boolean blocked){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		
		try {
			
			String query = "UPDATE users SET blocked = ? WHERE userName = ?";
		    
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setBoolean(1,blocked);
			pstmt.setString(2,userName);
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
	
	public static boolean changeType(String userName,String userType){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		
		try {
			
			String query = "UPDATE users set userType = ? where userName = ?";
		    
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,userType);
			pstmt.setString(2,userName);
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
	
	public static boolean deleteUser(String userName){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt0 = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		
		
		try {
			String query0 = "SET FOREIGN_KEY_CHECKS=0;";
			String query = "DELETE FROM users WHERE userName = ?";
			String query1 = "DELETE FROM video WHERE owner = ?";
			String query2 = "DELETE FROM comment WHERE owner = ?";
			String query3 = "DELETE FROM subscribe WHERE subscriber = ?";
			String query4 = "DELETE FROM subscribe WHERE mainUser = ?";
			String query5 = "SET FOREIGN_KEY_CHECKS=1";
			
			System.out.println(query0);
			pstmt0 = conn.prepareStatement(query0);			
			pstmt0.executeUpdate();
			
		    System.out.println(query);
			pstmt = conn.prepareStatement(query);			
			pstmt.setString(1,userName);
			pstmt.executeUpdate();	
			
			System.out.println(query1);
			pstmt1 = conn.prepareStatement(query1);			
			pstmt1.setString(1,userName);
			pstmt1.executeUpdate();
			
			System.out.println(query2);
			pstmt2 = conn.prepareStatement(query2);			
			pstmt2.setString(1,userName);
			pstmt2.executeUpdate();
			
			System.out.println(query3);
			pstmt3 = conn.prepareStatement(query3);			
			pstmt3.setString(1,userName);
			pstmt3.executeUpdate();
			
			System.out.println(query4);
			pstmt4 = conn.prepareStatement(query4);			
			pstmt4.setString(1,userName);
			pstmt4.executeUpdate();
			
			
			System.out.println(query5);
			pstmt5 = conn.prepareStatement(query5);			
			pstmt5.executeUpdate();
			
			
			
			return true;
			
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt0.close();
				pstmt.close();
				pstmt1.close();
				pstmt2.close();
				pstmt3.close();
				pstmt4.close();
				pstmt5.close();
				
				
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return false;
	}; 
	
	}
