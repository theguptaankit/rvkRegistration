package net.vms.springboot.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "volunteer_summary")
public class VolunteerSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String departments;
    private long expected_male;
    private long expected_female;
    private long total_expected;
    private long arrived_male;
    private long arrived_female;
    private long total_registered;
}
