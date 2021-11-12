package com.prueba.usermicroservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prueba.usermicroservice.entity.User;
import com.prueba.usermicroservice.feignClient.BikeFeignClient;
import com.prueba.usermicroservice.feignClient.CarFeignClient;
import com.prueba.usermicroservice.model.Bike;
import com.prueba.usermicroservice.model.Car;
import com.prueba.usermicroservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    public List<User> getAlll(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        User newUser = userRepository.save(user);
        return newUser;
    }

    public Car saveCar(int userid, Car car){
        car.setUserId(userid);
        Car newCar = carFeignClient.save(car);
        return newCar;
    }

    public Bike saveBike(int userid, Bike bike){
        bike.setUserId(userid);
        Bike newBike = bikeFeignClient.save(bike);
        return newBike;
    }

    public Map<String, Object> getVehiculesUser(int userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            result.put("Mensaje", "No existe el usuario.");
            return result;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty()){
            result.put("Cars", "Este usuario no tiene carros.");
        }else{
            System.out.print("//////////////////////" + cars.size());
            result.put("Cars", cars);
        }
        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty()){
            result.put("Bikes", "Este usuario no tiene motos.");
        }else{
            System.out.print("//////////////////////" + bikes.size());
            result.put("Bikes", bikes);
        }
        return result;
    }
}
