package com.example.Monikaspetitions;

import java.util.ArrayList;
import java.util.List;

public class Petition {
    private static int idCounter = 1;
    private int id;
    private String title;
    private String description;
    private List<Signature> signatures;

    public Petition(String title, String description) {
        this.id = idCounter++;
        this.title = title;
        this.description = description;
        this.signatures = new ArrayList<>();
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Signature> getSignatures() { return signatures; }

    public void addSignature(String name, String email) {
        signatures.add(new Signature(name, email));
    }

    public int getSignatureCount() {
        return signatures.size();
    }
}