package com.apuliadigitalmaker.buytopia.user;

import com.apuliadigitalmaker.buytopia.common.ResponseBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "List of Users Endpoint")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();

            if (users.isEmpty()) {
                return ResponseBuilder.notFound("Users not found");
            } else {
                return ResponseBuilder.success(users);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

        @GetMapping("/{id}")
        public ResponseEntity<?> getUser(@PathVariable int id) {
            try {
                return ResponseBuilder.success(userService.getUserById(id));
            } catch (EntityNotFoundException e) {
                return ResponseBuilder.notFound(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseBuilder.error();
            }
        }
            @PostMapping("/add")
            public ResponseEntity<?> addUser(@RequestBody User user) {
                try {
                    if (user.getId() != null) {
                        return ResponseBuilder.notFound("The Id must be empty");
                    }
                    if (user.getUsername() == null || user.getUsername().isEmpty()) {
                        return ResponseBuilder.notFound("Username is required");
                    }
                    if (user.getPassword() == null || user.getPassword().isEmpty()) {
                        return ResponseBuilder.notFound("Password is required");
                    }
                    if (user.getEmail() == null || user.getEmail().isEmpty()) {
                        return ResponseBuilder.notFound("Email is required");
                    }
                    if (user.getBillingAddress() == null || user.getBillingAddress().isEmpty()) {
                        return ResponseBuilder.notFound("Billing Address is required");
                    }


                    return ResponseBuilder.success(userService.createUser(user));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return ResponseBuilder.error();
                }
            }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody Map<String, Object> update) {
        try {
            return ResponseBuilder.success(userService.updateUser(id, update));
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {

        try {
            userService.deleteUser(id);
            return ResponseBuilder.deleted("User deleted successfully");
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/hardDelete/{id}")
    public ResponseEntity<?> hardDeleteUser(@PathVariable int id) {

        try {
            userService.hardDeleteUser(id);
            return ResponseBuilder.deleted("User deleted successfully");
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam String query) {

        if (query.length() < 3) {
            return ResponseBuilder.badRequest("Required at least 3 characters");
        }

        List<User> searchResults = userService.searchUser(query);
        if (searchResults.isEmpty()) {
            return ResponseBuilder.notFound("Search has no results");
        }

        return ResponseBuilder.searchResults(searchResults, searchResults.size());
    }


    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersByUserId(@RequestParam String email) {
        try {
            return ResponseBuilder.success(userService.getOrderByUserEmail(email));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }


}




