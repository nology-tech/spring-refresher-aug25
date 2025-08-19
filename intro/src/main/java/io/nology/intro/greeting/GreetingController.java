package io.nology.intro.greeting;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.intro.greeting.dtos.CreateNameDto;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping()
    public String defaultGreeting() {
        return this.greetingService.defaultGreeting();
    }

    @PostMapping()
    public String addName(@RequestBody @Valid CreateNameDto data) {
        try {
            return this.greetingService.addName(data);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getByName(@PathVariable String name) {
        try {
            String foundName = this.greetingService.greetByName(name);
            // return new ResponseEntity<>("Hello " + foundName, HttpStatus.OK);
            return ResponseEntity.ok("Hello " + foundName);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
