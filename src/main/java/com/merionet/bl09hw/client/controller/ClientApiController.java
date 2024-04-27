package com.merionet.bl09hw.client.controller;

import com.merionet.bl09hw.client.dto.request.EditClientRequest;
import com.merionet.bl09hw.client.dto.request.RegistrationRequest;
import com.merionet.bl09hw.client.dto.response.ClientResponse;
import com.merionet.bl09hw.client.entity.ClientEntity;
import com.merionet.bl09hw.client.exceptions.BadRequestException;
import com.merionet.bl09hw.client.exceptions.ClientAlreadyExist;
import com.merionet.bl09hw.client.exceptions.ClientNotFoundException;
import com.merionet.bl09hw.client.repository.ClientRepository;
import com.merionet.bl09hw.client.routes.ClientRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ClientApiController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(ClientRoutes.REGISTRATION)
    public ClientResponse registration(@RequestBody RegistrationRequest request)
            throws BadRequestException, ClientAlreadyExist {

        request.validate();

        Optional<ClientEntity> check = clientRepository.findByEmail(request.getEmail());
        if (check.isPresent()) throw new ClientAlreadyExist();

        ClientEntity client = ClientEntity.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        client = clientRepository.save(client);
        return ClientResponse.of(client);
    }

    @PostMapping(ClientRoutes.ME)
    public ClientResponse edit(Principal principal, @RequestBody EditClientRequest request) {
        ClientEntity client = clientRepository
                .findByEmail(principal.getName())
                .orElseThrow(ClientNotFoundException::new);

        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setSecondName(request.getSecondName());
        client.setSnils(request.getSnils());
        client.setPassportId(request.getPassportId());
        client.setChronicDiseases(request.getChronicDiseases());

        client = clientRepository.save(client);
        return ClientResponse.of(client);
    }

    @GetMapping(ClientRoutes.ME)
    public ClientResponse me(Principal principal) {
        return clientRepository
                .findByEmail(principal.getName())
                .map(ClientResponse::of)
                .orElseThrow(ClientNotFoundException::new);
    }
}
