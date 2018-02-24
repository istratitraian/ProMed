/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed;

import java.util.Random;
import javax.cache.annotation.CacheKey;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 *
 * @author I.T.W764
 */
@Component
public class CacheExample {

//    @CacheResult(cacheName = "cacheExample")
    @Cacheable("cacheExample")
    public Integer getCahce(@CacheKey String key) {
        int res = new Random().nextInt(100);
        System.out.println("getCahce() res = " + res);
        return res;
    }
}
