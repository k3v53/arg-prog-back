package com.kevinvicente.argprogback.argprogback;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @GetMapping("/hello_world")
  public Map<String, String> helloWorld() {
    Map<String, String> response = new HashMap<>();
    response.put("hello", "world");
    return response;
  }

}
