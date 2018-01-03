package urlshortener.bluecrystal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.SystemRamUsage;

import java.util.List;

public interface SystemRamRepository extends JpaRepository<SystemRamUsage, Long> {
    List<SystemRamUsage> findByTimeBetween(Long start, Long end);

    @Override
    void delete(SystemRamUsage systemRamUsage);
}
