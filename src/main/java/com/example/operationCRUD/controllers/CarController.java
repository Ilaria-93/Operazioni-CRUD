package com.example.operationCRUD.controllers;

import com.example.operationCRUD.entities.Car;
import com.example.operationCRUD.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    //crea nuova car
    @PostMapping
    public List<Car> createCars(@RequestBody List<Car> cars) {
        return carRepository.saveAll(cars);
    }

    //restituisce la lista di tutte le Car
    @GetMapping
    public List<Car> allCars() {
        return carRepository.findAll();
    }

    //restituisce una singola Car - se id non è presente in db (usa existsById()), restituisce Car vuota
    @GetMapping("/id")
    public ResponseEntity<Car> carById(@RequestParam Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (carRepository.existsById(id)) {
            return ResponseEntity.ok(car.get());
        } else {
            return ResponseEntity.of(Optional.of(new Car()));
        }
    }

    //aggiorna type della Car specifica, identificata da id e passando query param - se id non è presente in db (usa existsById()), restituisce Car vuota
    @PutMapping("/id")
    public ResponseEntity<Car> updateCarId(@RequestParam Long id, @RequestParam String type) {
        Car car = carRepository.findById(id).get();
        if (carRepository.existsById(id)) {
            car.setType(type);
            return ResponseEntity.ok(carRepository.save(car));
        } else {
            return ResponseEntity.of(Optional.of(new Car()));
        }
    }

    //cancella la Car specifica - se non presente, la risposta deve avere Conflict HTTP status
    @DeleteMapping("/id")
    public ResponseEntity<Car> deleteCar(@RequestParam Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    //cancella tutte le Cars in db
    @DeleteMapping
    public void deleteAllCars() {
        carRepository.deleteAll();
    }
}
