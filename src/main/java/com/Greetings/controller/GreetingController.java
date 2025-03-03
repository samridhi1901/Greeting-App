package com.Greetings.controller;

import com.Greetings.model.Greeting;
import com.Greetings.service.GreetingService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @PostMapping
    public Greeting saveGreeting(@RequestParam String message) {
        return greetingService.saveGreeting(message);
    }

    @GetMapping("/{id}")
    public Optional<Greeting> getGreeting(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
    }
}
