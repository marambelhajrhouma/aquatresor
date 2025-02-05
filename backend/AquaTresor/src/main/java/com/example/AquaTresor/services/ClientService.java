package com.example.AquaTresor.services;

import com.example.AquaTresor.entities.Client;
import com.example.AquaTresor.repositories.ClientRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveClient(Client client) {
        // Hacher le mot de passe avant d'enregistrer le client
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = findClientByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException("Client non trouvé");
        }
        return new org.springframework.security.core.userdetails.User(
            client.getEmail(), client.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );
    }
}
