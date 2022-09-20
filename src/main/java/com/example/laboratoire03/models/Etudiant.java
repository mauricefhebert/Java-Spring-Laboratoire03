package com.example.laboratoire03.models;

import lombok.Getter;
import lombok.Setter;

// Génère les getters et les setters
@Getter
@Setter
public class Etudiant {
    private String nas;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String codePostal;
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
