package com.example.Consejo_financiero.controller;


import com.example.Consejo_financiero.entity.Users;
import com.example.Consejo_financiero.entity.UsersStatus;
import com.example.Consejo_financiero.repository.UsuerRepository;
import com.example.Consejo_financiero.services.UsersService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // permite ese origen
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsuerRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Optional<Users> user = usersService.searchByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users users) {
        Users newUser = usersService.registerUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name){
        Optional<Users> users = usersService.searchByNameUser(name);
        return users.isPresent() ? ResponseEntity.ok(users.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuer not found");
    }

    @GetMapping("/search/id/{userId}")
    public ResponseEntity<?> searchById(@PathVariable Long userId){
        Optional<Users> users = usersService.searchById(userId);
        return users.isPresent() ? ResponseEntity.ok(users.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserInformation(@PathVariable Long userId,@RequestBody Users users){
        try{
            Users updateUserInformation = new Users();
            updateUserInformation.setName(users.getName());
            updateUserInformation.setSurname(users.getSurname());
            updateUserInformation.setEmail(users.getEmail());
            updateUserInformation.setPassword(users.getPassword());
            updateUserInformation.setAge(users.getAge());
            updateUserInformation.setPhone(users.getPhone());

            Users usersBBDD = usersService.updateUserInformation(userId,updateUserInformation);
            return ResponseEntity.ok(updateUserInformation);
        }catch (Exception exception){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping("/status/{userId}")
    public  ResponseEntity<?> changeUserStatus(@PathVariable Long userId, @RequestBody UsersStatus usersStatus){
        try{
            Users usersStatusUpdate = usersService.changeUserStatus(userId,usersStatus);
            return ResponseEntity.ok(usersStatusUpdate);
        }catch (Exception exception){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/status/{usersStatus}")
    public ResponseEntity<List<Users>> productListByStatus(@PathVariable UsersStatus usersStatus){
        List<Users> users = usersService.getByStatus(usersStatus);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/balance-minimo")
    public  ResponseEntity<?>actualizarBalanceMinimo(
            @PathVariable Long userId,
            @RequestBody Map<String, Double> request){
        try{
            Double balanceMinimo = request.get("balanceMinimo");

            if (balanceMinimo == null){
                return ResponseEntity.badRequest().body("Balance minimo es requerido");
            }

            Users usuario = usuarioRepository.findById(userId)
                    .orElseThrow(()-> new RuntimeException("usuario no encontrado"));

            usuario.setBalanceMinimoAlerta(balanceMinimo);
            usuarioRepository.save(usuario);

            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    //Endpoint para obtener el balanace  minimo
    @GetMapping("/{userId}/balance-minimo")
    public ResponseEntity<?> obtenerBalanceMinimo(@PathVariable Long userId){
        try{
            Users usuario = usuarioRepository.findById(userId)
                    .orElseThrow(()->new RuntimeException("Usuario no encontrado"));

            Map<String, Double> response = new HashMap<>();
            response.put("BalanaceMinimo", usuario.getBalanceMinimoAlerta());

            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
