package com.prueba.carmicroservice.service;

import java.util.List;

import com.prueba.carmicroservice.entity.Car;
import com.prueba.carmicroservice.repository.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car getCarById(int id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car){
        Car newCar = carRepository.save(car);
        return newCar;
    }

    public List<Car> getByUserId(int userid){
        return carRepository.findByUserId(userid);
    }
}
