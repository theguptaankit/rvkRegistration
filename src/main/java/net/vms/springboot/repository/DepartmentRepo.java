package net.vms.springboot.repository;

import net.vms.springboot.model.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Departments, Long> {
}
