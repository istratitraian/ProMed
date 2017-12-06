/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.duoline.promed.jpa.PictureRepository;

/**
 *
 * @author I.T.W764
 */
@Controller
public class PictureController {

    @Autowired
    private PictureRepository pictureRepository;

    // GIF image data
    @RequestMapping("/image/{imgId}")
    @ResponseBody
    public byte[] gifImage(@PathVariable Integer imgId) {
        return pictureRepository.findOne(imgId).getImage();
    }
    @RequestMapping("/imageByUser/{userId}")
    @ResponseBody
    public byte[] getImageByUser(@PathVariable Integer userId) {
        return pictureRepository.findByUserId(userId).getImage();
    }

}
