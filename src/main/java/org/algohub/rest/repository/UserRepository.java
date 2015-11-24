package org.algohub.rest.repository;

import org.algohub.rest.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


//@PreAuthorize("hasRole('USER')")
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  User findOneByEmail(String email);
  User findOneByUsername(String username);
}
