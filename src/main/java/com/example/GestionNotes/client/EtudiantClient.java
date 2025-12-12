package com.example.GestionNotes.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.GestionNotes.model.Etudiant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class EtudiantClient {
    private static final String BASE_URL = "http://localhost:8081/api/etudiants";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static Etudiant ajouterEtudiant(Etudiant etudiant) {
        return restTemplate.postForObject(BASE_URL + "/ajouter", etudiant, Etudiant.class);
    }

    public static List<Etudiant> getAllEtudiants() {
        ResponseEntity<Etudiant[]> response = restTemplate.getForEntity(BASE_URL + "/liste", Etudiant[].class);
        return Arrays.asList(response.getBody());
    }

    public static boolean modifierEtudiant(Long id, Etudiant etudiant) {
        try {
            String url = BASE_URL + "/modifier/" + id;
            System.out.println("Avant modification - ID: " + id);
            System.out.println("Avant modification - Etudiant: " + etudiant);
            System.out.println("Avant modification - numEt: " + etudiant.getNumEt());
            if (etudiant.getNumEt() == null || etudiant.getNumEt().isEmpty()) {
                System.out.println("⚠️ WARNING : numEt est NULL ou vide avant l'envoi !");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(etudiant);
            System.out.println("Données de l'objet avant conversion en JSON : " + etudiant);
            System.out.println("Données envoyées en JSON : " + json);

            restTemplate.put(url, etudiant);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la modification : " + e.getMessage());
            return false;
        }
    }

    public static void supprimerEtudiant(Long id) { // Modifier pour accepter Long
        String url = BASE_URL + "/supprimer/" + id; // Modifier l'URL avec id
        System.out.println("URL utilisée pour DELETE : " + url); // Vérifier l’URL
        try {
            restTemplate.delete(url);
            System.out.println("Suppression réussie !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static double getMoyenneClasse() {
        String url = BASE_URL + "/moyenne-classe";
        ResponseEntity<Double> response = restTemplate.getForEntity(url, Double.class);
        return response.getBody();
    }

    public static List<Etudiant> getTousEtudiants() {
        String url = BASE_URL + "/tous"; // Vérifie que cette URL correspond bien à ton backend
        ResponseEntity<Etudiant[]> response = restTemplate.getForEntity(url, Etudiant[].class);
        return Arrays.asList(response.getBody());
    }
}

