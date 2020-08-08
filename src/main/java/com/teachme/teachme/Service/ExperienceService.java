package com.teachme.teachme.service;

import com.teachme.teachme.dto.ExperienceDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Experience;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.ExperienceRepository;
import com.teachme.teachme.repository.SkillRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
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
        /*
        Set<Skill> skillSet = new HashSet<>();
        //get Skill using skillId and set them to Experience object
        for(int skill: experienceDTO.getSkillIdList())
        {
            Optional<Skill> SkillbyId = skillRepository.findById(skill);
            if(SkillbyId.isPresent())
            {
                skillSet.add(SkillbyId.get());
            }
        }


        //set these skills to experience
        experience.setSkills(skillSet);


        //get all experiences and now save this experience to list
        Set<Experience> userExperiences = user.getExperiences();
        userExperiences.add(experience);



        user.setExperiences(userExperiences);
        userRepository.save(user);
        */
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
}
