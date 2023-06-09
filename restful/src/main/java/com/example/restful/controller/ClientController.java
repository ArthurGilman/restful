package com.example.restful.controller;

import com.example.restful.model.Client;
import com.example.restful.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
public class ClientController {

    private ClientService clientService;


    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") int id) {
        Client client = clientService.read(id);
        return  client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @PostMapping(value = "/clients")
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        boolean response = clientService.create(client);
        return response ?
                new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<List<Client>> allClients() {
        ArrayList<Client> allClients = new ArrayList<>(clientService.readAll());


        return allClients != null && !allClients.isEmpty()
                ? new ResponseEntity<>(allClients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<?> updateClient(@PathVariable("id") int id, @RequestBody Client client) {


        return clientService.update(id, client)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") int id) {

        return clientService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
