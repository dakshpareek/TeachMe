package com.teachme.teachme.service;

import com.teachme.teachme.DTO.SkillDTO;
import com.teachme.teachme.DTO.UpdateSkillDTO;
import com.teachme.teachme.entity.Authority;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.AuthorityRepository;
import com.teachme.teachme.repository.SkillRepository;
import com.teachme.teachme.entity.Skill;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillService {


    private SkillRepository skillRepository;
    private UserDao userRepository;
    private AuthorityRepository authorityRepository;

    public SkillService(SkillRepository skillRepository,
                        UserDao userRepository,AuthorityRepository authorityRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

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

    public Map<String, Object> addSkill(SkillDTO skillDTO){

        Optional<Skill> skillOptional = skillRepository.findByName( skillDTO.getName() );

        if( skillOptional.isPresent() ){

            throw new CustomException("Skill Exists",HttpStatus.BAD_REQUEST,"/skills");
        }

        Skill skill = new Skill( skillDTO.getName());
        skill.setVerificationstatus(false);
        skillRepository.save(skill);

        //now if user is not admin then save it to user also
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);
        Set<Authority> authorities = user.getAuthorities();

        Authority authority = authorityRepository.findByName("ROLE_ADMIN").get();

        if(authorities.contains(authority) == false)
        {
            Set<Skill> skillList = user.getSkills();
            skillList.add(skill);
            user.setSkills(skillList);
            userRepository.save(user);
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Skill Added");
        body.put("status",200);
        body.put("path","/");
        return body;

        //return new ResponseEntity<String>( "Skill added and under verification", HttpStatus.OK );

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
