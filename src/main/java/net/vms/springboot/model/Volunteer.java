package net.vms.springboot.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "volunteers")
@Entity
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String volunteerId;

    private String smartCardId;
    private String name;
    private String gender;
    private String contactNumber;
    private String area;
    private String bed;
    private String email_id;
    private String remarks;
    private String arrival_status;
    private Date arrival_ts;
    private Date departure_ts;
}
