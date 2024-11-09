package com.gk.hotel.controllers;

import com.gk.hotel.entities.Hotel;
import com.gk.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    //create
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

    //get all hotels
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    //get hotel by id
    @GetMapping("{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId){
        return ResponseEntity.ok(hotelService.getHotel(hotelId));
    }

    //TODO: Delete
    //TODO: Update
}
