package com.merionet.bl09hw.client.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "clients")

public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    protected Long id;

    protected String firstName;
    protected String lastName;
    protected String secondName;

    protected String snils;
    protected String passportId;
    protected String chronicDiseases;

    protected String email;
    protected String password;
}
