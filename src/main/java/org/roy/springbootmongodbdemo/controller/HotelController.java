package org.roy.springbootmongodbdemo.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.roy.springbootmongodbdemo.model.Hotel;
import org.roy.springbootmongodbdemo.model.QHotel;
import org.roy.springbootmongodbdemo.repo.HotelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/all")
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable("id") String id) {
        return hotelRepository.findById(id).get();
    }

    @GetMapping("/price/{price}")
    public List<Hotel> getByPricePerNight(@PathVariable("price") int price) {
        return hotelRepository.findByPricePerNightLessThan(price);
    }

    @GetMapping("/address/city/{city}")
    public List<Hotel> getByCity(@PathVariable("city") String city) {
        return hotelRepository.findByCity(city);
    }

    @GetMapping("/address/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country) {
        // create a query class (QHotel)
        QHotel qHotel = new QHotel("hotel");

        // using the query class we can create the filters
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);

        // we can then pass the filters to the findAll() method
        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll(filterByCountry);

        return hotels;
    }

    @GetMapping("/recommended")
    public List<Hotel> getRecommended() {
        final int maxPrice = 100;
        final int minRating = 7;

        // create a query class (QHotel)
        QHotel qHotel = new QHotel("hotel");

        // using the query class we can create the filters
        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);

        // we can then pass the filters to the findAll() method
        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll(filterByPrice.and(filterByRating));
        return hotels;
    }

    @PostMapping
    public void insert(@RequestBody Hotel hotel) {
        hotelRepository.insert(hotel);
    }

    @PutMapping
    public void update(@RequestBody Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        Hotel hotel = null;
        if (hotelRepository.findById(id).isPresent()) {
            hotel = hotelRepository.findById(id).get();
            hotelRepository.delete(hotel);
        }
    }
}
