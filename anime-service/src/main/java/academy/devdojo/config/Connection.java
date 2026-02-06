package academy.devdojo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class Connection {
    private String host;
    private String username;
    private String password;
}
