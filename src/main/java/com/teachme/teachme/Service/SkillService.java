package com.teachme.teachme.Service;

import com.teachme.teachme.DTO.SkillDTO;
import com.teachme.teachme.DTO.UpdateSkillDTO;
import com.teachme.teachme.Entity.Skill;
import com.teachme.teachme.Repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    public ResponseEntity<List<Skill>> getAllSkills(){

        return new ResponseEntity<List<Skill>>(skillRepository.findAllByVerificationAAndIsdeleted( true, false ), HttpStatus.OK);

    }

    public ResponseEntity<Skill> getparticularskill( int skill_id ){

        Optional< Skill > skillOptional = skillRepository.findById( skill_id );

        /*
        if( skillOptional.isEmpty() ){

            return new ResponseEntity<String>( "Skill does not exsist", HttpStatus.BAD_REQUEST );
        }*/

        return new ResponseEntity<Skill>( skillOptional.get(), HttpStatus.OK );
    }

    public ResponseEntity< String > addSkill(SkillDTO skillDTO){

        Optional<Skill> skillOptional = skillRepository.findByName( skillDTO.getName() );

        if( skillOptional.isPresent() ){

            return new ResponseEntity<String>( "Skill already exsists", HttpStatus.CONFLICT );
        }
        else{

            skillRepository.save( new Skill( skillDTO.getName() ) );
            return new ResponseEntity<String>( "Skill added and under verification", HttpStatus.OK );
        }
    }


    public ResponseEntity<String> changedeletestatus( int skill_id ){

        Optional< Skill > skillOptional = skillRepository.findById( skill_id );

        if( skillOptional.isPresent() ){

            Skill skill = skillOptional.get();
            boolean currentstatus = skill.isIsdeleted();
            skill.setIsdeleted( !currentstatus );
            skillRepository.save( skill );
            return new ResponseEntity<String>( "Skill delete status changed successfully", HttpStatus.OK );
        }
        else{

            return new ResponseEntity<String>( "Skill does not exsist", HttpStatus.BAD_REQUEST );
        }
    }

    public ResponseEntity<String> changeverificationstatus( int skill_id ){

        Optional< Skill > skillOptional = skillRepository.findById( skill_id );

        if( skillOptional.isEmpty() ){

            return new ResponseEntity<String>( "Skill does not exsist", HttpStatus.BAD_REQUEST );
        }

        Skill skill = skillOptional.get();
        boolean currentstatus = skill.isVerificationstatus();
        skill.setVerificationstatus( !currentstatus );
        skillRepository.save( skill );
        return new ResponseEntity<String>( "Verification status changed successfully", HttpStatus.OK );
    }


    public ResponseEntity<String> updateskilldetails( int skill_id, UpdateSkillDTO skilldetails ){

        Optional< Skill > skillOptional = skillRepository.findById( skill_id );

        if( skillOptional.isEmpty() ){

            return new ResponseEntity<String>( "Skill does not exsist", HttpStatus.BAD_REQUEST );
        }

        Skill skill = skillOptional.get();

        if( skilldetails.getName() != null ){

            skill.setName( skilldetails.getName() );
        }

        if( skilldetails.getDescription() != null ){

            skill.setDescription( skilldetails.getDescription() );
        }

        skillRepository.save( skill );
        return new ResponseEntity<String>( "Skill details updated successfully", HttpStatus.OK );
    }




}
