package io.nology.intro.greeting;

import java.util.Optional;

public interface NameRepository {
    Optional<String> findByName(String name);

    void addName(String name) throws Exception;
}
