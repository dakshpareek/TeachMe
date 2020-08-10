package com.teachme.teachme.repository;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Experience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
public class UserDaoRepositoryCustomImpl implements UserDaoRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteByUserId(long id) {
        log.info("In deleteByUserId");
        // Retrieve the experience with this ID
        DAOUser user = entityManager.find(DAOUser.class, id);
        if (user != null) {
            try {
                // Start a transaction because we're going to change the database
                //entityManager.getTransaction().begin();

                // Remove all references to this experience from skills
                user.getSkills().forEach(skill -> {
                    skill.getDaoUserSet().remove(user);
                });

                // Now remove the experience
                entityManager.remove(user);

                // Commit the transaction
                //entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("deleteByUserId completed");
        }
    }
}
