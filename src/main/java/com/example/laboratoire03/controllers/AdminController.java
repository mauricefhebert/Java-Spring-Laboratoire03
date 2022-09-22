package com.example.laboratoire03.controllers;

import com.example.laboratoire03.models.Admin;
import com.example.laboratoire03.models.Cours;
import com.example.laboratoire03.models.Error;
import com.example.laboratoire03.models.Etudiant;
import com.example.laboratoire03.services.AppDataContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class AdminController {
    /**
     * Initialize le data context
     */
    private final AppDataContext dataContext;
    public AdminController(AppDataContext dataContext) {
        this.dataContext = dataContext;
    }

    /**
     * Récupère la page de connection pour l'admin si la session contient adminSession redirige vers la page d'ajout d'étudiant
     * @param model
     * @param request
     * @param session
     * @return
     */
    @GetMapping("/admin")
    public ModelAndView admin(Model model, HttpServletRequest request, HttpSession session) {
        try {
            if(session.getAttribute("adminSession") != null)
            {
                return new ModelAndView("redirect:/adminPanel/etudiant");
            }

            model.addAttribute("model", new Admin());
            return new ModelAndView("admin");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }

    }

    /**
     * Effectue connection pour l'admin si la connection est réussi ajoute adminSession dans la session
     * pour maintenir la connection active entre les pages.
     * Si la connection n'est pas réussi affiche des messages d'erreur définit sur le model
     * @param admin
     * @param result
     * @param session
     * @param request
     * @return
     */
    @PostMapping("/admin")
    public ModelAndView connection(@Valid @ModelAttribute("model") Admin admin, BindingResult result, HttpSession session, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return new ModelAndView("admin");
            }
            session.setAttribute("adminSession", admin);
            return new ModelAndView("redirect:/adminPanel/etudiant");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    /**
     * Récupère la page de creation d'étudiant. Si un utilisateur veut naviguer vers la page sans être connecté
     * retourne une page d'accès non authorizer
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/adminPanel/etudiant")
    public ModelAndView showAdminPanelEtudiant(Model model, HttpSession session) {
        if(session.getAttribute("adminSession") == null)
            return new ModelAndView("accessDenied");

        Etudiant etudiant = new Etudiant();
        etudiant.setGenre("Masculin");
        model.addAttribute("model", etudiant);
        return new ModelAndView("adminPanelFormEtudiant");
    }

    /**
     * Permet de créer un étudiant et de l'ajouter dans la liste d'étudiant
     * si le formulaire contient des erreurs retourne des messages d'erreurs et prévient l'envoie du formulaire
     * @param etudiant
     * @param result
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/adminPanel/etudiant")
    public ModelAndView ajouterEtudiant(@Valid @ModelAttribute("model") Etudiant etudiant, BindingResult result, HttpServletRequest request, HttpSession session) {
        try {
            if(session.getAttribute("adminSession") == null)
                return new ModelAndView("accessDenied");
            if (result.hasErrors()) {
                return new ModelAndView("adminPanelFormEtudiant");
            }
            dataContext.listeEtudiant.add(etudiant);
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    /**
     * Récupère la page de creation de cours. Si un utilisateur veut naviguer vers la page sans être connecté
     * retourne une page d'accès non authorizer
     * @param model
     * @return
     */
    @GetMapping("/adminPanel/cours")
    public ModelAndView showAdminPanelCours(Model model) {
        int nextNum = dataContext.listeCours.size() + 1;
        Cours cours = new Cours();
        cours.setNumero(nextNum);
        HashMap<String, String> sessionCegep = new HashMap<>();
        sessionCegep.put("hiver", "Hiver");
        sessionCegep.put("ete", "Été");
        sessionCegep.put("automne", "Automne");
        sessionCegep.put("printemps", "Printemps");

        model.addAttribute("sessionCegep", sessionCegep);
        model.addAttribute("cours", cours);
        return new ModelAndView("adminPanelFormCours");
    }

    /**
     * Permet de créer un cours et de l'ajouter dans la liste de cours
     * si le formulaire contient des erreurs retourne des messages d'erreurs et prévient l'envoie du formulaire
     * @param cours
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/adminPanel/cours")
    public ModelAndView ajouterCours(@Valid @ModelAttribute("cours") Cours cours, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return new ModelAndView("adminPanelFormCours");
            }
            dataContext.listeCours.add(cours);
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    /**
     * Permet la deconnection de l'admin.
     * Quand l'admin se deconnect object adminSession et retirée de la session
     * @param session
     * @return
     */
    @GetMapping("/adminPanel/logout")
    public ModelAndView adminPanelLogout(HttpSession session)
    {
        if(session.getAttribute("adminSession") != null)
        {
            session.removeAttribute("adminSession");
            return new ModelAndView("redirect:/admin");
        }
        return new ModelAndView("redirect:/adminPanel/etudiant");
    }
}
