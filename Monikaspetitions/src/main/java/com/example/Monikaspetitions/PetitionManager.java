package com.example.yournamespetitions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PetitionManager {

    // This stores all petitions in memory (not a database)
    private List<Petition> petitions = new ArrayList<>();

    // Constructor - adds some sample data when app starts
    public PetitionManager() {
        petitions.add(new Petition("Save the Park", "We need to protect our local park from development"));
        petitions.add(new Petition("Better School Lunches", "Improve the quality of meals in schools"));
        petitions.add(new Petition("Fix the Roads", "Repair potholes in our neighborhood"));
    }

    // Home page - shows all petitions
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("petitions", petitions);
        return "index";
    }

    // Page to create new petition
    @GetMapping("/create")
    public String createForm() {
        return "create";
    }

    // Handle creating a new petition
    @PostMapping("/create")
    public String createPetition(@RequestParam String title, @RequestParam String description) {
        petitions.add(new Petition(title, description));
        return "redirect:/";
    }

    // Search page
    @GetMapping("/search")
    public String searchForm() {
        return "search";
    }

    // Handle search
    @GetMapping("/search/results")
    public String searchResults(@RequestParam String query, Model model) {
        List<Petition> results = new ArrayList<>();
        for (Petition p : petitions) {
            if (p.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    p.getDescription().toLowerCase().contains(query.toLowerCase())) {
                results.add(p);
            }
        }
        model.addAttribute("results", results);
        model.addAttribute("query", query);
        return "search-results";
    }

    // View individual petition and sign it
    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable int id, Model model) {
        Petition petition = findPetitionById(id);
        if (petition == null) {
            return "redirect:/";
        }
        model.addAttribute("petition", petition);
        return "petition";
    }

    // Handle signing a petition
    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable int id,
                               @RequestParam String name,
                               @RequestParam String email) {
        Petition petition = findPetitionById(id);
        if (petition != null) {
            petition.addSignature(name, email);
        }
        return "redirect:/petition/" + id;
    }

    // Helper method to find petition by ID
    private Petition findPetitionById(int id) {
        for (Petition p : petitions) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}