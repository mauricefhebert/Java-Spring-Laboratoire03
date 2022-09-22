package com.example.laboratoire03.controllers;

import com.example.laboratoire03.models.Panier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    /**
     * Retourne la page d'accueil
     * @param session
     * @return
     */
    @GetMapping("/")
    public String Accueil(HttpSession session) {
        Panier panier = new Panier();
        if (session.getAttribute("panier") == null) {
            session.setAttribute("panier", panier);
        }
        return "accueil";
    }
}
