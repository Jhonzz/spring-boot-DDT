package academy.devdojo.commons;

import academy.devdojo.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public List<User> newUserList() {
        {
            var jhon = User.builder()
                    .id(1L)
                    .firstName("Ging")
                    .lastName("Freecss")
                    .email("ging.freecss@HXH.com")
                    .roles("USER")
                    .password("{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS")
                    .build();
            var willian = User.builder()
                    .id(2L).
                    firstName("Killua")
                    .lastName("Zoldyck")
                    .email("killua.zoldyck@HXH.com")
                    .roles("USER")
                    .password("{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS")
                    .build();

            var roberto = User.builder()
                    .id(3L).firstName("Meruem")
                    .lastName("Komugi")
                    .email("meruem.komugi@HXH.com")
                    .roles("USER")
                    .password("{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS")
                    .build();

            var paqueta = User.builder()
                    .id(4L)
                    .firstName("Hisoka")
                    .lastName("Morow")
                    .email("hisoka.morow@HXH.com")
                    .roles("USER")
                    .password("{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS")
                    .build();

            return new ArrayList<>(List.of(jhon, willian, roberto, paqueta));
        }
    }

    public User userToSave() {
        return User.builder()
                .firstName("Gon")
                .lastName("Freecss")
                .email("gon.freecss@HXH.com")
                .roles("USER")
                .password("{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS")
                .build();
    }

    public User newUserSaved() {
        return User.builder()
                .id(1L)
                .firstName("Gon")
                .lastName("Freecss")
                .email("gon.freecss@HXH.com")
                .roles("USER")
                .password("{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS")
                .build();
    }
}
