package com.rajitblog.api;

import com.rajitblog.model.Student;
import com.rajitblog.utils.BindingResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
public class StudentController {
    private static final String STUDENT_ADD_URL = "/api/v1/add/student";

    @RequestMapping(value = STUDENT_ADD_URL, method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(@RequestHeader String authorization,@Validated @RequestBody String student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new BindingResultHelper().getErrorMessage(STUDENT_ADD_URL,bindingResult),HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(new Student(101, "Rajesh"),HttpStatus.CREATED);
        } catch (Exception e) {
            throw new AppExceptionCreator().createException(STUDENT_ADD_URL,e);
        }

    }
}
