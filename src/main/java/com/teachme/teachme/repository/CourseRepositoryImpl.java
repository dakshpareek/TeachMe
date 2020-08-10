package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Course;
import com.teachme.teachme.entity.Experience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
public class CourseRepositoryImpl implements CourseRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteCourseById(long id) {
        log.info("In deleteCourseById");

        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            try {
                // Remove all references from skills
                course.getSkills().forEach(skill -> {
                    skill.getExperienceSet().remove(course);
                });

                // Now remove the experience
                entityManager.remove(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("Exiting deleteCourseById");
    }
}
