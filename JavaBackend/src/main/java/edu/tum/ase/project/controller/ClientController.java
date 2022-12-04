package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Client;
import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/listAll")
    public List<Client> getAllBoxes() {
        return clientService.getAllClients();
    }

    @PostMapping("/create")
    public Client createClient(@RequestBody String emil, @RequestBody String pass) {
        Client newClient = new Client(emil, pass);
        return clientService.createClient(newClient);
    }

    @GetMapping("/{clientEmail}")
    public Optional<Client> getClientByEmail(@PathVariable(value = "clientEmail") final String email) {
        return clientService.findByEmail(email);
    }
}
