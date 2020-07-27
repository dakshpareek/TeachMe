package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository< Skill, Integer > ,SkillRepositoryCustom{

    @Query( "select s from Skill s where s.verificationstatus = :vstatus and s.isdeleted = :dstatus" )
    List<Skill> findAllByVerificationAAndIsdeleted( boolean vstatus, boolean dstatus  );

    Optional<Skill> findByName( String name );

    Optional<Skill> findById(int id);
    /*
    @Query("SELECT s.verificationstatus FROM Skill s where s.skill_id = :id")
    boolean findVerificationstatusById( int id );

    @Transactional
    @Modifying
    @Query( nativeQuery = true , value = "UPDATE Skill SET isdeleted = true WHERE Skill.skill_id = :id" )
    void softDeleteById( int id );


    @Query( nativeQuery = true , value = "UPDATE Skill SET verificationstatus = :status WHERE Skill.skill_id = :id" )
    void updateverificationstatus( int id, boolean status );
    */

}
