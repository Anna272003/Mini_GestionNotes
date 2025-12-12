package com.example.GestionNotes.controller;

import com.example.GestionNotes.model.Etudiant;
import com.example.GestionNotes.service.EtudiantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/etudiants")
@CrossOrigin("*")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @PostMapping("/ajouter")
    public ResponseEntity<Etudiant> ajouterEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant nouveauEtudiant = etudiantService.ajouterEtudiant(etudiant);
        return ResponseEntity.ok(nouveauEtudiant);
    }

    @GetMapping("/liste")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        return ResponseEntity.ok(etudiants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Etudiant>> getEtudiantById(@PathVariable Long id) {
        Optional<Etudiant> etudiant = etudiantService.getEtudiantById(id);
        return etudiant.isPresent() ? ResponseEntity.ok(etudiant) : ResponseEntity.notFound().build();
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Etudiant> modifierEtudiant(@PathVariable Long id, @RequestBody(required = true) Etudiant etudiant) {
        System.out.println("Modification demandée pour l'étudiant ID: " + id);
        System.out.println("Données reçues : " + etudiant);

        if (etudiant == null) {
            System.out.println("❌ ERREUR : L'objet reçu est NULL !");
            return ResponseEntity.badRequest().build();
        }
        Etudiant etudiantModifie = etudiantService.modifierEtudiant(id, etudiant);
        if (etudiantModifie != null) {
            return ResponseEntity.ok(etudiantModifie);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<String> supprimerEtudiant(@PathVariable Long id) {
        boolean isDeleted = etudiantService.supprimerEtudiant(id);
        return isDeleted ? ResponseEntity.ok("Étudiant supprimé avec succès") : ResponseEntity.notFound().build();
    }

    @GetMapping("/moyenne-classe")
    public ResponseEntity<Double> calculerMoyenneClasse() {
        double moyenne = etudiantService.calculerMoyenneClasse();
        return ResponseEntity.ok(moyenne);
    }

    @GetMapping("/admis")
    public ResponseEntity<List<Etudiant>> getEtudiantsAdmis() {
        List<Etudiant> etudiantsAdmis = etudiantService.getEtudiantsAdmis();
        return ResponseEntity.ok(etudiantsAdmis);
    }

    @GetMapping("/tous")
    public List<Etudiant> getTousLesEtudiants() {
        return etudiantService.getTousLesEtudiants();
    }
}
