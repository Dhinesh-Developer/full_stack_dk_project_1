package com.dk.bussiness;

import java.util.List;

import com.dk.modules.employee;

public interface EmployeeBO {
	int save(employee e);
	int delete(int id);
	int delete(employee e);
	int update(employee e);
	int get(int id);
	List<employee> getAll();
}
