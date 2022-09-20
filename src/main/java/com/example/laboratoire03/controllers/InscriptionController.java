package com.example.laboratoire03.controllers;

import com.example.laboratoire03.models.Error;
import com.example.laboratoire03.models.Inscription;
import com.example.laboratoire03.models.Panier;
import com.example.laboratoire03.services.AppDataContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

@Controller
public class InscriptionController {

    @Autowired
    private AppDataContext dataContext;

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

    @GetMapping("/afficherPanier")
    public ModelAndView afficher(HttpSession session, HttpServletRequest request) {
        try {
            Panier panier = this.getPanier(session);
            return new ModelAndView("afficherPanier", "panier", panier);
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    @GetMapping("/afficherPanier/{numero}")
    public ModelAndView supprimer(@PathVariable("numero") int numero ,HttpSession session, HttpServletRequest request) {
        try {
            Panier panier = this.getPanier(session);
            panier.supprimerCours(numero);
            session.setAttribute("panier", panier);
            return new ModelAndView("redirect:/afficherPanier", "panier", panier);
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    @GetMapping("/afficherListEtudiant")
    public ModelAndView afficherListeEtudiant(HttpServletRequest request)
    {
        try {
            return new ModelAndView("ValiderEtudiant", "listeEtudiant", dataContext.listeEtudiant);
        }
        catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    @GetMapping("/afficherListEtudiant/{nas}")
    public ModelAndView confirmer(@PathVariable("nas") String nas, HttpServletRequest request, HttpSession session) {
        try {
            Panier panier = this.getPanier(session);
            Inscription inscription = new Inscription(nas, new Date(), (Arrays)panier.getListe());
            dataContext.addInscription(inscription);
            return new ModelAndView("confirmation");
        }
        catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }
}
