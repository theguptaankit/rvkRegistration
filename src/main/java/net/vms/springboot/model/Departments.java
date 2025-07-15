package net.vms.springboot.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "departments")
public class Departments {
    private String area;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
