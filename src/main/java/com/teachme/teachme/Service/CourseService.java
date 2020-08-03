package com.teachme.teachme.service;

import com.teachme.teachme.DTO.CourseDTO;
import com.teachme.teachme.DTO.CourseUpdateDTO;
import com.teachme.teachme.DTO.SkillWrapper;
import com.teachme.teachme.entity.Course;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Experience;
import com.teachme.teachme.entity.Skill;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.CourseRepository;
import com.teachme.teachme.repository.SkillRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class CourseService {

    private UserDao userRepository;
    private CourseRepository courseRepository;
    private SkillRepository skillRepository;

    public CourseService(UserDao userRepository,CourseRepository courseRepository,
                         SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.skillRepository = skillRepository;
    }

    public List<Course> getAllCourses() {
        //get user
        log.info("In getAllCourses service");
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        List<Course> courses = courseRepository.findAllByUserId(user.getId());

        log.info("Exiting getAllCourses service");
        return courses;
    }

    public Object createCourse(CourseDTO courseDTO) {
        log.info("In createCourse service");
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        //create new experience
        Course course = new Course();

        //assign properties from DTO
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCourseDuration(courseDTO.getCourseDuration());
        course.setOfferedPrice(courseDTO.getOfferedPrice());

        course.setAverageRating(0f);
        course.setIsPublic(true);
        course.setUser(user);

        courseRepository.save(course);


        //get Skill using skillId and set them to Course object
        for(long skill: courseDTO.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skill);

            if(skillOptional.isPresent())
            {
                Skill skillToUpdate = skillOptional.get();
                skillToUpdate.addCourse(course);
            }
        }

        //now send a message body as response
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Course Added");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting createCourse service");

        return body;
    }

    public Course getCourses(long id) {
        log.info("In getCourses service");
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        Optional<Course> courseOptional = courseRepository.findByIdAndUserId(id,user.getId());
        if(courseOptional.isPresent())
        {
            log.info("Exiting getCourses service");
            return courseOptional.get();
        }

        //if not found then raise exception
        throw new CustomException("Course Not Found", HttpStatus.NOT_FOUND,"/Course");
    }

    public Object deleteCourses(long id) {
        log.info("In getCourses service");
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        Optional<Course> courseOptional = courseRepository.findByIdAndUserId(id,user.getId());

        if(courseOptional.isPresent())
        {
            courseRepository.deleteCourseById(id);

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("message","Course Deleted");
            body.put("status",200);
            body.put("path","/");

            log.info("Exiting deleteCourses service");
            return body;
        }

        //if not found then raise exception
        throw new CustomException("Course Not Found", HttpStatus.NOT_FOUND,"/Course");

    }

    public Object updateCourses(long id, CourseUpdateDTO courseUpdateDTO) {
        log.info("In updateCourses Service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);


        Optional<Course> courseOptional = courseRepository.findByIdAndUserId(user.getId(), id);
        if(courseOptional.isEmpty())
        {
            log.warn("Course Not Found");
            throw new CustomException("Course Not Found",HttpStatus.NOT_FOUND,"/");
        }

        Course course = courseOptional.get();

        log.info("Updating Course Id: ",course.getId());

        if(courseUpdateDTO.getTitle() != null)
            course.setTitle(courseUpdateDTO.getTitle());


        if(courseUpdateDTO.getDescription() != null)
            course.setDescription(courseUpdateDTO.getDescription());

        if(courseUpdateDTO.getOfferedPrice() != null)
            course.setOfferedPrice(courseUpdateDTO.getOfferedPrice());

        if(courseUpdateDTO.getCourseDuration() != null)
            course.setCourseDuration(courseUpdateDTO.getCourseDuration());

        /*

        //update skills if any
        List<Integer> newSkillList = courseUpdateDTO.getSkillIdList();

        //current skills of this course
        Set<Skill> skillSet = course.getSkills();

        if(newSkillList != null)
        {
            //only these skills we need to add
            for(long skill : newSkillList)
            {
                Optional<Skill> SkillbyId = skillRepository.findById(skill);
                if(SkillbyId.isPresent())
                {
                    //if this skill is not in current skill set it means we have to remove it from skill set
                    if(skillSet.contains(SkillbyId.get()))
                    {
                        skillSet.remove(SkillbyId.get());
                    }
                }
            }
        }
        */
        //Get Skills To Update In Other Request
        courseRepository.save(course);
        log.info("Course Details Updated");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Course Details Updated");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting updateCourses Service");

        return body;
    }


    public Map<String, Object> addSkillsToCourse(long id, SkillWrapper skillWrapper) {
        log.info("In addSkillsToCourse Service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        Optional<Course> course = courseRepository.findByIdAndUserId(user.getId(),id);

        if(course.isEmpty())
        {
            throw new CustomException("Course Not Found",HttpStatus.NOT_FOUND,"/");
        }


        for (long skillId: skillWrapper.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skillId);
            if(skillOptional.isPresent())
            {
                skillOptional.get().addCourse(course.get());
            }
        }

        log.info("Completed addSkillsToCourse service");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Skills Added");
        body.put("status",200);
        body.put("path","/");
        return body;
    }

    public Object removeSkillsToCourse(long id, SkillWrapper skillWrapper) {
        log.info("In removeSkillsToCourse Service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        Optional<Course> course = courseRepository.findByIdAndUserId(user.getId(),id);

        if(course.isEmpty())
        {
            throw new CustomException("course Not Found",HttpStatus.NOT_FOUND,"/");
        }

        for (long skillId: skillWrapper.getSkillIdList())
        {
            Optional<Skill> skillOptional = skillRepository.findById(skillId);
            if(skillOptional.isPresent())
            {
                skillOptional.get().removeCourse(course.get());
            }
        }

        log.info("Completed removeSkillsToCourse service");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Skills Removed");
        body.put("status",200);
        body.put("path","/");
        return body;

    }

    public Object changeStatus(long id) {

        log.info("In changeStatus service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        Optional<Course> course = courseRepository.findByIdAndUserId(user.getId(),id);

        if(course.isEmpty() ){
            throw new CustomException("course Not Found",HttpStatus.NOT_FOUND,"/");
        }

        course.get().setIsPublic(! course.get().getIsPublic() );

        courseRepository.save(course.get());

        log.info("Completed changeStatus service");

        return course.get();
    }
}
