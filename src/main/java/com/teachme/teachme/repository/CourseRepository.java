package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> ,CourseRepositoryCustom{
    @Override
    Optional<Course> findById(Long aLong);

    List<Course> findAllByUserId(Long userId);

    Optional<Course> findByIdAndUserId(long id, long id1);
}
