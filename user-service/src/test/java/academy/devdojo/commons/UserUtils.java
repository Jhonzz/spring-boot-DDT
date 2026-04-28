package academy.devdojo.commons;

import academy.devdojo.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public List<User> newUserList() {
        {
            var jhon = User.builder().id(1L).firstName("Ging").lastName("Freecss").email("ging.freecss@HXH.com").build();
            var willian = User.builder().id(2L).firstName("Killua").lastName("Zoldyck").email("killua.zoldyck@HXH.com").build();
            var roberto = User.builder().id(3L).firstName("Meruem").lastName("Komugi").email("meruem.komugi@HXH.com").build();
            var paqueta = User.builder().id(4L).firstName("Hisoka").lastName("Morow").email("hisoka.morow@HXH.com").build();

            return new ArrayList<>(List.of(jhon, willian, roberto, paqueta));
        }
    }
     public User userToSave(){
       return User.builder().firstName("Gon").lastName("Freecss").email("gon.freecss@HXH.com").build();
     }

    public User newUserSaved(){
        return User.builder().id(1L).firstName("Gon").lastName("Freecss").email("gon.freecss@HXH.com").build();
    }
}
