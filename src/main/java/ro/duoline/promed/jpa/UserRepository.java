package ro.duoline.promed.jpa;

import org.springframework.data.repository.CrudRepository;
import ro.duoline.promed.domains.User;
//import org.springframework.data.repository.PagingAndSortingRepository;

//public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByEmail(String email);
    
    //    public Authority findByAuthority(Pageable pageable,String authority);

    /*
      List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);

  // Enables the distinct flag for the query
  List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
  List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);

  // Enabling ignoring case for an individual property
  List<Person> findByLastnameIgnoreCase(String lastname);
  // Enabling ignoring case for all suitable properties
  List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);

  // Enabling static ORDER BY for a query
  List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
     */
}
