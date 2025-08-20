package io.nology.intro.greeting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CsvNameRepository implements NameRepository {
    private final Path csvPath = Paths.get("names.csv");

    @Override
    public Optional<String> findByName(String name) {
        try (Stream<String> lines = Files.lines(csvPath)) {
            return lines.filter(line -> line.equalsIgnoreCase(name)).findFirst();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void addName(String name) throws Exception {
        if (findByName(name).isPresent()) {
            throw new Exception("Name already exists");
        }
        Files.writeString(csvPath, name + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

}
