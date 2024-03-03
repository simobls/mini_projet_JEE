package metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

public class UserDao {
	private static Connection cnx;
	
	static {
		cnx=SingletonConnection.getConnection();
	}
	
	public User getAdmin(String email,String password) {
		User user = null;
		try {
			PreparedStatement ps = cnx.prepareStatement("select * from user where email= ? and password = ? and type = 'admin'");
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user = new User(rs.getString("id"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setPhone_number(rs.getString("phone_number"));
				user.setPassword(rs.getString("password"));
				user.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return user;
	}
	
	public User getUser(String email,String password) {
		User user = null;
		try {
			PreparedStatement ps = cnx.prepareStatement("select * from user where email= ? and password = ? and type = 'client'");
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user = new User(rs.getString("id"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setPhone_number(rs.getString("phone_number"));
				user.setPassword(rs.getString("password"));
				user.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return user;
	}
}
