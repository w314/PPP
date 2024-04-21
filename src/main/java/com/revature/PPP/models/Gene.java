package com.revature.PPP.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

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

    public Gene() {
    }

    public Gene(int geneId, String geneName, String chromosome) {
        this.geneId = geneId;
        this.geneName = geneName;
        this.chromosome = chromosome;
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

    @Override
    public String toString() {
        return "Gene{" +
                "geneId=" + geneId +
                ", chromosome='" + chromosome + '\'' +
                '}';
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }
}
