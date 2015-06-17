package com.superum.db.customer.group;

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
public class GroupController {

	@RequestMapping(value = "/group/add", method = RequestMethod.POST, produces = RETURN_CONTENT_TYPE)
	public Group addGroup(@RequestBody @Valid Group group) {
		return groupService.addGroup(group);
	}
	
	@RequestMapping(value = "/group/{id}", method = RequestMethod.GET, produces = RETURN_CONTENT_TYPE)
	public Group findGroup(@PathVariable int id) {
		return groupService.findGroup(id);
	}
	
	@RequestMapping(value = "/group/update", method = RequestMethod.POST, produces = RETURN_CONTENT_TYPE)
	public Group updateGroup(@RequestBody @Valid Group group) {
		return groupService.updateGroup(group);
	}
	
	@RequestMapping(value = "/group/delete/{id}", method = RequestMethod.GET, produces = RETURN_CONTENT_TYPE)
	public Group deleteGroup(@PathVariable int id) {
		return groupService.deleteGroup(id);
	}
	
	@RequestMapping(value = "/group/customer/{customerId}", method = RequestMethod.GET, produces = RETURN_CONTENT_TYPE)
	public List<Group> findGroupsForCustomer(@PathVariable int customerId) {
		return groupService.findGroupsForCustomer(customerId);
	}
	
	// CONSTRUCTORS
	
	@Autowired
	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	// PRIVATE
	
	private final GroupService groupService;

}