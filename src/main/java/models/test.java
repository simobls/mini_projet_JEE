package models;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {

	public static void main(String[] args) {
        // Définir les paramètres de recherche
        String locationId = "175b54d4-2984-49c0-b205-e20d57dbf016"; // Exemple : ID de la location
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse("2024-03-10"); // Exemple : Date de début
            endDate = sdf.parse("2024-03-15");   // Exemple : Date de fin
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Afficher les voitures disponibles pour les paramètres de recherche
        List<Car> cars = Car.getAvailableCars(locationId, startDate, endDate);
        if (cars.isEmpty()) {
            System.out.println("Aucune voiture disponible pour la période sélectionnée.");
        } else {
            System.out.println("Voitures disponibles :");
            for (Car car : cars) {
                System.out.println(car.getModel());
            }
        }
    }



}
