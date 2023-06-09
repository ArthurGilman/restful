package com.example.restful.service;

import com.example.restful.model.Client;
import org.springframework.stereotype.Service;

import java.nio.channels.CancelledKeyException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClientServiceImpl implements ClientService {

    private static Connection connection;

    public ClientServiceImpl() throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Clients",
                    "root", "19731302");
        } catch (SQLException ex) {
            System.out.println("Connection failed");
            connection.close();
        }
    }

    @Override
    public List<Client> readAll() {
        List<Client> allClients = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM client");

            allClients = new ArrayList<>();
            while (results.next()) {
                Client client = new Client();
                client.setName(results.getString("name"));
                client.setEmail(results.getString("email"));
                client.setPhone(results.getString("phone"));
                allClients.add(client);
            }
            return allClients;
        } catch (SQLException ex) {
            System.out.println("smth went wrong with operation \" readAllClients \" ");
        }
        return allClients;
    }

    @Override
    public boolean create(Client client) {
        int count = 0;
        try {
            String insertQuery = "INSERT INTO client(name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());


            count = statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("smth went wrong with operation \" createClient \" ");
        }
        return count > 0;
    }

    @Override
    public Client read(int id) throws SQLException {
        Client client = null;
        try {
            String sqlQuery = "SELECT * FROM client WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();


            results.next();

            client = new Client();
            client.setName(results.getString("name"));
            client.setEmail(results.getString("email"));
            client.setPhone(results.getString("phone"));
        } catch (SQLException ex) {
            System.out.println("smth went wrong with operation \" readClient\" ");
            return null;
        }
        return client;
    }

    @Override
    public boolean update(int id, Client client) {
        int count = 0;
        try {
            String sqlQuery = "UPDATE client SET name = ?, email = ?, phone = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setInt(4, id);
            count = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("smth went wrong with operation \" updateClient\" ");
            return false;
        }
        return count > 0;
    }

    @Override
    public boolean delete(int id) {
        int count = 0;
        try {
            String sqlQuery = "DELETE FROM client WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("smth went wrong with operation \" deleteClient\" ");
        }
        return count > 0;
    }
}
