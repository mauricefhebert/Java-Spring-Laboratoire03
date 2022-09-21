package com.example.laboratoire03.controllers;

import com.example.laboratoire03.models.*;
import com.example.laboratoire03.models.Error;
import com.example.laboratoire03.services.AppDataContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AdminController {
    /**
     * Initialize le data context
     */
    private final AppDataContext dataContext;

    public AdminController(AppDataContext dataContext) {
        this.dataContext = dataContext;
    }

    private Admin adminSession;
    private Admin getAdmin(HttpSession session) throws Exception {
        try {
            adminSession = new Admin();
            if(session.getAttribute("admin") != null) {
                adminSession = (Admin)session.getAttribute("admin");
            } else {
                session.setAttribute("admin", null);
            }
            return adminSession;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/admin")
    public ModelAndView admin(Model model, HttpServletRequest request, HttpSession session) {
        try {
            model.addAttribute("model", new Admin());

            if(getAdmin(session) != null) {
                adminSession = getAdmin(session);
                return new ModelAndView("adminPanelFormEtudiant", "etudiant", new Etudiant());
            }

            session.setAttribute("admin", new Admin());

            return new ModelAndView("admin");

        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }

    }

    @PostMapping("/admin")
    public ModelAndView connection(@Valid @ModelAttribute("model") Admin admin, BindingResult result, HttpSession session, HttpServletRequest request) {
        try {
            if(result.hasErrors()) {
                return new ModelAndView("admin");
            }
            return new ModelAndView("redirect:/adminPanel/etudiant");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    @GetMapping("/adminPanel/etudiant")
    public ModelAndView showAdminPanelEtudiant(Model model)
    {
        model.addAttribute("model", new Etudiant());
        return new ModelAndView("adminPanelFormEtudiant");
    }

    @PostMapping("/adminPanel/etudiant")
    public ModelAndView ajouterEtudiant(@Valid @ModelAttribute("model") Etudiant etudiant, BindingResult result, HttpServletRequest request)
    {
        try {
            if(result.hasErrors()) {
                return new ModelAndView("adminPanelFormEtudiant");
            }
            dataContext.listeEtudiant.add(etudiant);
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    @GetMapping("/adminPanel/cours")
    public ModelAndView showAdminPanelCours(Model model)
    {
        int nextNum = dataContext.listeCours.size() + 1;
        Cours cours = new Cours();
        cours.setNumero(nextNum);
        model.addAttribute("model", cours);
        return new ModelAndView("adminPanelFormCours");
    }

    @PostMapping("/adminPanel/cours")
    public ModelAndView ajouterCours(@Valid @ModelAttribute("model") Cours cours, BindingResult result, HttpServletRequest request)
    {
        try {
            if(result.hasErrors()) {
                return new ModelAndView("adminPanelFormCours");
            }
            dataContext.listeCours.add(cours);
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }

    @GetMapping("/adminPanel/logout")
    public ModelAndView deconnectionAdminPanel(HttpServletRequest request, HttpSession session)
    {
        try {
            if(session.getAttribute("admin") != null)
            {
                session.removeAttribute("admin");
            }
            return new ModelAndView("redirect:/admin");
        } catch (Exception e) {
            return new ModelAndView("error", "model", new Error(request.getRequestURL().toString(), e));
        }
    }
}
