package com.prueba.bikemicroservice.controller;

import java.util.List;

import com.prueba.bikemicroservice.entity.Bike;
import com.prueba.bikemicroservice.service.BikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bike")
public class BikeController {
    
    @Autowired
    private BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> getAll(){
        List<Bike>  bikes = bikeService.getAll();
        if( bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok( bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBike(@PathVariable(name = "id") int id){
        Bike bike = bikeService.getBikeById(id);
        if(bike == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<Bike> save(@RequestBody Bike  bike){
        Bike newBike = bikeService.save( bike);
        return ResponseEntity.ok(newBike);
    }

    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable(name = "id") int id){
        List<Bike> bikes = bikeService.getByUserId(id);
        if(bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }
}
