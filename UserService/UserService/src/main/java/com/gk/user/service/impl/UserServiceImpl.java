package com.gk.user.service.impl;

import com.gk.user.entities.Hotel;
import com.gk.user.entities.Rating;
import com.gk.user.entities.User;
import com.gk.user.external.service.HotelService;
import com.gk.user.repositories.UserRepository;
import com.gk.user.exceptions.ResourceNotFoundException;
import com.gk.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveuser(User user) {
        //generate unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            // Fetch ratings for each user
            Rating[] ratingsOfUser = restTemplate.getForObject(
                    "http://RATING-SERVICE/ratings/users/" + user.getUserId(),
                    Rating[].class
            );
            logger.info("Ratings for user {}: {}", user.getUserId(), ratingsOfUser);

            List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

            // Enrich ratings with hotel details
            List<Rating> ratingList = ratings.stream().map(rating -> {
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);
                return rating;
            }).collect(Collectors.toList());

            user.setRatings(ratingList);
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public User getUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        //fetch rating of the above user from RATING SERVICE
        //http://localhost:8083/ratings/users/03012f73-31c4-4108-8881-18b4782fe334
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{} ", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to HOTEL SERVICE to get the hotel
            //http://localhost:8082/hotels/02f0c941-4a46-4035-b215-65343bdc5799
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
//            logger.info("response status code: {}", forEntity.getStatusCode());

            //below hotelService is being used by the help of "Feign Client"
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    @Override
    public User deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            User u = userRepository.findById(userId).get();
            userRepository.deleteById(userId);
            return u;
        }
        return null;
    }

    @Override
    public User updateUser(String userId, User user) {
        if (userRepository.existsById(userId)) {
            User u = userRepository.findById(userId).get();
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            u.setAbout(user.getAbout());
            return userRepository.save(u);
        }
        return null;
    }
}
