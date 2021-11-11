package com.prueba.usermicroservice.feignClient;

import java.util.List;

import com.prueba.usermicroservice.model.Car;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "car-service", url = "http://localhost:8091")
@RequestMapping("/car")
public interface CarFeignClient {
    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/byuser/{userId}")
    List<Car> getCars(@PathVariable(name = "userId") int userId);
}
