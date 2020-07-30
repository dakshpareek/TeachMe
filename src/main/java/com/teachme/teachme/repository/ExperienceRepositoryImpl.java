package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Experience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
public class ExperienceRepositoryImpl implements ExperienceRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteExperienceById(long id) {
        log.info("In deleteExperienceById");
        // Retrieve the experience with this ID
        Experience experience = entityManager.find(Experience.class, id);
        if (experience != null) {
            try {
                // Start a transaction because we're going to change the database
                //entityManager.getTransaction().begin();

                // Remove all references to this experience from skills
                experience.getSkills().forEach(skill -> {
                    skill.getExperienceSet().remove(experience);
                });

                // Now remove the experience
                entityManager.remove(experience);

                // Commit the transaction
                //entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
