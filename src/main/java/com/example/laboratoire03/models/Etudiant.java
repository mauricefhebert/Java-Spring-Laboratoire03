package com.example.laboratoire03.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// Génère les getters et les setters
@Getter
@Setter
public class Etudiant {

    @Pattern(regexp = "^\\d{3} \\d{3} \\d{3}$", message = "Veuillez respecter le format xxx xxx xxx")
    private String nas;
    private String nom;
    private String prenom;
    @Pattern(regexp = "^\\d{3} \\d{3}-\\d{4}$", message = "Veuillez respecter le format xxx xxx-xxx")
    private String telephone;
    private String adresse;
    private String codePostal;
    @NotBlank(message = "Le courriel ne peu étre vide")
    @Email(message = "Le format du courriel ne respect pas un format valid")
    private String courriel;
    private String genre;

    public Etudiant(){}

    public Etudiant(String nas, String nom, String prenom, String telephone, String adresse, String codePostal, String courriel, String genre) {
        this.nas = nas;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.courriel = courriel;
        this.genre = genre;
    }
}
