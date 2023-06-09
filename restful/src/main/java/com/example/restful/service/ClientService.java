package com.example.restful.service;

import com.example.restful.model.Client;

import java.sql.SQLException;
import java.util.*;

public interface ClientService {

    boolean create(Client client);
    List<Client> readAll();

    Client read(int id);

    boolean update(int id, Client client);

    boolean delete(int id);

}
