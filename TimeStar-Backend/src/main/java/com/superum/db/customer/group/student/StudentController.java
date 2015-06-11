package com.superum.db.customer.group.student;

import static com.superum.utils.ControllerUtils.RETURN_CONTENT_TYPE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/timestar/api")
public class StudentController {

	@RequestMapping(value = "/student/add", method = RequestMethod.POST, produces = RETURN_CONTENT_TYPE)
	public Student addStudent(@RequestBody @Valid Student student) {
		return studentService.addStudent(student);
	}
	
	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET, produces = RETURN_CONTENT_TYPE)
	public Student findStudent(@PathVariable int id) {
		return studentService.findStudent(id);
	}
	
	@RequestMapping(value = "/student/update", method = RequestMethod.POST, produces = RETURN_CONTENT_TYPE)
	public Student updateStudent(@RequestBody @Valid Student student) {
		return studentService.updateStudent(student);
	}
	
	@RequestMapping(value = "/student/delete/{id}", method = RequestMethod.GET, produces = RETURN_CONTENT_TYPE)
	public Student deleteStudent(@PathVariable int id) {
		return studentService.deleteStudent(id);
	}
	
	@RequestMapping(value = "/student/customer/{customerId}", method = RequestMethod.GET, produces = RETURN_CONTENT_TYPE)
	public List<Student> findStudentsForCustomer(@PathVariable int customerId) {
		return studentService.findStudentsForCustomer(customerId);
	}
	
	@RequestMapping(value = "/student/group/{groupId}", method = RequestMethod.GET, produces = RETURN_CONTENT_TYPE)
	public List<Student> findStudentsForGroup(@PathVariable int groupId) {
		return studentService.findStudentsForGroup(groupId);
	}
	
	// CONSTRUCTORS
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	// PRIVATE
	
	private final StudentService studentService;

}