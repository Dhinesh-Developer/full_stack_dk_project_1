
package com.dk.app;

import com.dk.bussiness.employeeBoImplementation;
import com.dk.modules.employee;
import java.util.List;

public class program1 {

    public static void main(String[] args) {

        employeeBoImplementation empbo = new employeeBoImplementation();
        List<employee> employees = empbo.getAll();

        for (employee ep : employees) {
            System.out.println(ep);
        }
    }
}

