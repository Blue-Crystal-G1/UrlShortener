package urlshortener.bluecrystal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
