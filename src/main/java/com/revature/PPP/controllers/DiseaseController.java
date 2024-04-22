package com.revature.PPP.controllers;

import com.revature.PPP.daos.DiseaseDAO;
import com.revature.PPP.daos.GeneDAO;
import com.revature.PPP.models.Disease;
import com.revature.PPP.models.Gene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;


@RestController
@RequestMapping("/diseases")
public class DiseaseController {
    private DiseaseDAO diseaseDAO;
    private GeneDAO geneDAO;

    @Autowired
    public DiseaseController(DiseaseDAO diseaseDAO,GeneDAO geneDAO) {
        this.geneDAO = geneDAO;
        this.diseaseDAO = diseaseDAO;
    }

    @GetMapping
    public ResponseEntity<List<Disease>> getAllDisease(){

        List<Disease> diseases = diseaseDAO.findAll();
        return ResponseEntity.ok(diseases);
    }


    @GetMapping("/{diseaseId}")
    public ResponseEntity<Object> getDiseasesById(@PathVariable int diseaseId){

        Optional<Disease> oD = diseaseDAO.findById(diseaseId);

        if(oD.isEmpty()){
            //return a ResponseEntity with status 404 (not found)
            return ResponseEntity.status(404).body("No Disease record found for ID: " + diseaseId);
        }

        Disease d = oD.get();
        //return a ResponseEntity with status 200 (ok), and the user in the body
        return ResponseEntity.ok(d);

    }

    @GetMapping("/genes/{geneId}")
    public ResponseEntity<List<Disease>> getAllDiseasesByGeneId(@PathVariable int geneId){

        List<Disease> diseases = diseaseDAO.findByGeneGeneId(geneId);
        //Disease ds = diseaseDAO.findByGeneGeneId(geneId);

        if (diseases.isEmpty()) {
            // Return a ResponseEntity with status 404 (Not Found) and an error message
            return ResponseEntity.notFound().build();
           // return ResponseEntity.status(404).body("No Disease record found for gene ID: " + geneId);
        }

        //return a ResponseEntity with status 200 (ok), and the user in the body
        return ResponseEntity.ok(diseases);
    }

    @PostMapping("/{geneId}")
    public ResponseEntity<Object> insertDisease(@RequestBody Disease disease, @PathVariable int geneId){

        // get the gene by ID, to attach to the Disease.
        // To prevent a null pointer exception when using Optional
        Optional<Gene> oG= geneDAO.findById(geneId);
        // Gene g = geneDAO.findById(geneId).get();
        // Alternatively
        // Gene g = geneDAO.findById(geneId).orElse(null); // Provide a default value (null in this case)

        if(oG.isEmpty()) {
            return ResponseEntity.badRequest().body("Please enter a Disease with  a valid gene ID!");
        }

        // Check if the disease with the same gene already exists
        List<Disease> existingDisease = diseaseDAO.findByGeneGeneId(geneId);

        if (!existingDisease.isEmpty()) {
            return ResponseEntity.badRequest().body(existingDisease + " with the Gene ID " + geneId + "already exists!");
        }
        Gene g =oG.get();
        disease.setGene(g);
        Disease d = diseaseDAO.save(disease);

        //return ResponseEntity.status(201).body(d);
        return ResponseEntity.status(HttpStatus.CREATED).body(d);
    }

    // Replace an existing Disease
    @PutMapping("/{geneId}")
    public ResponseEntity<Object> updateDisease(@RequestBody Disease disease, @PathVariable int geneId){

        Optional<Gene> oG= geneDAO.findById(geneId);
        // Gene g = geneDAO.findById(geneId).get();
        // Alternatively
        // Gene g = geneDAO.findById(geneId).orElse(null); // Provide a default value (null in this case)

        if(oG.isEmpty()) {
            return ResponseEntity.badRequest().body("Gene not found for ID: " + geneId);
        }

        Gene g = oG.get();
        disease.setGene(g);
        // Check if the disease with the same gene already exists
        List<Disease> existingDiseases = diseaseDAO.findByGeneGeneId(geneId);
        for (Disease existingDisease : existingDiseases) {
            // Check if the disease to be updated already exists
            if (existingDisease.getDiseaseName().equals(disease.getDiseaseName())) {
                return ResponseEntity.badRequest().body(disease.getDiseaseName() +" with gene ID: " + geneId + " already exists!");
            }
        }

        Disease d = diseaseDAO.save(disease); //updates and inserts use the SAME JPA METHOD, save()

        //if we send a Disease with no PK, it knows to create a new Disease
        //if we send a Disease with an EXISTING PK, it knows to update the existing Disease

        return ResponseEntity.ok(d);

    }
//
//    // updates the disease (with the disease id in the URI) with the new values provided
//    @PutMapping("/{diseaseId}")
//    public ResponseEntity<Object> updateADisease(@PathVariable int diseaseId, @RequestBody Disease disease) {
//
//        // check if disease ID is valid
//        Optional<Disease> diseaseOptional = diseaseDAO.findById(diseaseId);
//
//        // if there is no disease with the provided id return bad not found error
//        if(diseaseOptional.isEmpty()) {
//            return ResponseEntity.status(404).body("There is no disease with ID: " + diseaseId);
//        }
//
//        // if we have the disease get it from the Optional container
//        Disease d =  (Disease) diseaseOptional.get();
//
//        // update old values with the new ones
//        d.setDiseaseName(disease.getDiseaseName());
//        d.setSeverity(disease.getSeverity());
//        d.setGene(disease.getGene());
//
//        // save our updated disease to the DB
//        Disease updatedDisease = diseaseDAO.save(disease);
//
//        // send response with the updated disease in the body
//        return ResponseEntity.ok().body(updatedDisease);
//    }


    // Delete a Disease by its ID
    @DeleteMapping("/{diseaseId}")
    public ResponseEntity<Object> deleteDisease(@PathVariable int diseaseId){

        Optional<Disease> d = diseaseDAO.findById(diseaseId);

        if(d.isEmpty()){
            return ResponseEntity.status(404).body("No Disease found with ID of: " + diseaseId);
        }

        Disease disease = d.get();

        // Delete!
        diseaseDAO.deleteById(diseaseId);

        return ResponseEntity.accepted().body("Disease " + disease.getDiseaseName() + " with "+ "Disease ID "+ disease.getDiseaseId() + " has been deleted!");

    }

    @GetMapping("/name/{diseaseName}")
    public ResponseEntity<Object> getDiseaseByDiseaseName(@PathVariable String diseaseName){

        List<Disease> ds = diseaseDAO.findByDiseaseName(diseaseName);

        if(ds.isEmpty()){
            //return a ResponseEntity with status 404 (not found)
            //return ResponseEntity.notFound().build();
            return ResponseEntity.badRequest().body("There is no disease record for disease " + diseaseName);
        }
        //return a ResponseEntity with status 200 (ok), and the user in the body
        return ResponseEntity.ok(ds);
    }

//    // Update ONLY the name of a Disease
//    @PatchMapping("/{diseaseId}")
//    public ResponseEntity<Object> updateDiseaseName(@RequestBody String name, @PathVariable int diseaseId){
//
//        //get the game by Id, set the new title (setter), save it back to the DB
//        // We'll use Optionals as they were meant to be used - to prevent NullPointerExceptions
//        Optional<Disease> d = diseaseDAO.findById(diseaseId);
//
//        //if the Optional is empty, send an error message, otherwise send the updated game
//        if(d.isEmpty()){
//            //This will return a String message telling the user what went wrong
//            //Note the method's return type needs to be ResponseEntity<Optional> for this to work
//            return ResponseEntity.status(404).body("No disease found with ID of: " + diseaseId);
//        }
//
//        //extract the Disease from the Optional
//        Disease disease = d.get();
//
//        //update the name, using the setter
//        disease.setDiseaseName(name);
//
//        //finally, save the updated game back to the DB
//        diseaseDAO.save(disease);
//
//        return ResponseEntity.accepted().body(disease);
//
//    }

}

//user=gene
//item=disease
//Select all genes from the disease table
//Select one disease by its ID
//Select all disease that belong to a certain gene (find by user id FK)
    //This may be tricky, look at GameDAO for a hint.
//Insert a new item into the items table
//Insert a new gene into the genes table
//Update a gene (instead Update diseases table with new genes found recent research)
//Delete an disease
//[Some other functionality of your choice]
