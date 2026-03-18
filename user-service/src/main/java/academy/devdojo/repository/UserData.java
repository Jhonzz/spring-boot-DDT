package academy.devdojo.repository;

import academy.devdojo.domain.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {
    @Getter
    private final List<User> users = new ArrayList<>(4);

    {
        var jhon = User.builder().id(1L).firstName("Jhon").lastName("Pablo").email("jhon@teste.com").build();
        var willian = User.builder().id(2L).firstName("Willian").lastName("Suane").email("willian@teste.com").build();
        var roberto = User.builder().id(3L).firstName("Roberto").lastName("Justo").email("robertera@teste.com").build();
        var paqueta = User.builder().id(4L).firstName("Lucas").lastName("Paqueta").email("lucas@teste.com").build();
        users.addAll(List.of(jhon, willian, roberto, paqueta));
    }

}
