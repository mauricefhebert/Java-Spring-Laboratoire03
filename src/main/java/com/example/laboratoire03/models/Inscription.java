package com.example.laboratoire03.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;

// Génère les getters et les setters
@Getter
@Setter
public class Inscription {
    private String nas;
    private Date dateInscription;
    private Arrays listeDeCours;
    
    public Inscription(){}

    public Inscription(String nas, Date dateInscription, Arrays listeDeCours) {
        this.nas = nas;
        this.dateInscription = dateInscription;
        this.listeDeCours = listeDeCours;
    }
}
