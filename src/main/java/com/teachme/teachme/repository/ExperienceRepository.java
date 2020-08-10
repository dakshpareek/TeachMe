package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long>, com.teachme.teachme.repository.ExperienceRepositoryCustom {



    Optional<Experience> findByUserId(Long DaoUserId);

    List<Experience> findAllByUserId(Long DaoUserId);

    Optional<Experience> findByIdAndUserId(Long id, Long DaoUserId);

    Optional<Experience> findById(long id);

    void deleteById(long id);
}
