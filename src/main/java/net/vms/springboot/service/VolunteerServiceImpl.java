package net.vms.springboot.service;

import net.vms.springboot.model.Departments;
import net.vms.springboot.model.OverallSummary;
import net.vms.springboot.model.Volunteer;
import net.vms.springboot.model.VolunteerSummary;
import net.vms.springboot.repository.DepartmentRepo;
import net.vms.springboot.repository.OverallSummaryRepo;
import net.vms.springboot.repository.VolunteerRepository;
import net.vms.springboot.repository.VolunteerSummaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService {

    private static final List<String> STATUS = Arrays.asList("Arrived","Up/Down");
    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerSummaryRepo volunteerSummaryRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private OverallSummaryRepo overallSummaryRepo;

    @Override
    public List<Volunteer> getAllVolunteers() {
        return (List<Volunteer>) volunteerRepository.findAll();
    }

    @Override
    public void saveVolunteer(Volunteer volunteer) {
        this.volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer getVolunteerBySCId(long id) {
        Optional<Volunteer> optional = volunteerRepository.findById(id);
        Volunteer volunteer = null;
        if (optional.isPresent()) {
            volunteer = optional.get();
        } else {
            throw new RuntimeException("Volunteer Not Found for Smart Card: " + id);
        }
        return volunteer;
    }

    @Override
    public void deleteVolunteerBySCId(long id) {
        this.volunteerRepository.deleteById(id);
    }

    @Override
    public Page<Volunteer> findPaginated(int pageNo, int pageSize, String sortField, String sortSeq) {
        Sort sort = sortSeq.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.volunteerRepository.findAll(pageable);
    }

    public Page<Volunteer> findPaginated2(String keyword, int pageNo, int pageSize, String sortField, String sortSeq) {
        Sort sort = sortSeq.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return volunteerRepository.findByKeyword(keyword, pageable);
    }


    public List<VolunteerSummary> getSummary(){
        return volunteerSummaryRepo.findAll();
    }

    public List<OverallSummary> getOverallSummary(){
        return overallSummaryRepo.findAll();
    }

    public List<Departments> getDepartments() {return departmentRepo.findAll();}
}
