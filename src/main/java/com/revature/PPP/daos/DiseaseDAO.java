package com.revature.PPP.daos;

import com.revature.PPP.models.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DiseaseDAO extends JpaRepository<Disease, Integer> {

    //Spring Data IS smart enough to implement this method for us. We just need to define it.
    //NOTE: The method MUST BE NAMED "findByXYZ", otherwise it won't work as intended.
    //public Disease findByDiseaseName(String diseaseName);
    public List<Disease> findByDiseaseName(String diseaseName);
    //find all diseases by gene id
    //public Disease findByGeneGeneId(int geneId);
    public List<Disease> findByGeneGeneId(int id);
   // public Set<Disease> findByGeneGeneId(int id);
}
