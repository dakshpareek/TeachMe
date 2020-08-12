package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CourseContract extends ContractDetails{

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "student_id", nullable = false )
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser student;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "teacher_id", nullable = false )
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser teacher;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "course_id", nullable = false )
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Course course;
}
