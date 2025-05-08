package com.tranthanhqueanh.models;

import com.tranthanhqueanh.k22411csampleproject.R;

import java.util.ArrayList;

public class ListEmployee {
    private ArrayList<Employee> employees;

    public ListEmployee() {
        employees=new ArrayList<>();
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
    public void gen_dataset() {
        // Ae Sun
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Ae Sun");
        e1.setEmail("aesun.mckinley@gmail.com");
        e1.setPhone("0123456789");
        e1.setUsername("aesun");
        e1.setPassword("123456");
        e1.setImage(R.drawable.img_employee_1);
        employees.add(e1);

        // Gwan Sik
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Gwan Sik");
        e2.setEmail("gwansik.mckinley@gmail.com");
        e2.setPhone("0123456790");
        e2.setUsername("gwansik");
        e2.setPassword("123456");
        e2.setImage(R.drawable.img_employee_2);
        employees.add(e2);

        // Geum Myeong
        Employee e3 = new Employee();
        e3.setId(3);
        e3.setName("Geum Myeong");
        e3.setEmail("geummyeong.mckinley@gmail.com");
        e3.setPhone("0123456791");
        e3.setUsername("geummyeong");
        e3.setPassword("123456");
        e3.setImage(R.drawable.img_employee_3);
        employees.add(e3);

        // Chung Seob
        Employee e4 = new Employee();
        e4.setId(4);
        e4.setName("Chung Seob");
        e4.setEmail("chungseob.mckinley@gmail.com");
        e4.setPhone("0123456792");
        e4.setUsername("chungseob");
        e4.setPassword("123456");
        e4.setImage(R.drawable.img_employee_4);
        employees.add(e4);

        // Yeong Beom
        Employee e5 = new Employee();
        e5.setId(5);
        e5.setName("Yeong Beom");
        e5.setEmail("yeongbeom.mckinley@gmail.com");
        e5.setPhone("0123456793");
        e5.setUsername("yeongbeom");
        e5.setPassword("123456");
        e5.setImage(R.drawable.img_employee_5);
        employees.add(e5);
    }
}
