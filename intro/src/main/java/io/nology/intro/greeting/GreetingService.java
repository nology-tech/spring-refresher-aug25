package io.nology.intro.greeting;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.nology.intro.greeting.dtos.CreateNameDto;

@Service
public class GreetingService {
    private final NameRepository nameRepository;

    public GreetingService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    public String defaultGreeting() {
        return "Hello from service";
    }

    public String greetByName(String name) throws Exception {
        Optional<String> result = this.nameRepository.findByName(name);
        if (result.isEmpty()) {
            throw new Exception("Name does not exist");
        }
        return result.get();
    }

    public String addName(CreateNameDto data) throws Exception {
        String cleanedName = data.getName().trim().toLowerCase();
        this.nameRepository.addName(cleanedName);
        return cleanedName + " added to repository";
    }

}
