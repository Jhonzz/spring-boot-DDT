package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String name){
        return name == null ? repository.findAll() : repository.findByFirstNameIgnoreCase(name);
    }

    public User findByIdOrNotFoundException(Long id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User user){
        return repository.save(user);
    }

    public void update(User userToUpdate){
        assertUserExists(userToUpdate.getId());
        repository.save(userToUpdate);
    }

    public void delete(Long id){
        var expectedUser = findByIdOrNotFoundException(id);
        repository.delete(expectedUser);
    }

    public void assertUserExists(Long id){
        findByIdOrNotFoundException(id);
    }
}
