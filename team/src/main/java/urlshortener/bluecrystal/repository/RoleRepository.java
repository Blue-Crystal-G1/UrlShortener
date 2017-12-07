package urlshortener.bluecrystal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
