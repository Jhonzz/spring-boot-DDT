package academy.devdojo.commons;

import academy.devdojo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUtils {

    public List<Producer> newProducerList(){
        var dateTime = "2026-01-20T20:09:29.4158618";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);

        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(localDateTime).build();
        var madHouse = Producer.builder().id(2L).name("MadHouse").createdAt(localDateTime).build();
        var saru = Producer.builder().id(3L).name("Saru").createdAt(localDateTime).build();
        var studioGhibli = Producer.builder().id(4L).name("StudioGhibli").createdAt(localDateTime).build();
        return new ArrayList<>(List.of(ufotable, madHouse, saru, studioGhibli));
    }

    public Producer newProducerToSave(){
        return Producer.builder().id(99L).name("Mappa").createdAt(LocalDateTime.now()).build();
    }
}
