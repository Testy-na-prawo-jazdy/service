package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.parser.CsvReader;
import com.github.drivingtest.server.parser.TaskSpecialist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {
    private final CsvReader csvReader;


    public TestController(CsvReader csvReader) {
        this.csvReader = csvReader;
    }
    @RequestMapping("index")
    public ResponseEntity<List<TaskSpecialist>> index(){
        HashSet<String> hashSet = new HashSet<>();


    //    List<String[]> categories = csvReader.getTaskSpecialistList().stream().map(TaskSpecialist::getCategories).collect(Collectors.toList());
        csvReader.getTaskSpecialistList().forEach(e -> {
            List<String> cats = e.getCategories();
            hashSet.addAll(cats);
        });


        System.out.println(hashSet);


        return new ResponseEntity<>(csvReader.getTaskSpecialistList(), HttpStatus.OK);
    }
}
