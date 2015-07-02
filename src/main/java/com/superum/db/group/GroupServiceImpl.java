package com.superum.db.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

	@Override
	public Group addGroup(Group group) {
		return groupDAO.create(group);
	}

	@Override
	public Group findGroup(int id) {
		return groupDAO.read(id);
	}

	@Override
	public Group updateGroup(Group group) {
		return groupDAO.update(group);
	}

	@Override
	public Group deleteGroup(int id) {
		return groupDAO.delete(id);
	}

	@Override
	public List<Group> findGroupsForCustomer(int customerId) {
		return groupQueries.readAllForCustomer(customerId);
	}
	
	@Override
	public List<Group> findGroupsForTeacher(int teacherId) {
		return groupDAO.readAllForTeacher(teacherId);
	}
	
	@Override
	public List<Group> findGroupsForCustomerAndTeacher(int customerId, int teacherId) {
		return groupQueries.readAllForCustomerAndTeacher(customerId, teacherId);
	}

	// CONSTRUCTORS

	@Autowired
	public GroupServiceImpl(GroupDAO groupDAO, GroupQueries groupQueries) {
		this.groupDAO = groupDAO;
		this.groupQueries = groupQueries;
	}

	// PRIVATE
	
	private final GroupDAO groupDAO;
	private final GroupQueries groupQueries;
	
}