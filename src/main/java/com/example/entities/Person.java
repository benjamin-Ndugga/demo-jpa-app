package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "persons")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_code_seq")
    @GenericGenerator(
            name = "person_code_seq",
            strategy = "com.example.entities.StringSequenceIdentifier",
            parameters = {
                @Parameter(name = "sequence_name", value = "person_code_seq"),
                @Parameter(name = StringSequenceIdentifier.VALUE_PREFIX_PARAMETER, value = "PER_")
            })
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    public Person() {
    }

    public Person(String moduleName, String moduleDescription) {
        this.name = moduleName;
        this.location = moduleDescription;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
