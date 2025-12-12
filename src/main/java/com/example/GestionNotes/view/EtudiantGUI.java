package com.example.GestionNotes.view;

import com.example.GestionNotes.client.EtudiantClient;
import com.example.GestionNotes.model.Etudiant;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

import java.awt.GradientPaint;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


public class EtudiantGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNumEt, txtNom, txtNoteMath, txtNotePhys;
    private JButton btnAjouter, btnRafraichir, btnModifier, btnSupprimer, btnMoyenneClasse, btnEtudiantsAdmis,btnAfficherHistogramme;
    private JLabel lblMoyenneClasse,lblMoyenneMin,lblMoyenneMax,lblAdmis,lblRedoublants;
    private double moyenneClasse, moyenneMin, moyenneMax;

    public EtudiantGUI() {
        // Configuration de base
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Gestion des Étudiants");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 250));

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Num", "Nom", "Math", "Phys", "Moyenne"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(200, 220, 240));
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Panel du formulaire
        JPanel panelForm = new JPanel(new GridLayout(2, 4, 10, 10));
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(new Color(230, 240, 250));

        txtNumEt = new JTextField(); txtNumEt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNom = new JTextField(); txtNom.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNoteMath = new JTextField(); txtNoteMath.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNotePhys = new JTextField(); txtNotePhys.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panelForm.add(createStyledLabel("Numéro :"));
        panelForm.add(txtNumEt);
        panelForm.add(createStyledLabel("Nom :"));
        panelForm.add(txtNom);
        panelForm.add(createStyledLabel("Math :"));
        panelForm.add(txtNoteMath);
        panelForm.add(createStyledLabel("Phys :"));
        panelForm.add(txtNotePhys);

        // Panel pour le bouton Ajouter
        JPanel panelAjouter = new JPanel();
        btnAjouter = new JButton("Ajouter");
        btnAjouter.setPreferredSize(new Dimension(120, 30));
        panelAjouter.add(btnAjouter);

        styleButton(btnAjouter, new Color(46, 204, 113), Color.BLACK);

        // Hover effect
        btnAjouter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAjouter.setBackground(new Color(39, 174, 96));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAjouter.setBackground(new Color(46, 204, 113));
            }
        });

        // Conteneur haut (formulaire + bouton Ajouter)
        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.add(panelForm, BorderLayout.CENTER);
        panelHaut.add(panelAjouter, BorderLayout.SOUTH);
        add(panelHaut, BorderLayout.NORTH);

        // Panel des boutons restants
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnAfficherHistogramme = new JButton("Afficher Histogramme");

        styleButton(btnModifier, new Color(59, 130, 246), Color.BLACK);
        styleButton(btnSupprimer, new Color(255, 99, 71), Color.BLACK);
        styleButton(btnAfficherHistogramme, new Color(253, 191, 2), Color.BLACK);

        panelButtons.add(btnModifier);
        panelButtons.add(btnSupprimer);
        panelButtons.add(btnAfficherHistogramme);

        // Panel des statistiques
        JPanel panelStats = new JPanel(new GridLayout(2, 3, 10, 5));
        panelStats.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(59, 130, 246), 2),
                "Statistiques",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(59, 130, 246)
        ));
        panelStats.setBackground(new Color(245, 247, 251));

        lblMoyenneClasse = new JLabel("Moyenne de la classe : 0.00");
        lblAdmis = new JLabel("Étudiants admis : 0");
        lblRedoublants = new JLabel("Étudiants redoublants : 0");
        lblMoyenneMin = new JLabel("Moyenne minimale : 0.00");
        lblMoyenneMax = new JLabel("Moyenne maximale : 0.00");


        Font statsFont = new Font("Arial", Font.BOLD, 12);
        lblMoyenneClasse.setFont(statsFont);
        lblMoyenneMin.setFont(statsFont);
        lblMoyenneMax.setFont(statsFont);
        lblAdmis.setFont(statsFont);
        lblRedoublants.setFont(statsFont);

        panelStats.add(lblMoyenneClasse);
        panelStats.add(lblMoyenneMin);
        panelStats.add(lblMoyenneMax);
        panelStats.add(lblAdmis);
        panelStats.add(lblRedoublants);

        // Panel du bas
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBorder(new EmptyBorder(5, 10, 10, 10));
        panelBas.add(panelButtons, BorderLayout.NORTH);
        panelBas.add(panelStats, BorderLayout.CENTER);

        add(panelBas, BorderLayout.SOUTH);

        // Action Listeners
        btnAjouter.addActionListener(e -> {
            ajouterEtudiant();
            mettreAJourStatistiques();
        });
        btnModifier.addActionListener(e -> {
            modifierEtudiant();
            mettreAJourStatistiques();
        });
        btnSupprimer.addActionListener(e -> {
            supprimerEtudiant();
            mettreAJourStatistiques();
        });
        btnAfficherHistogramme.addActionListener(e -> afficherHistogramme());

        // Initialisation des données
        rafraichirTable();
        mettreAJourMoyenneClasse();
        mettreAJourStatistiques();
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }

    private void styleButton(JButton button, Color backgroundColor, Color textColor) {
        button.setFocusPainted(false);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }
    private void ajouterEtudiant() {
        try {
            Etudiant etudiant = new Etudiant();
            etudiant.setNumEt(txtNumEt.getText());
            etudiant.setNom(txtNom.getText());
            etudiant.setNoteMath(Double.parseDouble(txtNoteMath.getText()));
            etudiant.setNotePhys(Double.parseDouble(txtNotePhys.getText()));

            EtudiantClient.ajouterEtudiant(etudiant);
            JOptionPane.showMessageDialog(this, "Étudiant ajouté avec succès !");
            rafraichirTable();

            // Vider les champs
            txtNumEt.setText("");
            txtNom.setText("");
            txtNoteMath.setText("");
            txtNotePhys.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout !");
        }
    }

    private void rafraichirTable() {
        try {
            List<Etudiant> etudiants = EtudiantClient.getAllEtudiants();
            tableModel.setRowCount(0); // Vider la table

            for (Etudiant etudiant : etudiants) {
                double moyenne = (etudiant.getNoteMath() + etudiant.getNotePhys()) / 2.0;
                tableModel.addRow(new Object[]{etudiant.getId(),etudiant.getNumEt(), etudiant.getNom(), etudiant.getNoteMath(), etudiant.getNotePhys(), moyenne});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des étudiants !");
        }
    }

    private void modifierEtudiant() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant !");
            return;
        }

        Object idObj = tableModel.getValueAt(selectedRow, 0); // L'ID est dans la première colonne
        if (idObj == null) {
            JOptionPane.showMessageDialog(this, "Erreur : ID introuvable !");
            return;
        }
        try {
            Long id = Long.parseLong(idObj.toString());
            JTextField txtNumEtModif = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
            JTextField txtNomModif = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
            JTextField txtNoteMathModif = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
            JTextField txtNotePhysModif = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());

            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
            panel.add(new JLabel("Numéro Étudiant :"));
            panel.add(txtNumEtModif);
            panel.add(new JLabel("Nom :"));
            panel.add(txtNomModif);
            panel.add(new JLabel("Note Math :"));
            panel.add(txtNoteMathModif);
            panel.add(new JLabel("Note Physique :"));
            panel.add(txtNotePhysModif);

            int result = JOptionPane.showConfirmDialog(null, panel, "Modifier Étudiant",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                // Récupération des nouvelles valeurs
                String numEt = txtNumEtModif.getText().trim();
                String nom = txtNomModif.getText().trim();
                String noteMathStr = txtNoteMathModif.getText().trim();
                String notePhysStr = txtNotePhysModif.getText().trim();
                if (nom.isEmpty() || noteMathStr.isEmpty() || notePhysStr.isEmpty() || numEt.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer toutes les notes, le nom et le numéro étudiant !");
                    return;
                }
                double noteMath = Double.parseDouble(noteMathStr);
                double notePhys = Double.parseDouble(notePhysStr);

                Etudiant etudiant = new Etudiant();
                etudiant.setId(id);
                etudiant.setNumEt(numEt);
                etudiant.setNom(nom);
                etudiant.setNoteMath(noteMath);
                etudiant.setNotePhys(notePhys);

                boolean success = EtudiantClient.modifierEtudiant(id, etudiant);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Étudiant modifié avec succès !");
                    rafraichirTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la modification !");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erreur : Les notes doivent être des nombres valides !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification !");
        }
    }

    private void supprimerEtudiant() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant !");
            return;
        }
        Object valeurId = tableModel.getValueAt(selectedRow, 0);

        try {
            Long id = Long.parseLong(valeurId.toString());
            System.out.println("ID récupéré : " + id);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Voulez-vous vraiment supprimer cet étudiant ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    EtudiantClient.supprimerEtudiant(id);
                    JOptionPane.showMessageDialog(this, "Étudiant supprimé avec succès !");
                    rafraichirTable();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression !");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : ID invalide !");
        }
    }

    private void mettreAJourMoyenneClasse() {
        try {
            double moyenne = EtudiantClient.getMoyenneClasse();
            lblMoyenneClasse.setText("Moyenne de la classe : " + String.format("%.2f", moyenne));
        } catch (Exception e) {
            lblMoyenneClasse.setText("Moyenne de la classe : Erreur");
        }
    }

    private void mettreAJourStatistiques() {
        try {
            List<Etudiant> etudiants = EtudiantClient.getTousEtudiants();
            if (etudiants.isEmpty()) {
                lblMoyenneClasse.setText("Moyenne de la classe : 0.00");
                lblMoyenneMin.setText("Moyenne minimale : 0.00");
                lblMoyenneMax.setText("Moyenne maximale : 0.00");
                lblAdmis.setText("Étudiants admis : 0");
                lblRedoublants.setText("Étudiants redoublants : 0");
                return;
            }
            double sommeMoyenne = 0;
            moyenneMin = Double.MAX_VALUE;
            moyenneMax = Double.MIN_VALUE;
            int nbAdmis = 0, nbRedoublants = 0;

            for (Etudiant etudiant : etudiants) {
                double moyenne = etudiant.getMoyenne();
                sommeMoyenne += moyenne;
                if (moyenne < moyenneMin) moyenneMin = moyenne;
                if (moyenne > moyenneMax) moyenneMax = moyenne;
                if (moyenne >= 10) nbAdmis++;
                else nbRedoublants++;
            }
            moyenneClasse = sommeMoyenne / etudiants.size();
            lblMoyenneClasse.setText(String.format("Moyenne de la classe : %.2f", moyenneClasse));
            lblMoyenneMin.setText(String.format("Moyenne minimale : %.2f", moyenneMin));
            lblMoyenneMax.setText(String.format("Moyenne maximale : %.2f", moyenneMax));
            lblAdmis.setText("Étudiants admis : " + nbAdmis);
            lblRedoublants.setText("Étudiants redoublants : " + nbRedoublants);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du calcul des statistiques : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherHistogramme() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(moyenneMin, "Moyenne", "Min");
        dataset.addValue(moyenneMax, "Moyenne", "Max");
        dataset.addValue(moyenneClasse, "Moyenne", "Classe");

        JFreeChart barChart = ChartFactory.createBarChart(
                " Statistiques des Moyennes",
                "Catégorie",
                "Valeur",
                dataset
        );
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(230, 230, 250));
        plot.setOutlinePaint(Color.DARK_GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new GradientPaint(0, 0, new Color(255, 99, 132), 0, 0, new Color(255, 159, 64)));
        renderer.setSeriesPaint(1, new GradientPaint(0, 0, new Color(54, 162, 235), 0, 0, new Color(75, 192, 192)));
        renderer.setSeriesPaint(2, new GradientPaint(0, 0, new Color(153, 102, 255), 0, 0, new Color(201, 203, 207)));
        renderer.setShadowVisible(true);
        renderer.setShadowXOffset(2);
        renderer.setShadowYOffset(2);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlinePaint(1, Color.BLACK);
        renderer.setSeriesOutlinePaint(2, Color.BLACK);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Arial", Font.BOLD, 14));
        renderer.setDefaultItemLabelPaint(Color.BLACK);

        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.BOLD, 14));
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.BOLD, 14));
        barChart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));
        barChart.setBackgroundPaint(new Color(245, 245, 245));
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 550));

        JFrame frame = new JFrame("Histogramme des Moyennes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EtudiantGUI().setVisible(true));
    }
}
