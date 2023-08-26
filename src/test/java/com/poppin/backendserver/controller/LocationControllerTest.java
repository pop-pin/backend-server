package com.poppin.backendserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poppin.backendserver.dto.LocationDTO;
import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.service.LocationService;
import com.poppin.backendserver.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService locationService;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void testSearchLocation() throws Exception {
        String name = "apple";
        Pageable pageable = PageRequest.of(0, 6);
        List<Location> locations = new ArrayList<Location>();

        for (int i = 0; i < 30; i++) {
            locations.add(Location.builder()
                    .name("apple" + i)
                    .address("korea" + i)
                    .telephone("0101234" + i)
                    .closedDay("monday")
                    .openTime("10~21")
                    .latitude((long) (10 + i))
                    .lontitude((long) (30 + i))
                    .build());
        }

        List<Location> pageContent = locations.subList(0, 6);
        Page<Location> mockPage = new PageImpl<>(pageContent, pageable, locations.size());
        when(locationService.searchLocation(name, pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/v1/locations/search?keyword={keyword}&page={page}&size={size}", "apple",0 , 6))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("apple0"))
                .andExpect(jsonPath("$.content[0].address").value("korea0"))
                .andExpect(jsonPath("$.content[0].telephone").value("01012340"))
                .andExpect(jsonPath("$.content[0].closedDay").value("monday"))
                .andExpect(jsonPath("$.content[0].openTime").value("10~21"))
                .andExpect(jsonPath("$.content[0].latitude").value(10))
                .andExpect(jsonPath("$.content[0].lontitude").value(30));;
    }

    @Test
    public void testGetLocation() throws Exception {
        Long locationId = 1L;
        Location mockLocation = Location.builder()
                .name("apple" )
                .address("korea" )
                .telephone("0101234" )
                .closedDay("monday")
                .openTime("10~21")
                .latitude((long) (10))
                .lontitude((long) (30))
                .build();

        when(locationService.getLocation(locationId)).thenReturn(Optional.of(mockLocation));

        mockMvc.perform(get("/v1/locations/{location_id}", locationId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockLocation.getName()))
                .andExpect(jsonPath("$.address").value(mockLocation.getAddress()))
                .andExpect(jsonPath("$.telephone").value(mockLocation.getTelephone()))
                .andExpect(jsonPath("$.closedDay").value(mockLocation.getClosedDay()))
                .andExpect(jsonPath("$.openTime").value(mockLocation.getOpenTime()))
                .andExpect(jsonPath("$.latitude").value(mockLocation.getLatitude()))
                .andExpect(jsonPath("$.lontitude").value(mockLocation.getLontitude()));
    }

    @Test
    public void testGetReviews() throws Exception {
        Long locationId = 1L;

        mockMvc.perform(get("/v1/locations/{location_id}/reviews", locationId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateLocation() throws Exception {
        LocationDTO locationDTO = LocationDTO.builder()
                .name("apple" )
                .address("korea" )
                .telephone("01012345678" )
                .closedDay("monday")
                .openTime("10~21")
                .latitude((long) (10))
                .lontitude((long) (30))
                .build();

        mockMvc.perform(post("/v1/locations/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateLocation() throws Exception {
        Long locationId = 1L;

        LocationDTO locationDTO = LocationDTO.builder()
                .telephone("01012347777" )
                .closedDay("Friday")
                .openTime("10~23")
                .build();

        mockMvc.perform(put("/v1/locations/{location_id}", locationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteLocation() throws Exception {
        Long locationId = 1L;

        mockMvc.perform(delete("/v1/locations/{location_id}", locationId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}