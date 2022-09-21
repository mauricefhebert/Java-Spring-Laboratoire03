package com.example.laboratoire03.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
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

    /**
     * Supprimer tous les elements de la liste
     */
    public void viderPanier() {
        Iterator<Cours> i = liste.iterator();
        while (i.hasNext()) {
            Cours cours = i.next();
            i.remove();
        }
    }
}
