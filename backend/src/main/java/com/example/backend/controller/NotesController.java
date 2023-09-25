package com.example.backend.controller;

import com.example.backend.dto.WeatherAPI.WeatherDTO;
import com.example.backend.model.entity.WeatherNotes;
import com.example.backend.service.NotesService;
import com.example.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotesController {
    private final NotesService notesService;

    @GetMapping("/super/notes")
    public List<WeatherNotes> getAllNotes( ){
        return  notesService.getAllNotes();
    }
    @GetMapping("/super/{city}/notes")
    public List<WeatherNotes> noteForCity(@PathVariable String city){
        return  notesService.getAllCityNotes(city);
    }


    @GetMapping("/notes/{adminId}")
    public List<WeatherNotes> notesForAdmin( @PathVariable Integer adminId){
        return  notesService.getAllNotesToAdmin(adminId);
    }

    @PostMapping("/notes/{adminId}")
    public ResponseEntity<WeatherNotes> addWeatherNotes(@PathVariable Integer adminId ,
//            @PathParam("cityName") String cityName,

          @RequestBody WeatherNotes noteValue) {

        return ResponseEntity.ok(notesService.addWeatherNote( adminId , noteValue));
    }
//    @PathParam("cityName") String cityName,
    @PutMapping("/notes/{noteId}")
    public ResponseEntity<WeatherNotes> updatedWeatherNotes(
            @RequestBody WeatherNotes noteValue, @PathVariable Integer noteId) {

        return ResponseEntity.ok(notesService.updateWeatherNote(noteId, noteValue));
    }

//    @PathParam("cityName") String cityName
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<String> deleteWeatherNotes(@PathVariable Integer noteId) {
        notesService.deleteWeatherNote(noteId);
        return ResponseEntity.ok("Note with id " + noteId + " has been deleted ");
    }

}
