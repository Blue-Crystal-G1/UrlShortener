package urlshortener.bluecrystal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
