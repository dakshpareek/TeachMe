package com.teachme.teachme.repository;


import com.teachme.teachme.entity.Course;
import com.teachme.teachme.entity.CourseResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseResponseRepository extends JpaRepository<CourseResponse,Long> {

    List<CourseResponse> findAllByUserId(long id);

    List<CourseResponse> findAllByCourseId(long id);

    List<CourseResponse> findAllByCourseIdAndStatus(long id,boolean status);

    Optional<CourseResponse> findByIdAndUserId(long id, long id1);

    Optional<CourseResponse> findByIdAndCourseId(long id, long id1);

    void deleteById(long id);
}
