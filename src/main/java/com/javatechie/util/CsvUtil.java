package com.javatechie.util;

import com.javatechie.dto.Employee;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    private static final String FILE_PATH = "data/employees_100.csv";

    public static List<Employee> readEmployeesFromClasspath() {

        List<Employee> employees = new ArrayList<>();

        try {

            ClassPathResource resource = new ClassPathResource(FILE_PATH);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream())
            );

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                Employee emp = new Employee(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        Integer.parseInt(data[5])
                );

                employees.add(emp);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV file from classpath", e);
        }

        return employees;
    }
}
