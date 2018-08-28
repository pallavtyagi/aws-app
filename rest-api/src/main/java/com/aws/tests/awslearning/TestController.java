package com.aws.tests.awslearning;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String testMethodGet(@RequestBody Post post) {


        return "Hello World!!";
    }
}
