package net.vms.springboot.repository;

import net.vms.springboot.model.OverallSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverallSummaryRepo extends JpaRepository<OverallSummary, Long> {

}