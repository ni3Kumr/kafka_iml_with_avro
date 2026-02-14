package com.javatechie.controller;

import com.javatechie.dto.Employee;
import com.javatechie.producer.KafkaAvroProducer;
import com.javatechie.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private KafkaAvroProducer producer;

    @PostMapping("/events")
    public String sendMessage(@RequestBody Employee employee) {

        List<Employee> employees = CsvUtil.readEmployeesFromClasspath();

        employees.forEach( emp ->  producer.send(emp));
          //  producer.send(employee);


        return "message published !";
    }
}
