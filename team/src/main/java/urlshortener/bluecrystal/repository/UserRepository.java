package urlshortener.bluecrystal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

}
