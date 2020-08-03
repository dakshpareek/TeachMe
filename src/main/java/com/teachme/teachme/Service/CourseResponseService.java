package com.teachme.teachme.service;

import com.teachme.teachme.DTO.CourseResponseDTO;
import com.teachme.teachme.entity.Course;
import com.teachme.teachme.entity.CourseResponse;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Skill;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.CourseRepository;
import com.teachme.teachme.repository.CourseResponseRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CourseResponseService {

    private CourseResponseRepository courseResponseRepository;
    private UserDao userRepository;
    private CourseRepository courseRepository;
    public CourseResponseService(CourseResponseRepository courseResponseRepository,
                                 UserDao userRepository,CourseRepository courseRepository) {
        this.courseResponseRepository = courseResponseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public Object getAllResponses() {
        //get user
        log.info("In getAllResponses service");
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        List<CourseResponse> responseList = courseResponseRepository.findAllByUserId(user.getId());

        log.info("Exiting getAllResponses service");

        return responseList;
    }

    public CourseResponse getResponses(long id) {
        log.info("In getResponses service");
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        Optional<CourseResponse> courseOptional = courseResponseRepository.findByIdAndUserId(id,user.getId());

        if(courseOptional.isEmpty())
        {
            //if not found then raise exception
            throw new CustomException("Course Response Not Found", HttpStatus.NOT_FOUND,"/");
        }

        log.info("Exiting getResponses service");
        return courseOptional.get();

    }

    public Object deleteResponse(long id) {
        log.info("In deleteResponse service");
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        Optional<CourseResponse> optionalCourseResponse = courseResponseRepository.findByIdAndUserId(id, user.getId());

        if(optionalCourseResponse.isEmpty())
        {
            //if not found then raise exception
            throw new CustomException("Course Response Not Found", HttpStatus.NOT_FOUND,"/");
        }

        courseResponseRepository.deleteById(id);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Course Response Deleted");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting deleteResponse service");
        return body;

    }

    public Object createResponse(CourseResponseDTO courseResponseDTO,long id) {
        log.info("In createResponse service");
        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        //check if course exists or not
        Optional<Course> course = courseRepository.findById(id);
        if(course.isEmpty())
        {
            throw new CustomException("Course Not Found", HttpStatus.NOT_FOUND,"/");
        }

        //create new course response
        CourseResponse courseResponse = new CourseResponse();

        //assign properties from DTO
        courseResponse.setMessage(courseResponseDTO.getMessage());
        courseResponse.setProposedPrice(courseResponseDTO.getProposedPrice());
        courseResponse.setCourse(course.get());
        courseResponse.setUser(user);

        courseResponseRepository.save(courseResponse);

        //now send a message body as response
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Course Response Added");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting createResponse service");

        return body;
    }

    public Object changeStatus(long id, long rid) {

        log.info("In changeStatus service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        //check if this course belongs to this user then only he can change status of response id
        Optional<Course> course = courseRepository.findByIdAndUserId(id,user.getId());

        if(course.isEmpty() ){
            throw new CustomException("Course Not Found",HttpStatus.NOT_FOUND,"/");
        }

        Optional<CourseResponse> responseOptional = courseResponseRepository.findById(rid);
        if(responseOptional.isEmpty())
        {
            throw new CustomException("Course Response Not Found",HttpStatus.NOT_FOUND,"/");
        }

        responseOptional.get().setStatus(! responseOptional.get().getStatus());

        courseResponseRepository.save(responseOptional.get());
        log.info("Changed Status of Response");

        log.info("Completed changeStatus service");

        return responseOptional.get();
    }

    public List<CourseResponse> getResponsesOnCourse(long id, int status) {
        log.info("In getResponsesOnCourse service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        //check if this course belongs to this user
        Optional<Course> course = courseRepository.findByIdAndUserId(id, user.getId());
        if (course.isEmpty()) {
            throw new CustomException("Course Not Found", HttpStatus.NOT_FOUND, "/");
        }

        List<CourseResponse> courseResponseList;

        //if no status is given then return all responses in spite of their status
        if (status == -1)
        {
            courseResponseList = courseResponseRepository.findAllByCourseId(id);
        }
        else
        {
            //get responses based on status also
            courseResponseList = courseResponseRepository.findAllByCourseIdAndStatus(id,status > 0 ? true : false);
        }


        log.info("Completed getResponsesOnCourse service");
        return courseResponseList;
    }

    public Object getResponseOnCourseById(long id, long rid) {
        log.info("In getResponseOnCourseById service");

        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername);

        //check if this course belongs to this user
        Optional<Course> course = courseRepository.findByIdAndUserId(id,user.getId());
        if(course.isEmpty() )
        {
            throw new CustomException("Course Not Found",HttpStatus.NOT_FOUND,"/");
        }

        Optional<CourseResponse> byIdAndCourseId = courseResponseRepository.findByIdAndCourseId(rid, id);

        if(byIdAndCourseId.isEmpty())
        {
            throw new CustomException("Course Response Not Found",HttpStatus.NOT_FOUND,"/");
        }

        log.info("Exiting getResponseOnCourseById service");
        return byIdAndCourseId.get();
    }
}
