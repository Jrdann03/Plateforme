package com.plateforme.plateforme.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommercantRequest(

        @NotBlank
        @Size(max = 100)
        String nom,

        @NotBlank
        @Size(max = 100)
        String prenom,

        @NotBlank
        @Email
        @Size(max = 255)
        String email,

        @Size(max = 30)
        String telephone,

        @Size(max = 255)
        String adresse,

        @NotBlank
        @Size(max = 100)
        String sousDomaine
) {
}