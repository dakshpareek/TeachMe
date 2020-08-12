package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Course;
import com.teachme.teachme.entity.CourseContract;
import com.teachme.teachme.entity.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseContractRepository extends JpaRepository<CourseContract, Long> {

    Optional<CourseContract> findByStudentAndCourse( DAOUser student, Course course );

    List<CourseContract> findAllByStudent( DAOUser student );

    List<CourseContract> findAllByTeacher( DAOUser teacher );
}
