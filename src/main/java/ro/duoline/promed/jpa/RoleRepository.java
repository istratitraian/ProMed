package ro.duoline.promed.jpa;

import ro.duoline.promed.domains.security.Authority;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Authority, Integer> {

    public Authority findByAuthority(String authority);
}