package com.merionet.bl09hw.client.dto.request;

import lombok.Getter;

@Getter
public class EditClientRequest {
    private String firstName;
    private String lastName;
    private String secondName;

    protected String snils;
    protected String passportId;
    protected String chronicDiseases;
}
