package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.jni.Address;

import metier.SingletonConnection;

public class User {

		private String id;
	    private String nom;
	    private String prenom;
	    private String email;
	    private String password;
	    private String phone_number;
	    private String address;
	    private String type;

	    public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public User(String id) {
	    	this.id = id;
	    }
		
		public String getId() {
			return id;
		}

	    public String getNom() {
	        return nom;
	    }

	    public void setNom(String nom) {
	        this.nom = nom;
	    }
	    
	    public String getPrenom() {
	        return prenom;
	    }

	    public void setPrenom(String prenom) {
	        this.prenom = prenom;
	    }


	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getPhone_number() {
	        return phone_number;
	    }

	    public void setPhone_number(String phone_number) {
	        this.phone_number = phone_number;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }
	    
	    public static String getIDbyEmail(String email) {
	        String ID = null;
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            connection = SingletonConnection.getConnection();
	            String query = "SELECT id FROM user WHERE email = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, email);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	            	ID = resultSet.getString("id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return ID;
	    }

}
