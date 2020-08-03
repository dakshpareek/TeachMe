package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Experience;
import com.teachme.teachme.entity.Skill;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
public class SkillRepositoryImpl implements SkillRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteSkillById(long id) {
        log.info("In deleteSkillById");
        // Retrieve the experience with this ID
        Skill skill = entityManager.find(Skill.class, id);
        if (skill != null) {
            try {
                // Start a transaction because we're going to change the database
                //entityManager.getTransaction().begin();

                // Remove all references to this experience from skills
                skill.getExperienceSet().forEach(experience -> {
                    experience.getSkills().remove(skill);
                });

                //remove reference from Daouser
                skill.getDaoUserSet().forEach(user -> {
                    user.getSkills().remove(skill);
                });

                //remove reference from Course
                skill.getCourseSet().forEach(course -> {
                    course.getSkills().remove(skill);
                });

                // Now remove the skill
                entityManager.remove(skill);

                // Commit the transaction
                //entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
