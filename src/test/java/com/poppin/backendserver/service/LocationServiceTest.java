package com.poppin.backendserver.service;

import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.repository.LocationRepository;
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
class LocationServiceTest {
    @Autowired
    LocationService locationService;
    @Autowired
    LocationRepository locationRepository;

    @Test
    public void 가게생성() throws Exception {
        // given
        Location location = Location.builder()
                .name("kim")
                .address("경기")
                .telephone("010-1234-5678")
                .closedDay("화요일")
                .openTime("10시~20시")
                .latitude((long) 100.1)
                .lontitude((long) 101.2)
                .build();

        // when
        Long savedId = locationService.saveLocation(location);
        // then
        assertEquals(location, locationService.getLocation(savedId).get());
    }

    @Test
    public void 가게이름_중복확인() throws Exception {
        // given
        Location location1 = Location.builder()
                .name("kim")
                .address("경기")
                .telephone("010-1234-5678")
                .closedDay("화요일")
                .openTime("10시~20시")
                .latitude((long) 100.1)
                .lontitude((long) 101.2)
                .build();
        Location location2 = Location.builder()
                .name("kim")
                .address("경기")
                .telephone("010-1234-5678")
                .closedDay("화요일")
                .openTime("10시~20시")
                .latitude((long) 100.1)
                .lontitude((long) 101.2)
                .build();
        // when
        locationService.saveLocation(location1);
        try {
            locationService.saveLocation(location2);
        } catch (IllegalStateException e) {
            return;
        }
        // then
        fail("예외가 발생해야 합니다");
    }

    @Test
    public void 장소_업데이트() throws Exception {
        // given
        Location location1 = Location.builder()
                .name("kim")
                .address("경기")
                .telephone("010-1234-5678")
                .closedDay("화요일")
                .openTime("10시~20시")
                .latitude((long) 100.1)
                .lontitude((long) 101.2)
                .build();

        Location location2 = Location.builder()
                .name("kim")
                .address("경기")
                .telephone("010-4321-5678")
                .closedDay("수요일")
                .openTime("10시~21시")
                .latitude((long) 100.1)
                .lontitude((long) 101.2)
                .build();
        // when
        Long savedId = locationService.saveLocation(location1);

        // then
        try {
            locationService.updateLocation(savedId, location2);
        } catch (IllegalStateException e) {
            fail("업데이트 실패");
        }
    }

    @Test
    public void 장소_삭제() throws Exception {
        // given
        Location location1 = Location.builder()
                .name("kim")
                .address("경기")
                .telephone("010-1234-5678")
                .closedDay("화요일")
                .openTime("10시~20시")
                .latitude((long) 100.1)
                .lontitude((long) 101.2)
                .build();
        // when
        Long savedId = locationService.saveLocation(location1);
        locationService.deleteLocation(savedId);

        // then
        if (!locationService.getLocation(savedId).isEmpty()) {
            fail("삭제 실패");
        }
    }


}