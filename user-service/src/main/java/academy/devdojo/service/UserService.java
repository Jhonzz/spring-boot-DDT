package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.exception.EmailAlreadyExistsException;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
        assertEmailDoesNotExist(user.getEmail());
        return repository.save(user);
    }

    public void update(User userToUpdate){
        assertUserExists(userToUpdate.getId());
        assertEmailDoesNotExist(userToUpdate.getEmail(), userToUpdate.getId());
        repository.save(userToUpdate);
    }

    public void delete(Long id){
        var expectedUser = findByIdOrNotFoundException(id);
        repository.delete(expectedUser);
    }

    public void assertUserExists(Long id){
        findByIdOrNotFoundException(id);
    }

    public void assertEmailDoesNotExist(String email){
        repository.findByEmail(email).
                ifPresent(this::throwEmailExistsException);
    }

    public void assertEmailDoesNotExist(String email, Long id){
        repository.findByEmailAndIdNot(email, id).
                ifPresent(this::throwEmailExistsException);
    }

    private void throwEmailExistsException(User user) {
        throw new EmailAlreadyExistsException("E-mail %s already exists".formatted(user.getEmail()));
//        throw new ResponseStatusException(BAD_REQUEST, "E-mail %s already exists".formatted(user.getEmail()));
    }
}
