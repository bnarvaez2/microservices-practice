package com.prueba.bikemicroservice.service;

import java.util.List;

import com.prueba.bikemicroservice.entity.Bike;
import com.prueba.bikemicroservice.repository.BikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeService {

    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAll(){
        return bikeRepository.findAll();
    }

    public Bike getBikeById(int id){
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike save(Bike bike){
        Bike newBike = bikeRepository.save(bike);
        return newBike;
    }

    public List<Bike> getByUserId(int userid){
        return bikeRepository.findByUserId(userid);
    }
}
