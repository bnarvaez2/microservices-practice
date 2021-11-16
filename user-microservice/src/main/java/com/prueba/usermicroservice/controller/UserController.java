package com.prueba.usermicroservice.controller;

import java.util.List;
import java.util.Map;

import com.prueba.usermicroservice.entity.User;
import com.prueba.usermicroservice.model.Bike;
import com.prueba.usermicroservice.model.Car;
import com.prueba.usermicroservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

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

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable(name = "userId") int userId){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @GetMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable(name = "userId") int userId, @RequestBody Car car){
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable(name = "userId") int userId){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @GetMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable(name = "userId") int userId, @RequestBody Bike bike){
        Bike newBike = userService.saveBike(userId, bike);
        return ResponseEntity.ok(newBike);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable(name = "userId") int userId){
        Map<String, Object> result = userService.getVehiculesUser(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable(name = "userId") int userId, RuntimeException e){
        return new ResponseEntity("El usuario" + userId + "tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Car>> fallBackSaveCar(@PathVariable(name = "userId") int userId, @RequestBody Car car, RuntimeException e){
        return new ResponseEntity("El usuario" + userId + "no tiene dinero para carros", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable(name = "userId") int userId, RuntimeException e){
        return new ResponseEntity("El usuario" + userId + "tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackSaveBike(@PathVariable(name = "userId") int userId, @RequestBody Bike bike, RuntimeException e){
        return new ResponseEntity("El usuario" + userId + " no tiene dinero para motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable(name = "userId") int userId, RuntimeException e){
        return new ResponseEntity("El usuario" + userId + "tiene los vehiculos en el taller", HttpStatus.OK);
    }
}
