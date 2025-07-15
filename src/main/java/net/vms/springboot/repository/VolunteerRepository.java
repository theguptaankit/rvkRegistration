package net.vms.springboot.repository;

import net.vms.springboot.model.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends PagingAndSortingRepository<Volunteer, Long> {

    @Query(value = "select * from volunteers v where v.smart_card_id ilike %:keyword% or v.name ilike %:keyword% or v.department ilike %:keyword% or v.volunteer_id ilike %:keyword%", nativeQuery = true)
    Page<Volunteer> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
