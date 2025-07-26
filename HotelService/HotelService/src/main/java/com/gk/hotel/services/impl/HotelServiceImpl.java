package com.gk.hotel.services.impl;

import com.gk.hotel.entities.Hotel;
import com.gk.hotel.exception.ResourceNotFoundException;
import com.gk.hotel.repositories.HotelRepository;
import com.gk.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hoel with given id is not found !! :" + id));
    }

    @Override
    public Hotel updateHotel(String id, Hotel hotel) {
        if (hotelRepository.existsById(id)) {
            Hotel h = hotelRepository.findById(id).get();
            h.setName(hotel.getName());
            h.setAbout(hotel.getAbout());
            h.setLocation(hotel.getLocation());
            return hotelRepository.save(h);
        }
        return null;
    }

    @Override
    public Hotel deleteHotel(String id) {
        if (hotelRepository.existsById(id)) {
            Hotel hotel = hotelRepository.findById(id).get();
            hotelRepository.deleteById(id);
            return hotel;
        }
        return null;
    }
}
