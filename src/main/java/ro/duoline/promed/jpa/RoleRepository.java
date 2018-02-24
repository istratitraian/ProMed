package ro.duoline.promed.jpa;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import ro.duoline.promed.domains.security.Authority;


public interface RoleRepository extends CrudRepository<Authority, Integer> {

    @Cacheable("authority")
    public Authority findByAuthority(String authority);

//    public Authority findByAuthority(Pageable pageable,String authority);
}