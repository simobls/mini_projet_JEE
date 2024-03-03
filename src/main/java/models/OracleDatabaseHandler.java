package models;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import models.*;
import metier.*;

public class OracleDatabaseHandler {
    private Connection connection;

    public OracleDatabaseHandler(String url, String user, String password) throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void insertObject(Object obj, String tableName) throws SQLException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = obj.getClass();

        // Extract attributes and values from the object using reflection
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = field.getName();
            Object value = field.get(obj);
            if (columnName.equals("id") && value == null)
            {
            	value = UUID.randomUUID().toString();
            }
            if (value != null) {
                if (columns.length() > 0) {
                    columns.append(", ");
                    placeholders.append(", ");
                }
                columns.append(columnName);
                placeholders.append("?");
            }
        }

        // Build the SQL query
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);

        // Prepare and execute the query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value != null) {
                    // Set the parameter without checking the type
                    preparedStatement.setObject(parameterIndex, value);

                    parameterIndex++;
                }
            }
            preparedStatement.executeUpdate();
        }
    }
    
    public boolean isCarAlreadyBooked(String carId, Date startDate, Date endDate) throws SQLException {
        String query = "SELECT * FROM booking WHERE car_id = ? AND ((start_date BETWEEN ? AND ?) OR (end_date BETWEEN ? AND ?))";
        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, carId);
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(endDate.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // true if there is a booking, false otherwise
            }
        }
    }
    
    public boolean hasBookingInDateRange(String userId, Date startDate, Date endDate) throws SQLException {
        String query = "SELECT * FROM booking WHERE user_id = ? AND ((start_date BETWEEN ? AND ?) OR (end_date BETWEEN ? AND ?))";
        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(5, new java.sql.Date(endDate.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Return true if there's at least one booking in the date range
            }
        }
    }

}
