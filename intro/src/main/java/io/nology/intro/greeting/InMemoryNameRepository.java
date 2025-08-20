package io.nology.intro.greeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
// @Primary
public class InMemoryNameRepository implements NameRepository {
    private final List<String> names = new ArrayList<>(Arrays.asList("alice", "bob", "charlie"));

    @Override
    public Optional<String> findByName(String name) {
        return names.stream().filter(n -> n.equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public void addName(String name) throws Exception {
        if (names.contains(name)) {
            throw new Exception("Name already exists");
        }
        names.add(name);
    }

}
