package com.revature.PPP.models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "diseases")
@Component
public class Disease {
    @Id // This annotations makes this field the PK (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //The PK will now auto-increment
    private int diseaseId;

    @Column(nullable = false, unique = true)
    private String diseaseName;
    private String severity;

    // Multiple diseases can be associated with the same gene
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "geneId")
    private Gene gene;

 //   @Autowired
 //   public Disease(Gene gene){
 //   }

    public Disease() {
    }

    public Disease(int diseaseId, String diseaseName, String severity) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.severity = severity;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    @Override
    public String toString() {
        return "Disease{" +
                "diseaseId=" + diseaseId +
                ", diseaseName='" + diseaseName + '\'' +
                ", severity='" + severity + '\'' +
                '}';
    }
}
