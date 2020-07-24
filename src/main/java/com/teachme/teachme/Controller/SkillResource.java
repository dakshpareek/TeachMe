package com.teachme.teachme.Controller;

import com.teachme.teachme.DTO.SkillDTO;
import com.teachme.teachme.DTO.UpdateSkillDTO;
import com.teachme.teachme.Entity.Skill;
import com.teachme.teachme.Service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SkillResource {

    @Autowired
    SkillService skillService;

    @GetMapping( "/api/skills" )
    public ResponseEntity<List<Skill>> getSkills(){

        return skillService.getAllSkills();
    }

    @GetMapping( "/api/skills/{skill_id}" )
    public ResponseEntity<Skill> getParticularSkill( @PathVariable int skill_id ){

        return skillService.getparticularskill( skill_id );
    }

    @PostMapping( "/api/skills" )
    public ResponseEntity<String> addNewSkill(@RequestBody SkillDTO skillDTO ){

        return skillService.addSkill( skillDTO );
    }

    @PatchMapping( "/api/skills/deleteStatus/{skill_id}" )
    public ResponseEntity<String> changeDeleteStatus( @PathVariable int skill_id ){

        return skillService.changedeletestatus( skill_id );
    }

    @PatchMapping( "/api/skills/verificationstatus/{skill_id}" )
    public ResponseEntity<String> changeVerificationStatus( @PathVariable int skill_id ){

        return skillService.changeverificationstatus( skill_id );
    }


    @PutMapping( "/api/skills/{skill_id}" )
    public ResponseEntity<String> updateSkillDetails( @PathVariable int skill_id, @RequestBody UpdateSkillDTO skilldetails ){

        return skillService.updateskilldetails( skill_id, skilldetails );
    }
}
