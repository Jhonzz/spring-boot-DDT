package academy.devdojo.commons;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileUtils {
    @Autowired
    private ResourceLoader resourceLoader;

    @SneakyThrows //throw exception without put in signature method (try catch)
    public String readResourceFile(String fileName) {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }
}
