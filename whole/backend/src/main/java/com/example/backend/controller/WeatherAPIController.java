package com.example.backend.controller;

import com.example.backend.dto.WeatherAPI.WeatherDTO;
import com.example.backend.service.WeatherService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class WeatherAPIController {

    private final WeatherService weatherService;

    @GetMapping("/info/{cityName}")
    public ResponseEntity<WeatherDTO> getWeatherData(  @PathVariable String cityName) {
        return ResponseEntity.ok(weatherService.getCurrentWeather(cityName));
    }


}