package org.algohub.rest.repository;

import org.algohub.rest.domain.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//@PreAuthorize("hasRole('USER')")
@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, String> {

}
