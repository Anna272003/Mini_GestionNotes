package com.example.GestionNotes.repository;

import com.example.GestionNotes.model.Etudiant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
    Optional<Etudiant> findById(Long id);

    // Trouver tous les étudiants avec une moyenne supérieure ou égale à 10
    List<Etudiant> findByNoteMathGreaterThanEqualAndNotePhysGreaterThanEqual(double minMath, double minPhys);
}


