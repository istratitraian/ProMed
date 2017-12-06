/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.jpa;

import org.springframework.data.repository.CrudRepository;
import ro.duoline.promed.domains.Picture;

/**
 *
 * @author I.T.W764
 */
public interface PictureRepository extends CrudRepository<Picture, Integer> {

    public Picture findByUserId(Integer id);

}
