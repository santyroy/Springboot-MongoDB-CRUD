package org.roy.springbootmongodbdemo.seeder;

import org.roy.springbootmongodbdemo.model.Address;
import org.roy.springbootmongodbdemo.model.Hotel;
import org.roy.springbootmongodbdemo.model.Review;
import org.roy.springbootmongodbdemo.repo.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private HotelRepository hotelRepository;

    public DbSeeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Hotel marriot = new Hotel("Marriot", 130,
                new Address("Paris", "France"),
                Arrays.asList(new Review("John", 8, false),
                        new Review("Marry", 7, true)));

        Hotel ibis = new Hotel("Ibis", 90,
                new Address("Bucharest", "Romania"),
                Arrays.asList(new Review("Teddy", 9, true)));

        Hotel sofitel = new Hotel("Sofitel", 200,
                new Address("Rome", "Italy"),
                new ArrayList<>());

        // drop all hotels
        this.hotelRepository.deleteAll();

        // add our hotels to database
        List<Hotel> hotels = Arrays.asList(marriot, ibis, sofitel);
        this.hotelRepository.saveAll(hotels);
    }
}
