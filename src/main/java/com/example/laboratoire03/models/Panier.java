package com.example.laboratoire03.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Panier {
    private List<Cours> liste;

    public Panier() {
        this.liste = new ArrayList<>();
    }

    public void ajouterCours(Cours cours) {
        liste.add(cours);
    }

    public void supprimerCours(int numero) {
        liste.removeIf(n -> n.getNumero() == numero);
    }

    public void viderPanier() {
        for (Cours cours : liste) {
            liste.remove(cours);
        }
    }
}
