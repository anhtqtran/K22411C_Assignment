package com.tranduythanh.connectors;

import com.tranduythanh.models.Employee;
import com.tranduythanh.models.ListEmployee;

public class EmployeeConnector
{
    public Employee login(String usr,String pwd)
    {
        ListEmployee le=new ListEmployee();
        le.gen_dataset();
        for (Employee emp : le.getEmployees())
        {
            if (emp.getUsername().equalsIgnoreCase(usr) && emp.getPassword().equals(pwd))
            {
                return emp;
            }
        }
        return null;
    }
}
