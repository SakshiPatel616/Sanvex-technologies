package com.sanvex.controller;

import com.sanvex.model.ContactForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sanvex.entity.ContactMessage;
import com.sanvex.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WebController {
	@Autowired
	private ContactRepository contactRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("page", "home");
        model.addAttribute("contactForm", new ContactForm());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("page", "about");
        return "about";
    }

    @GetMapping("/solutions")
    public String solutions(Model model) {
        model.addAttribute("page", "solutions");
        return "solutions";
    }

    @GetMapping("/industries")
    public String industries(Model model) {
        model.addAttribute("page", "industries");
        return "industries";
    }

    @GetMapping("/resources")
    public String resources(Model model) {
        model.addAttribute("page", "resources");
        return "resources";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("page", "contact");
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    @PostMapping("/contact/submit")
    public String submitContact(@Valid @ModelAttribute("contactForm") ContactForm form,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("page", "contact");
            return "contact";
        }

        // Create entity
        ContactMessage message = new ContactMessage();

        // Copy data from form to entity
        message.setName(form.getName());
        message.setEmail(form.getEmail());
        message.setCompany(form.getCompany());
        message.setPhone(form.getPhone());
        message.setService(form.getService());
        message.setMessage(form.getMessage());

        // Save to database
        contactRepository.save(message);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Thank you, " + form.getName() + "! We'll be in touch within 24 hours."
        );

        return "redirect:/contact";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("page", "services");
        return "services";
    }
}
