package academy.devdojo.mapper;

import academy.devdojo.annotation.EncodedMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component //Managed by Spring
public class PasswordEncoderMapper {
    private final PasswordEncoder passwordEncoder;

    @EncodedMapping
    public String encode(String rawPassword){
        return rawPassword == null ? null : passwordEncoder.encode(rawPassword); //validation to update
    }
}
