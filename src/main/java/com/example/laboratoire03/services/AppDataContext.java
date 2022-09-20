package com.example.laboratoire03.services;

import com.example.laboratoire03.models.Cours;
import com.example.laboratoire03.models.Etudiant;
import com.example.laboratoire03.models.Inscription;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppDataContext {
    public ArrayList<Cours> listeCours = new ArrayList() {
        {
            add(new Cours(1, "Java", 10, "Hiver", "423-10-3 Programmation en java"));
            add(new Cours(2, "Php", 12, "Hiver", "419-7-3 Programmation en Php"));
            add(new Cours(3, "JavaScript", 15, "Automne", "421-8-4 Programmation en javaScript"));
        }
    };

    public ArrayList<Etudiant> listeEtudiant = new ArrayList() {
        {
            add(new Etudiant("234 123 098", "Tremblay", "Alex", "(819) 555-5555", "423 Street", "JX4 V4D", "TremblayAlex@gmail.com", "Masculin"));
            add(new Etudiant("654 321 254", "Dupuis", "Bob", "(819) 333-3333", "123 Chemin", "JP4 F1E", "DupuisBob@gmail.com", "Masculin"));
            add(new Etudiant("134 416 126", "Bourdages", "Caroline", "(819) 983-3251", "123 Boulevard", "JD7 S9H", "BourdagesCaroline@gmail.com", "Féminin"));
        }
    };
    public ArrayList<Inscription> listeInscription = new ArrayList<>();

    public void addInscription(Inscription inscription) {
        listeInscription.add(inscription);
    }

    public Cours getCours(int id) {
        return listeCours.stream().filter(n -> n.getNumero() == id).findFirst().orElse(null);
    }
}
