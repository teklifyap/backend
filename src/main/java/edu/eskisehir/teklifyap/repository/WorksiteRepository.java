package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Worksite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorksiteRepository extends JpaRepository<Worksite,Long> {
}
