package com.rmorpoz2909.aad;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class Student {
    Integer id;
    String firstName;
    String lastName;
    Date birthDate;
    Double averageGrade;

}
