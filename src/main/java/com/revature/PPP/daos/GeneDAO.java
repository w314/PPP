package com.revature.PPP.daos;

import com.revature.PPP.models.Disease;
import com.revature.PPP.models.Gene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneDAO extends JpaRepository<Gene, Integer> {
    //Spring Data IS smart enough to implement this method for us. We just need to define it.
    //NOTE: The method MUST BE NAMED "findByXYZ", otherwise it won't work as intended.
    public Gene findByGeneName(String geneName);
    public Gene findByGeneId(int geneId);

}
