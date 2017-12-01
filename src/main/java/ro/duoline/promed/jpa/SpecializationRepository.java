/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.jpa;

import org.springframework.data.repository.CrudRepository;
import ro.duoline.promed.domains.Specialization;

/**
 *
 * @author I.T.W764
 */
public interface SpecializationRepository extends CrudRepository<Specialization, Integer> {
    
}
