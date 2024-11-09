package com.gk.hotel.services;

import com.gk.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel create(Hotel hotel);

    //get all hotels
    List<Hotel> getAllHotels();

    //get single hotel
    Hotel getHotel(String id);

    //TODO: Delete
    //TODO: Update
}
