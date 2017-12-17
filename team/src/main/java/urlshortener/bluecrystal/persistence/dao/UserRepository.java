package urlshortener.bluecrystal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

}
