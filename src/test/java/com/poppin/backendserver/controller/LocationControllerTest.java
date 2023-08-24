package com.poppin.backendserver.controller;

import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.service.LocationService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class LocationControllerTest {

    @Autowired LocationService locationService;
    @Autowired LocationController locationController;

    /**
     * 테스트 위한 더미데이터
     */
    public void init(){
        for (int i = 0; i < 100; i++) {
            locationService.saveLocation(Location.builder()
                    .name("apple"+i)
                    .address("korea"+i)
                    .telephone("0101234"+i)
                    .closedDay("monday")
                    .openTime("10~21")
                    .latitude((long) (10+i))
                    .lontitude((long) (30+i))
                    .build());
        }
    }
    @Test
    public void LocationControllerTest() throws Exception {
        // given
        init();
        // when

        // then
    }

}