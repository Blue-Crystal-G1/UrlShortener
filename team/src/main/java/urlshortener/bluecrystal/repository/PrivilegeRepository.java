package urlshortener.bluecrystal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.domain.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
