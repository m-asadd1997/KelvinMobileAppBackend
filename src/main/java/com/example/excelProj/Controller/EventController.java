package com.example.excelProj.Controller;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.EventDto;
import com.example.excelProj.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping("/post-event")
    public ApiResponse postEvent(@RequestBody EventDto eventDto){
        return eventService.postEvent(eventDto);
    }

    @GetMapping("/get-all-events")
    public ApiResponse getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/get-event/{id}")
    public ApiResponse getEventById(@PathVariable("id") Long id){
        return eventService.getEventById(id);
    }

    @DeleteMapping("/delete-event/{id}")
    public ApiResponse deleteEventById(@PathVariable("id") Long id){
        return eventService.deleteEvent(id);
    }

    @PutMapping("/update-event/{id}")
    public ApiResponse updateEvent(@PathVariable("id") Long id,@RequestBody EventDto eventDto){
        return eventService.updateEvent(id,eventDto);
    }


}
