package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.UserHardCodedRepository;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserHardCodedRepository repository;
    private final UserRepository userRepository;

    public List<User> findAll(String name){
        return name == null ? userRepository.findAll() : repository.findByFirstName(name);
    }

    public User findByIdOrNotFoundException(Long id){
        return repository.findByid(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User user){
        return repository.save(user);
    }

    public void update(User userToUpdate){
        assertUserExists(userToUpdate.getId());
        repository.update(userToUpdate);
    }

    public void delete(Long id){
        var expectedUser = findByIdOrNotFoundException(id);
        repository.delete(expectedUser);
    }

    public void assertUserExists(Long id){
        findByIdOrNotFoundException(id);
    }
}
