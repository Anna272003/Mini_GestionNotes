package com.example.GestionNotes.service;

import com.example.GestionNotes.model.Etudiant;
import com.example.GestionNotes.repository.EtudiantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    // Ajouter un nouvel étudiant
    public Etudiant ajouterEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // Récupérer tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    // Récupérer un étudiant par son numéro étudiant
    public Optional<Etudiant> getEtudiantById(Long id) {
        return etudiantRepository.findById(id);
    }

    // Modifier un étudiant
    public Etudiant modifierEtudiant(Long id, Etudiant etudiantDetails) {
        try {
            Optional<Etudiant> etudiantOpt = etudiantRepository.findById(id);

            if (etudiantOpt.isPresent()) {
                Etudiant etudiant = etudiantOpt.get();
                System.out.println("Avant modification : " + etudiant);

                // Ajout d'une vérification de null avant modification
                if (etudiantDetails.getNumEt() != null && !etudiantDetails.getNumEt().isEmpty()) {
                    etudiant.setNumEt(etudiantDetails.getNumEt());
                } else {
                    System.out.println("⚠️ WARNING : numEt est NULL ou vide, il ne sera pas modifié !");
                }

                etudiant.setNom(etudiantDetails.getNom());
                etudiant.setNoteMath(etudiantDetails.getNoteMath());
                etudiant.setNotePhys(etudiantDetails.getNotePhys());

                // Vérification avant sauvegarde
                System.out.println("Données avant sauvegarde : " + etudiant);
                Etudiant updatedEtudiant = etudiantRepository.save(etudiant);
                System.out.println("Après modification : " + updatedEtudiant);

                return updatedEtudiant;
            } else {
                throw new RuntimeException("Étudiant non trouvé !");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur complète
            throw new RuntimeException("Erreur lors de la modification : " + e.getMessage());
        }
    }

    public boolean supprimerEtudiant(Long id) {
        System.out.println("Vérification de l'existence de l'étudiant avec id : " + id);

        Optional<Etudiant> etudiant = etudiantRepository.findById(id); // Recherche par id
        if (etudiant.isPresent()) {
            System.out.println("L'étudiant existe, suppression en cours...");
            etudiantRepository.delete(etudiant.get()); // Suppression
            System.out.println("Suppression réussie !");
            return true;
        } else {
            System.out.println("Aucun étudiant trouvé avec cet id !");
            return false;
        }
    }

    // Calculer la moyenne de la classe
    public double calculerMoyenneClasse() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        if (etudiants.isEmpty()) return 0.0;

        double somme = etudiants.stream().mapToDouble(Etudiant::getMoyenne).sum();
        return somme / etudiants.size();
    }

    // Récupérer les étudiants admis (moyenne >= 10)
    public List<Etudiant> getEtudiantsAdmis() {
        return etudiantRepository.findByNoteMathGreaterThanEqualAndNotePhysGreaterThanEqual(10, 10);
    }

    public List<Etudiant> getTousLesEtudiants() {
        return etudiantRepository.findAll();
    }


}



