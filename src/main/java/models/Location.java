package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.SingletonConnection;

public class Location {

    private String id;
    private String name;
    private String address;

    public Location(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static List<Location> getAllLocations() throws SQLException {
        List<Location> locations = new ArrayList<>();
        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM location");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Location location = new Location(resultSet.getString("id"));
                location.setName(resultSet.getString("name"));
                location.setAddress(resultSet.getString("address"));
                locations.add(location);
            }
        }
        return locations;
    }

    public static Location getLocationById(String id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM location WHERE id = ?")) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Location location = new Location(resultSet.getString("id"));
                    location.setName(resultSet.getString("name"));
                    location.setAddress(resultSet.getString("address"));
                    return location;
                } else {
                    throw new SQLException("Location with ID " + id + " not found");
                }
            }
        }
    }
    
    public static String getNameById(String id) throws SQLException {
        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM location WHERE id = ?")) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    throw new SQLException("Location with ID " + id + " not found");
                }
            }
        }
    }
    
    public static String getLocationbyID(String id) {
        String name = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SingletonConnection.getConnection();
            String query = "SELECT name FROM location WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	name = resultSet.getString("name");
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

        return name;
    }



    public static int getLocationIdByName(String name) throws SQLException {
        int locationId = -1;
        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM location WHERE name = ?")) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    locationId = resultSet.getInt("id");
                }
            }
        }
        return locationId;
    }
    
    
    
    public void supprimerLocation(String id) {
	    Connection connection = SingletonConnection.getConnection();
	    try {
	        PreparedStatement ps = connection.prepareStatement("DELETE FROM location WHERE id = ?");
	        ps.setString(1, id);
	        ps.executeUpdate();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    
    public void updateLocation(Location location) {
	    Connection connection = SingletonConnection.getConnection();
	    try {
	        PreparedStatement ps = connection.prepareStatement("UPDATE location  SET name = ?, address = ? WHERE id = ?");
	        ps.setString(1, location.getName());
	        ps.setString(2, location.getAddress());
	        ps.setString(3, location.getId());
	        ps.executeUpdate();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
