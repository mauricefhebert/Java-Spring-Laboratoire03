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
import java.util.List;

@Controller
public class CoursController {

    /**
     * Initialize le data context
     */
    private final AppDataContext dataContext;
    public CoursController(AppDataContext dataContext) {
        this.dataContext = dataContext;
    }

    /**
     * Récupère le panier dans la session s'il existe,
     * Sinon crée le panier et l'ajoute dans la session
     * @param session
     * @return Panier
     * @throws Exception
     */
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

    /**
     * Affiche la liste de cours dans la vue 'listeCours'
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/listeDeCours")
    public ModelAndView liste(HttpServletRequest request) throws Exception {
        try {
            return new ModelAndView("listeCours", "liste", dataContext.listeCours);
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    /**
     * Recuper le panier dans la session et génère la liste de cours, ajouter un cours au panier et sauvegarde la session
     * @param numero
     * @param session
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/listeDeCours/{numero}")
    public ModelAndView ajouter(@PathVariable("numero") int numero, HttpSession session, HttpServletRequest request) throws Exception {
        try {
            Panier panier = this.getPanier(session);
            Cours cours = dataContext.getCours(numero);
            List<Cours> liste = panier.getListe();
            panier.ajouterCours(cours);
            session.setAttribute("panier", panier);
            return new ModelAndView("redirect:/listeDeCours");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }
}
