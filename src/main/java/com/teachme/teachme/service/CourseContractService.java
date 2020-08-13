package com.teachme.teachme.service;

import com.teachme.teachme.dto.CreateCourseContractDTO;
import com.teachme.teachme.dto.UpdateCourseContractDTO;
import com.teachme.teachme.entity.Course;
import com.teachme.teachme.entity.CourseContract;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.RequestContract;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.CourseContractRepository;
import com.teachme.teachme.repository.CourseRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseContractService {

    CourseRepository courseRepository;

    UserDao userRepository;

    CourseContractRepository courseContractRepository;

    public CourseContractService( CourseRepository courseRepository, UserDao userRepository, CourseContractRepository courseContractRepository ){

        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseContractRepository = courseContractRepository;
    }

    public Object createCourseContract(long course_id, CreateCourseContractDTO createCourseContractDTO ){

        Optional<Course> courseOptional = courseRepository.findById( course_id );

        if( courseOptional.isEmpty() ){

            throw new CustomException("Course Not Found", HttpStatus.NOT_FOUND,"/Course");
        }

        Course course = courseOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser teacher = userOptional.get();

        DAOUser courseOwner = course.getUser();

        if( teacher.getId() != courseOwner.getId() ){

            throw new CustomException("User Not Valid", HttpStatus.UNAUTHORIZED,"/Course");
        }

        userOptional = userRepository.findById( createCourseContractDTO.getStudent_id() );

        if( userOptional.isEmpty() ){

            throw new CustomException("Student Not Found", HttpStatus.NOT_FOUND,"/Course");
        }

        DAOUser student = userOptional.get();

        Optional<CourseContract> courseContractOptional = courseContractRepository.findByStudentAndCourse( student, course );

        if( courseContractOptional.isPresent() ){

            throw new CustomException("Student has already an contract for this course", HttpStatus.CONFLICT,"/Course");
        }

        CourseContract courseContract = new CourseContract();
        courseContract.setTeacher( teacher );
        courseContract.setStudent( student );
        courseContract.setCourse( course );
        courseContract.setHourlyPricing( createCourseContractDTO.isHourlyPricing() );
        courseContract.setPrice( createCourseContractDTO.getPrice() );

        courseContractRepository.save( courseContract );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Contract created successfully");
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object getAllCourseContractForStudent(){

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );

        if( userOptional.isEmpty() ){

            throw new CustomException("Student Not Found", HttpStatus.NOT_FOUND,"/Course");
        }
        DAOUser student = userOptional.get();

        List<CourseContract> courseContractList = courseContractRepository.findAllByStudent( student );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Course Contracts", courseContractList );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object getAllCourseContractForTeacher(){

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );

        if( userOptional.isEmpty() ){

            throw new CustomException("Teacher Not Found", HttpStatus.NOT_FOUND,"/Course");
        }
        DAOUser teacher = userOptional.get();

        List<CourseContract> courseContractList = courseContractRepository.findAllByTeacher( teacher );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Course Contracts", courseContractList );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object updateCourseContract(long contract_id, UpdateCourseContractDTO updateCourseContractDTO ){

        Optional<CourseContract> courseContractOptional = courseContractRepository.findById( contract_id );

        if( courseContractOptional.isEmpty() ){

            throw new CustomException( "Contract does not exsist", HttpStatus.NOT_FOUND,"/Course");
        }

        CourseContract courseContract = courseContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser teacher = userOptional.get();

        DAOUser contractowner = courseContract.getTeacher();

        if( teacher.getId() != contractowner.getId() ){

            throw new CustomException( "Teacher not valid to update contract details", HttpStatus.UNAUTHORIZED,"/Course" );
        }

        courseContract.setHourlyPricing( updateCourseContractDTO.isHourlyPricing() );
        courseContract.setPrice( updateCourseContractDTO.getPrice() );
        courseContractRepository.save( courseContract );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Contract Updated Successfully" );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object deleteCourseContract( long contract_id ){

        Optional<CourseContract> courseContractOptional = courseContractRepository.findById( contract_id );

        if( courseContractOptional.isEmpty() ){

            throw new CustomException( "Contract does not exsist", HttpStatus.NOT_FOUND,"/Course");
        }

        CourseContract courseContract = courseContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser teacher = userOptional.get();

        DAOUser contractowner = courseContract.getTeacher();

        if( teacher.getId() != contractowner.getId() ){

            throw new CustomException( "Teacher not valid to delete contract", HttpStatus.UNAUTHORIZED,"/Course" );
        }

        courseContractRepository.deleteById( courseContract.getId() );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Contract deleted successfully" );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object changeCourseContractStatus( long contract_id ){

        Optional<CourseContract> courseContractOptional = courseContractRepository.findById( contract_id );

        if( courseContractOptional.isEmpty() ){

            throw new CustomException( "Contract does not exsist", HttpStatus.NOT_FOUND,"/Course");
        }

        CourseContract courseContract = courseContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        DAOUser contractAcceptor = courseContract.getStudent();

        if( student.getId() != contractAcceptor.getId() ){

            throw new CustomException( "Student not valid to accept this contract", HttpStatus.UNAUTHORIZED,"/Course" );
        }

        courseContract.setAccepted( !courseContract.isAccepted() );
        courseContractRepository.save( courseContract );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Status of contract changed successfully" );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

}
