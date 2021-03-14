package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.parser.CsvReader;
import com.github.drivingtest.server.parser.TaskSpecialist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Test {
    private final CsvReader csvReader;


    public Test(CsvReader csvReader) {
        this.csvReader = csvReader;
    }
    @RequestMapping("index")
    public ResponseEntity<List<TaskSpecialist>> index(){
        System.out.println(csvReader.getTaskSpecialistList());
        return new ResponseEntity<>(csvReader.getTaskSpecialistList(), HttpStatus.OK);
    }
}
