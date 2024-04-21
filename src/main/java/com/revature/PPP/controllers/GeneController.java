package com.revature.PPP.controllers;

import com.revature.PPP.daos.DiseaseDAO;
import com.revature.PPP.daos.GeneDAO;
import com.revature.PPP.models.Disease;
import com.revature.PPP.models.Gene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genes")
public class GeneController {

    private GeneDAO geneDAO;

    @Autowired
    public GeneController( GeneDAO geneDAO) {
        this.geneDAO = geneDAO;
    }

    @GetMapping
    public ResponseEntity<List<Gene>> getAllGenes(){

        List<Gene> genes = geneDAO.findAll();
        return ResponseEntity.ok(genes);
    }

    @GetMapping("/{geneName}")
    public ResponseEntity<Gene> getGeneByGeneName(@PathVariable String geneName){

        Gene g = geneDAO.findByGeneName(geneName);

        if(g == null){
            //return a ResponseEntity with status 404 (not found)
            return ResponseEntity.notFound().build();
        }

        //return a ResponseEntity with status 200 (ok), and the user in the body
        return ResponseEntity.ok(g);

    }

    @GetMapping("/{geneId}")
    public ResponseEntity<Gene> getGeneByGeneId(@PathVariable int geneId){

        Gene g = geneDAO.findByGeneId(geneId);

        if(g == null){
            //return a ResponseEntity with status 404 (not found)
            return ResponseEntity.notFound().build();
        }

        //return a ResponseEntity with status 200 (ok), and the user in the body
        return ResponseEntity.ok(g);

    }

    @PostMapping
    public ResponseEntity<Gene> insertGene(@RequestBody Gene gene){
        Gene g = geneDAO.save(gene);
        if(g == null){
            //this will a response with status 500, and NO RESPONSE BODY (which is what build() does)
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(201).body(g);
    }

//    // Update a  Gene
//    @PutMapping("/{geneId}")
//    public ResponseEntity<Gene> updateGene(@RequestBody Gene gene, @PathVariable int geneId){
//
//        Gene g = geneDAO.findById(geneId).orElseThrow(() -> new IllegalArgumentException("Gene not found for ID: " + geneId)); // Throw an exception
//        g.setGeneId(gene.getGeneId());
//        g.setChromosome(gene.getChromosome());
//        g.setGeneName(gene.getGeneName());
//
//       geneDAO.save(g); //updates and inserts use the SAME JPA METHOD, save()
//
//        //if we send a Disease with no PK, it knows to create a new Disease
//        //if we send a Disease with an EXISTING PK, it knows to update the existing Disease
//
//        return ResponseEntity.ok(g);
//
//    }

}
