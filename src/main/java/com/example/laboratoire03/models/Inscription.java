package com.example.laboratoire03.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Génère les getters et les setters
@Getter
@Setter
public class Inscription {
    private String nas;
    private Date dateInscription;
    private List<Cours> listeDeCours;
    
    public Inscription(){}

    public Inscription(String nas, Date dateInscription, List<Cours> listeDeCours) {
        this.nas = nas;
        this.dateInscription = dateInscription;
        this.listeDeCours = new ArrayList<>();
        this.listeDeCours.addAll(listeDeCours);
        }
}
