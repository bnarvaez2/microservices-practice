package com.prueba.usermicroservice.controller;

import java.util.List;
import java.util.Map;

import com.prueba.usermicroservice.entity.User;
import com.prueba.usermicroservice.model.Bike;
import com.prueba.usermicroservice.model.Car;
import com.prueba.usermicroservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAlll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") int id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        User newUser = userService.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable(name = "userId") int userId, @RequestBody Car car){
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @GetMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable(name = "userId") int userId, @RequestBody Bike bike){
        Bike newBike = userService.saveBike(userId, bike);
        return ResponseEntity.ok(newBike);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable(name = "userId") int userId){
        Map<String, Object> result = userService.getVehiculesUser(userId);
        return ResponseEntity.ok(result);
    }
}
