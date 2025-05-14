package com.tranthanhqueanh.connectors;

import com.tranthanhqueanh.models.Customer;
import com.tranthanhqueanh.models.ListCustomer;

import java.util.ArrayList;

public class CustomerConnector {
    ListCustomer listCustomer;

    public CustomerConnector() {
        listCustomer = new ListCustomer();
        listCustomer.generate_sample_dataset();
    }

    public ArrayList<Customer> get_all_customers() {
        if (listCustomer == null) {
            listCustomer = new ListCustomer();
            listCustomer.generate_sample_dataset();
        }
        return listCustomer.getCustomers();
    }

    public ArrayList<Customer> get_customers_by_provider(String provider) {
        if (listCustomer == null) {
            listCustomer = new ListCustomer();
            listCustomer.generate_sample_dataset();
        }
        ArrayList<Customer>results = new ArrayList<>();
        for (Customer c : listCustomer.getCustomers()) {
            if (c.getPhone().startsWith(provider)) {
                results.add(c);
            }
        }
        return results;
    }
}


