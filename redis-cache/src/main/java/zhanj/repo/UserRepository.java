package zhanj.repo;

import org.springframework.data.repository.CrudRepository;
import zhanj.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
