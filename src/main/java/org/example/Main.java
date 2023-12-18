package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.database.DatabaseConnection;
import org.example.impl.Railway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buff = new BufferedReader(isr);
        int selectedOperation;

        // connecting to database
        String connectionString = "mongodb://localhost:27017";
        DatabaseConnection mongoClient = new DatabaseConnection();
        mongoClient.connect(connectionString);

        String databaseName = "railway_ticketing_db";
        mongoClient.getDatabase(databaseName);

        String collectionName = "tickets";
        MongoCollection<Document> tickets = mongoClient.getCollection(collectionName);

        // menu-driven program starts here
        System.out.print("\nWelcome to the Railway Ticketing System\n\n");
        do {
           System.out.print("\nSelect an option : \n");
           System.out.print("0. Exit\n1. Save a ticket\n2. Update a ticket by ID\n3. Remove a ticket by ID\n4. Find tickets by title\n5. Find tickets by cost range\n6. Find tickets by type\n7. Find tickets by cost\n8. Remove tickets that are more than two years old\n9. Display all tickets\n");

            try {
                selectedOperation = Integer.parseInt(buff.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("User selected option " + selectedOperation + "\n\n");

            Railway railway = new Railway(tickets);

            switch (selectedOperation) {
                case 1 : railway.saveTicket(); break;
                case 2 : railway.updateTicketById(); break;
                case 3 : railway.removeTicketById(); break;
                case 4 : railway.findByTitle(); break;
                case 5 : railway.findByCostRange(); break;
                case 6 : railway.findByType(); break;
                case 7 : railway.findByCost(); break;
                case 8 : railway.removeOldTickets(); break;
                case 9 : railway.displayAllTickets(); break;
            }
        } while (selectedOperation != 0);

        // closing connection to database
        mongoClient.close();
    }
}