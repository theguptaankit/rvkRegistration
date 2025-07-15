package net.vms.springboot.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "overall_summary")
public class OverallSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String gender;
    private long totalExpected_Volunteers;
    private long totalRegistered_Volunteers;
    private long volunteer_stay;
    private long volunteer_ud;
}
