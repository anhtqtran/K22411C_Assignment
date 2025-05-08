package com.tranthanhqueanh.connectors;

import com.tranthanhqueanh.models.Employee;
import com.tranthanhqueanh.models.ListEmployee;

public class EmployeeConnector {
    private static ListEmployee listEmployee;

    public EmployeeConnector() {
        if (listEmployee == null) {
            listEmployee = new ListEmployee();
            listEmployee.gen_dataset();
        }
    }

    public Employee login(String usr, String pwd) {
        for (Employee emp : listEmployee.getEmployees()) {
            if (emp.getUsername().equalsIgnoreCase(usr) && emp.getPassword().equals(pwd)) {
                return emp;
            }
        }
        return null;
    }
}