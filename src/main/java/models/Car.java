package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import metier.SingletonConnection;

public class Car {
    
	private String id;
    private String type;
    private String model;
    private int year;
    private String availability_status;
    private double price_per_day;
    private String image_url;
    private String location_id;

    public Car(String id) {
    	this.id = id;
    }
    
    public String getId() {
    	return id;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getAvailability_status() {
		return availability_status;
	}

	public void setAvailability_status(String availability_status) {
		this.availability_status = availability_status;
	}

	public double getPrice_per_day() {
		return price_per_day;
	}

	public void setPrice_per_day(double price_per_day) {
		this.price_per_day = price_per_day;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
    
    public String getLocationName(String locationId) {
        try (Connection connection = SingletonConnection.getConnection()) {
            if (!connection.isClosed()) {
                Location location = Location.getLocationById(locationId);
                if (location != null) {
                    return location.getName();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ""; // Retourne l'ID de la location si la location n'est pas trouvée
    }
    
    public static List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        try {
            connection = SingletonConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM car");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car(resultSet.getString("id"));
                car.setType(resultSet.getString("type"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setAvailability_status(resultSet.getString("availability_status"));
                car.setPrice_per_day(resultSet.getDouble("price_per_day"));
                car.setImage_url(resultSet.getString("image_url"));
                car.setLocation_id(resultSet.getString("location_id"));
                cars.add(car);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return cars;
    }
    
    public static List<Car> getAvailableCars(String pickupLocation, Date startDate, Date endDate) {
        List<Car> availableCars = new ArrayList<>();

        try {
            Connection connection = SingletonConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM car WHERE location_id = ? AND id NOT IN (SELECT car_id FROM booking WHERE start_date <= ? AND end_date >= ?)");
            preparedStatement.setString(1, pickupLocation);
            preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Car car1 = new Car(resultSet.getString("id"));
                car1.setType(resultSet.getString("type"));
                car1.setModel(resultSet.getString("model"));
                car1.setPrice_per_day(resultSet.getDouble("price_per_day"));
                car1.setLocation_id(resultSet.getString("location_id"));
                car1.setImage_url(resultSet.getString("image_url"));
                availableCars.add(car1);
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableCars;
    }
    
    public void updateCar(Car car) {
	    Connection connection = SingletonConnection.getConnection();
	    try {
	        PreparedStatement ps = connection.prepareStatement("UPDATE car SET model = ?, type = ?,year = ?,price_per_day = ?, image_url = ?, location_id = ? WHERE id = ?");
	        ps.setString(1, car.getModel());
	        ps.setString(2, car.getType());
	        ps.setInt(3, car.getYear());
	        ps.setDouble(4, car.getPrice_per_day());
	        ps.setString(5, car.getImage_url());
	        ps.setString(6, car.getLocation_id());
	        ps.setString(7, car.getId());
	        ps.executeUpdate();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    public void supprimerCar(String id) {
	    Connection connection = SingletonConnection.getConnection();
	    try {
	        PreparedStatement ps = connection.prepareStatement("DELETE FROM car WHERE id = ?");
	        ps.setString(1, id);
	        ps.executeUpdate();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    
    public static String getImageById(String id) {
        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT image_url FROM car WHERE id = ?")) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("image_url");
                } else {
                    return null; // ou une image par défaut si aucune image n'est trouvée
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
