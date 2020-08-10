package com.teachme.teachme.service;

import com.teachme.teachme.dto.SkillWrapper;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Skill;
import com.teachme.teachme.repository.SkillRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class UserSkillService {

    private UserDao userRepository;
    private SkillRepository skillRepository;

    public UserSkillService(UserDao userRepository,
                            SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }


    public Stream<Skill> showSkills() {
        Optional<String> currentUsername = SecurityUtils.getCurrentUsername();
        Optional<DAOUser> userOptional = userRepository.findByEmail(currentUsername.get());

        DAOUser user = userOptional.get();

        Set<Skill> skills = user.getSkills();
        Stream<Skill> skillStream = skills.stream().filter(skill -> skill.isVerificationstatus() == true);
        return skillStream;

    }

    public Map<String, Object> addSkillstoUser(SkillWrapper skillWrapper) {

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail(currentUsername);

        DAOUser user = userOptional.get();

        Set<Skill> skillList = user.getSkills();

        for (int skillId: skillWrapper.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skillId);
            if(skillOptional.isPresent())
            {
                skillList.add(skillOptional.get());
            }
        }

        user.setSkills(skillList);
        userRepository.save(user);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Skills Added");
        body.put("status",200);
        body.put("path","/");
        return body;
    }

    public Map<String, Object> deleteSkillsofUser(SkillWrapper skillWrapper) {
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail(currentUsername);

        DAOUser user = userOptional.get();

        Set<Skill> skillList = user.getSkills();

        for (int skillId: skillWrapper.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skillId);
            if(skillOptional.isPresent())
            {
                skillList.remove(skillOptional.get());
            }
        }

        user.setSkills(skillList);
        userRepository.save(user);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Skills Deleted");
        body.put("status",200);
        body.put("path","/");
        return body;
    }


}
