package net.vms.springboot.repository;

import net.vms.springboot.model.VolunteerSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerSummaryRepo extends JpaRepository<VolunteerSummary, Long> {
}
