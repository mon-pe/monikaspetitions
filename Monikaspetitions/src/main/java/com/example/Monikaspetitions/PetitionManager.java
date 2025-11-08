package com.example.Monikaspetitions;

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
        petitions.add(new Petition("Save Moore Street From Demolition",  "Moore Street is a site of immense historical significance to Ireland's struggle for independence. We call on the government to protect this heritage site from commercial development and preserve it for future generations as a national monument."));
        petitions.add(new Petition("Better Flood Defences ", "Recent flooding has caused severe damage to homes and businesses in our community. We demand immediate action to improve drainage systems, install flood barriers, and protect residents from future flooding disasters."));
        petitions.add(new Petition("Fix the Road in Cloyne", "The main road through Cloyne has deteriorated to dangerous levels with potholes and cracked surfaces. We urge the local council to prioritize urgent repairs to ensure the safety of motorists, cyclists, and pedestrians."));

    }

    // Home page - shows all petitions
    @GetMapping("/")
    public String home(Model model) {
        // Pass all petitions to the view for display
        model.addAttribute("petitions", petitions);
        return "index"; // Returns the index.html template
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