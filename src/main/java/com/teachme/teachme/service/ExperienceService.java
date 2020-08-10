package com.teachme.teachme.service;

import com.teachme.teachme.dto.ExperienceDTO;
import com.teachme.teachme.dto.ExperienceUpdateDTO;
import com.teachme.teachme.dto.SkillWrapper;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Experience;
import com.teachme.teachme.entity.Skill;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.ExperienceRepository;
import com.teachme.teachme.repository.SkillRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class ExperienceService {

    private UserDao userRepository;
    private SkillRepository skillRepository;
    private ExperienceRepository experienceRepository;

    public ExperienceService(UserDao userRepository,
                             SkillRepository skillRepository,ExperienceRepository experienceRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> getAllExperience() {
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> daoUserOptional = userRepository.findByEmail(currentUsername);
        DAOUser user = daoUserOptional.get();

        List<Experience> experiences = experienceRepository.findAllByUserId(user.getId());

        //Set<Experience> experiences = user.getExperiences();
        return experiences;
    }

    public Map<String, Object> createExperience(ExperienceDTO experienceDTO) {
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();

        Optional<DAOUser> daoUserOptional = userRepository.findByEmail(currentUsername);
        DAOUser user = daoUserOptional.get();

        //create new experience
        Experience experience = new Experience();

        //assign properties from DTO
        experience.setName(experienceDTO.getName());
        experience.setDescription(experienceDTO.getDescription());
        experience.setStartdate(experienceDTO.getStartdate());
        experience.setEnddate(experienceDTO.getEnddate());
        experience.setCurrently_working(experienceDTO.getCurrently_working());
        experience.setUser(user);

        experienceRepository.save(experience);

        //get Skill using skillId and set them to Course object
        for(long skill: experienceDTO.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skill);

            if(skillOptional.isPresent())
            {
                Skill skillToUpdate = skillOptional.get();
                skillToUpdate.addExperience(experience);
            }
        }

        //now send a message body as response
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Experience Added");
        body.put("status",200);
        body.put("path","/");
        return body;
    }

    public Experience getExperience(long id) {
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();

        Optional<DAOUser> daoUserOptional = userRepository.findByEmail(currentUsername);
        DAOUser user = daoUserOptional.get();

        Optional<Experience> experienceOptional = experienceRepository.findByIdAndUserId(id,user.getId());
        if(experienceOptional.isPresent())
        {
            return experienceOptional.get();
        }
        /*
        Optional<Experience> experienceOptional = experienceRepository.findById(id);
        if(experienceOptional.isPresent())
        {
            Experience experience = experienceOptional.get();
            if(user.getExperiences().contains(experience))
            {
                return experience;
            }
        }
         */
        //if not found then raise exception
        throw new CustomException("Experience Not Found", HttpStatus.NOT_FOUND,"/experience");
    }

    public Object deleteExperience(long id) {
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();

        Optional<DAOUser> daoUserOptional = userRepository.findByEmail(currentUsername);
        DAOUser user = daoUserOptional.get();

        Optional<Experience> experienceOptional = experienceRepository.findByIdAndUserId(id,user.getId());
        if(experienceOptional.isPresent())
        {
            experienceRepository.deleteExperienceById(id);
            //Experience experience = experienceOptional.get();
            //experienceRepository.delete(experience);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("message","Experience Deleted");
            body.put("status",200);
            body.put("path","/");
            return body;
        }
        /*
        Optional<Experience> experienceOptional = experienceRepository.findById(id);
        if(experienceOptional.isPresent())
        {
            Experience experience = experienceOptional.get();
            if(user.getExperiences().contains(experience))
            {
                //this means that experience belongs to this user
                //now delete this from experience table
                experienceRepository.deleteById(id);
                //System.out.println("Here");
                //body to send
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("message","Experience Deleted");
                body.put("status",200);
                body.put("path","/");
                return body;
            }
        }
         */
        //if not found then raise exception
        throw new CustomException("Experience Not Found", HttpStatus.NOT_FOUND,"/experience");
    }

    public Map<String, Object> addSkillsToExperience(long id,SkillWrapper skillWrapper) {
        log.info("In addSkillsToExperience Service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        Optional<Experience> experience = experienceRepository.findByIdAndUserId(user.getId(),id);

        if(experience.isEmpty())
        {
            throw new CustomException("Experience Not Found",HttpStatus.NOT_FOUND,"/");
        }


        for (long skillId: skillWrapper.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skillId);
            if(skillOptional.isPresent())
            {
                //skillList.remove(skillOptional.get());
                //saving this experience to skill also
                skillOptional.get().addExperience(experience.get());
            }
        }

        log.info("Completed addSkillsToExperience");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Skills Added");
        body.put("status",200);
        body.put("path","/");
        return body;
    }

    public Object removeSkillsToExperience(long id, SkillWrapper skillWrapper) {
        log.info("In removeSkillsToExperience Service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        Optional<Experience> experience = experienceRepository.findByIdAndUserId(user.getId(),id);

        if(experience.isEmpty())
        {
            throw new CustomException("Experience Not Found",HttpStatus.NOT_FOUND,"/");
        }

        Set<Skill> skillList = user.getSkills();

        for (long skillId: skillWrapper.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skillId);
            if(skillOptional.isPresent())
            {
                //get experiences of this skill and remove current experience from it
                //skillOptional.get().getExperienceSet().remove(experience.get());
                skillOptional.get().removeExperience(experience.get());
            }
        }

        log.info("Completed removeSkillsToExperience");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Skills Removed");
        body.put("status",200);
        body.put("path","/");
        return body;

    }

    public Map<String, Object> updateExperience(long id, ExperienceUpdateDTO experienceDTO) {
        log.info("In updateExperience Service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();


        Optional<Experience> experience = experienceRepository.findByIdAndUserId(user.getId(), id);
        if(experience.isEmpty())
        {
            log.warn("Experience Not Found");
            throw new CustomException("Experience Not Found",HttpStatus.NOT_FOUND,"/");
        }

        Experience experienceObject = experience.get();

        log.info("Updating Experience Id: ",experienceObject.getId());

        if(experienceDTO.getName() != null)
            experienceObject.setName(experienceDTO.getName());


        if(experienceDTO.getDescription() != null)
            experienceObject.setDescription(experienceDTO.getDescription());

        if(experienceDTO.getCurrently_working() != null)
            experienceObject.setCurrently_working(experienceDTO.getCurrently_working());

        if(experienceDTO.getStartdate() != null)
            experienceObject.setStartdate(experienceDTO.getStartdate());

        if(experienceDTO.getEnddate() != null)
            experienceObject.setEnddate(experienceDTO.getEnddate());

        //Get Skills To Update In Other Request
        experienceRepository.save(experienceObject);
        log.info("Experience Details Updated");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Experience Details Updated");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting updateExperience Service");

        return body;
    }
}
