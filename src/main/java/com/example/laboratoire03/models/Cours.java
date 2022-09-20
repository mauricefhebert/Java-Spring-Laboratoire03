package com.example.laboratoire03.models;

import lombok.Getter;
import lombok.Setter;

// Génère les getters et les setters
@Getter
@Setter
public class Cours {
    private int numero;
    private String intitule;
    private int nombreDeCredits;
    private String session;
    private String planCours;

    // Constructeur par défaut
    public Cours(){}

    // Constructeur par paramètres
    public Cours(int numero, String intitule, int nombreDeCredits, String session, String planCours) {
        this.numero = numero;
        this.intitule = intitule;
        this.nombreDeCredits = nombreDeCredits;
        this.session = session;
        this.planCours = planCours;
    }
}
