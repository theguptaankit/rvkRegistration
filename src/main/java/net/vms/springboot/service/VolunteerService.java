package net.vms.springboot.service;

import net.vms.springboot.model.Departments;
import net.vms.springboot.model.OverallSummary;
import net.vms.springboot.model.Volunteer;
import net.vms.springboot.model.VolunteerSummary;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VolunteerService {
    List<Volunteer> getAllVolunteers();

    void saveVolunteer(Volunteer volunteer);

    Volunteer getVolunteerBySCId(long Id);

    void deleteVolunteerBySCId(long Id);

    Page<Volunteer> findPaginated(int pageNo, int pageSize, String sortField, String query);

   Page<Volunteer> findPaginated2(String keyword, int pageNo, int pageSize, String sortField, String sortSeq);

    List<VolunteerSummary> getSummary();

    List<OverallSummary> getOverallSummary();

    List<Departments> getDepartments();

}
