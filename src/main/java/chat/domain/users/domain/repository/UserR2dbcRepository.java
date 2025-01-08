package chat.domain.users.domain.repository;

import org.apache.catalina.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {

}
