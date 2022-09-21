package com.example.laboratoire03.controllers;

import com.example.laboratoire03.models.Error;
import com.example.laboratoire03.models.Etudiant;
import com.example.laboratoire03.models.Inscription;
import com.example.laboratoire03.models.Panier;
import com.example.laboratoire03.services.AppDataContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Controller
public class InscriptionController {
    /**
     * Initialize le data context
     */
    private final AppDataContext dataContext;
    public InscriptionController(AppDataContext dataContext) {
        this.dataContext = dataContext;
    }

    /**
     * Récupère le panier dans la session s'il existe,
     * Sinon crée le panier et l'ajoute dans la session
     * @param session
     * @return Panier
     * @throws Exception
     */
    private Panier getPanier(HttpSession session) {
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
     * Affiche le panier
     * @param session
     * @param request
     * @return
     */
    @GetMapping("/afficherPanier")
    public ModelAndView afficher(HttpSession session, HttpServletRequest request) {
        try {
            Panier panier = this.getPanier(session);
            return new ModelAndView("afficherPanier", "panier", panier);
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    /**
     * Supprimer un element du panier de session et rafraichit la liste de cours
     * @param numero
     * @param session
     * @param request
     * @return
     */
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

    /**
     * Affiche la liste des étudiants dans la vue 'ValidaterEtudiant'
     * @param request
     * @return
     */
    @GetMapping("/afficherListEtudiant")
    public ModelAndView afficherListeEtudiant(HttpServletRequest request, HttpSession session)
    {
        try {
            return new ModelAndView("ValiderEtudiant", "listeEtudiant", dataContext.listeEtudiant);
        }
        catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    /**
     * Confirm l'inscription d'un étudiant a une liste de cours, vide le panier
     * @param nas
     * @param request
     * @param session
     * @return
     */
    @GetMapping("/afficherListEtudiant/{nas}")
    public ModelAndView confirmer(@PathVariable("nas") String nas, HttpServletRequest request, HttpSession session, Model model) {
        try {
            Etudiant etudiant = dataContext.listeEtudiant.stream().filter(e -> Objects.equals(e.getNas(), nas)).findFirst().get();
            Panier panier = this.getPanier(session);
            Inscription inscription = new Inscription(nas, new Date(), panier.getListe());
            dataContext.addInscription(inscription);
            model.addAttribute("listeCours", inscription.getListeDeCours());
            panier.viderPanier();
            session.setAttribute("panier", panier);
            return new ModelAndView("confirmation", "etudiant", etudiant);
        }
        catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }
}
