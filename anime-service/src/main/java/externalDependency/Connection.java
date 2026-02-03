package externalDependency;

import lombok.AllArgsConstructor;
import lombok.Data;
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
