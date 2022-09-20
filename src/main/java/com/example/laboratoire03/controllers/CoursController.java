package com.example.laboratoire03.controllers;

import com.example.laboratoire03.models.*;
import com.example.laboratoire03.models.Error;
import com.example.laboratoire03.services.AppDataContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CoursController {

    private final AppDataContext dataContext;
    public CoursController(AppDataContext dataContext) {
        this.dataContext = dataContext;
    }

    private Panier getPanier(HttpSession session) throws Exception {
        try {
          Panier panier = new Panier();
          if(session.getAttribute("panier") != null) {
              panier = (Panier)session.getAttribute("panier");
          }
          return panier;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/listeDeCours")
    public ModelAndView liste() throws Exception {
        return new ModelAndView("listeCours", "liste", dataContext.listeCours);
    }

    @GetMapping("/listeDeCours/{numero}")
    public ModelAndView ajouter(@PathVariable("numero") int numero, HttpSession session, HttpServletRequest request) throws Exception {
        try {
            Panier panier = this.getPanier(session);
            Cours cours = dataContext.getCours(numero);
            panier.ajouterCours(cours);
            session.setAttribute("panier", panier);
            return new ModelAndView("redirect:/listeDeCours");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }
}
