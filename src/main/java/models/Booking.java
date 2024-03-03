package models;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.*;
import java.util.*;
import models.*;
import metier.*;

public class Booking {

    private String id;
    private Date start_date;
    private Date end_date;
    private String booking_status;
    private double total_price;
    private String car_id;
    private String user_id;

    public Booking(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getTotalPriceForBooking(double pricePerDay) {
        long diffInMillies = Math.abs(end_date.getTime() - start_date.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diffInDays * pricePerDay;
    }
    
    public static List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SingletonConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM booking");

            while (resultSet.next()) {
                Booking booking = new Booking(resultSet.getString("id"));
                booking.setStart_date(resultSet.getDate("start_date"));
                booking.setEnd_date(resultSet.getDate("end_date"));
                booking.setBooking_status(resultSet.getString("booking_status"));
                booking.setTotal_price(resultSet.getDouble("total_price"));
                booking.setUser_id(resultSet.getString("user_id"));
                booking.setCar_id(resultSet.getString("car_id"));
                bookings.add(booking);
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

        return bookings;
    }
    
    public String getModelByID(String bookingId) {
        String model = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SingletonConnection.getConnection();
            String query = "SELECT car.model FROM booking INNER JOIN car ON booking.car_id = car.id WHERE booking.id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, bookingId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                model = resultSet.getString("model");
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

        return model;
    }
    
    public String getLocationByID(String bookingId) {
        String location_id = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SingletonConnection.getConnection();
            String query = "SELECT car.location_id FROM booking INNER JOIN car ON booking.car_id = car.id WHERE booking.id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, bookingId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                location_id = resultSet.getString("location_id");
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

        return location_id;
    }
    
    public String getuserByID(String bookingId) {
        String email = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SingletonConnection.getConnection();
            String query = "SELECT user.email FROM booking INNER JOIN user ON booking.user_id = user.id WHERE booking.id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, bookingId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	email = resultSet.getString("email");
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

        return email;
    }
    
    public static List<Booking> getBookingsHistorique(String id) {
        List<Booking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SingletonConnection.getConnection();
            String query = "SELECT * FROM booking where user_id=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking(resultSet.getString("id"));
                booking.setStart_date(resultSet.getDate("start_date"));
                booking.setEnd_date(resultSet.getDate("end_date"));
                booking.setBooking_status(resultSet.getString("booking_status"));
                booking.setTotal_price(resultSet.getDouble("total_price"));
                booking.setUser_id(resultSet.getString("user_id"));
                booking.setCar_id(resultSet.getString("car_id"));
                bookings.add(booking);
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

        return bookings;
    }

}
