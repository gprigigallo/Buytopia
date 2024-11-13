package com.apuliadigitalmaker.buytopia.user;

import com.apuliadigitalmaker.buytopia.dto.MapperRepository;
import com.apuliadigitalmaker.buytopia.dto.UserDto;
import com.apuliadigitalmaker.buytopia.order.Order;
import com.apuliadigitalmaker.buytopia.order.OrderRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private static final String notFoundMessage = "User not found";
    private static final String usernameAlreadyExistsMessage = "Username already exists";
    private static final String emailAlreadyExistsMessage = "Email already exists";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;


    // Ottieni tutti
    public List<User> getAllUsers() {

        return userRepository.findAllNotDeleted();
    }

    // Ottieni da Id
    public User getUserById(int id) {

        return userRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));
    }

    
    // Crea Utente
    public User createUser(User user) {

        for (User testUser : userRepository.findAll()){
            if (testUser.getUsername().equals(user.getUsername())){
                throw new EntityExistsException(usernameAlreadyExistsMessage);
            }
            if (testUser.getEmail().equals(user.getEmail())){
                throw new EntityExistsException(emailAlreadyExistsMessage);
            }
        }
        return userRepository.save(user);
    }

    
    // Aggiorna Utente
    @Transactional
    public User updateUser(Integer id, Map<String, Object> update) {
        
        // Trova Utente da Id
        Optional<User> optionalUser = userRepository.findByIdNotDeleted(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException(notFoundMessage);
        }

        User user = optionalUser.get();

        // Esegui un foreach e trova corrispondenze con la Map Update
        // Nel caso in cui dovesse trovarle aggiorna il valore
        update.forEach((key, value) -> {
            switch (key) {
                case "username":
                    user.setUsername((String) value);
                case "first_name":
                    user.setFirstName((String) value);
                    break;
                case "last_name":
                    user.setLastName((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "password":
                    user.setPassword((String) value);
                    break;
                case "phone_number":
                    user.setPhone((String) value);
                    break;
                case "billing_address":
                    user.setBillingAddress((String) value);
                    break;
            }
        });
            return userRepository.save(user);
        }

     // Elimina Utente
    @Transactional
    public void deleteUser(int id) {

        // Cerco l'utente da eliminare
        User user = userRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

        // Aggiungo una data a deletedAt
        user.softDelete();

        // Salvo la modifica
        userRepository.save(user);


    }

    @Transactional
    public void hardDeleteUser(int id) {
        // Cerco l'utente da eliminare
        User user = userRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

        // Elimino l'utente
        userRepository.delete(user);
    }

    // Cerco l'utente da query
    public List<User> searchUser(String search) {
        return userRepository.findByUsernameStartsWithIgnoreCaseAndDeletedAtIsNull(search);
    }


    private final MapperRepository mapperRepository = new MapperRepository();

    public UserDto getOrderByUserEmail (String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));
        return mapperRepository.toDTO(user);

    }
        
        

        
}
