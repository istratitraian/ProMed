package ro.duoline.promed.jpa;

import org.springframework.data.repository.CrudRepository;
import ro.duoline.promed.domains.User;
//import org.springframework.data.repository.PagingAndSortingRepository;

//public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByEmail(String email);

}
