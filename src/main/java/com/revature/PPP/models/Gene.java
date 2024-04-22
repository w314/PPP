package com.revature.PPP.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Table(name="genes")
public class Gene {

    @Id //This makes the field the PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //This makes the PK auto-increment
    private int geneId;
    private String geneName;
    @Column(nullable = false)
    private String chromosome;

    // the one side of the one to many relationship between gene and their diseases
    // mapped by the gene field in Class Disease
    // cascade and orphanRemoval make sure if we delete a gene all their diseases are deleted too
    @OneToMany(mappedBy = "gene", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonBackReference is needed to avoid infinite loops when converting objects to JSON
    // this setup will not show the list of diseases a gene has when printing out the gene
    // but will show the gene associated with the disease when we print out a disease with using @JsonManagedReference on that side
//    @JsonBackReference
    @JsonManagedReference
    private List<Disease> diseases;

    public Gene() {
    }

    public Gene(int geneId, String geneName, String chromosome, List<Disease> diseases) {
        this.geneId = geneId;
        this.geneName = geneName;
        this.chromosome = chromosome;
        this.diseases = diseases;
    }

    public int getGeneId() {
        return geneId;
    }

    public void setGeneId(int geneId) {
        this.geneId = geneId;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "geneId=" + geneId +
                ", geneName='" + geneName + '\'' +
                ", chromosome='" + chromosome + '\'' +
                ", diseases=" + diseases +
                '}';
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }
}
