package urlshortener.bluecrystal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.SystemCpuUsage;

import java.util.List;

public interface SystemCpuRepository extends JpaRepository<SystemCpuUsage, Long> {
    List<SystemCpuUsage> findByTimeBetween(Long start, Long end);

    @Override
    void delete(SystemCpuUsage systemCpuUsage);
}
