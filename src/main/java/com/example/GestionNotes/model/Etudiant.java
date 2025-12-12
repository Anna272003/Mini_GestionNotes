package com.example.GestionNotes.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "etudiant")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numEt;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private double noteMath;

    @Column(nullable = false)
    private double notePhys;

    // Constructeurs
    public Etudiant() {}

    public Etudiant(String numEt, String nom, double noteMath, double notePhys) {
        this.numEt = numEt;
        this.nom = nom;
        this.noteMath = noteMath;
        this.notePhys = notePhys;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonProperty("numEt")
    public String getNumEt() {
        return numEt;
    }

    public void setNumEt(String numEt) {
        this.numEt = numEt;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getNoteMath() {
        return noteMath;
    }

    public void setNoteMath(double noteMath) {
        this.noteMath = noteMath;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", numEt='" + numEt + '\'' +
                ", nom='" + nom + '\'' +
                ", noteMath=" + noteMath +
                ", notePhys=" + notePhys +
                '}';
    }

    public double getNotePhys() {
        return notePhys;
    }

    public void setNotePhys(double notePhys) {
        this.notePhys = notePhys;
    }

    public double getMoyenne() {
        return (noteMath + notePhys) / 2;
    }
}
