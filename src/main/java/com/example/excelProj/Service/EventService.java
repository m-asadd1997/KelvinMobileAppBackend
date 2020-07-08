package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.EventDto;
import com.example.excelProj.Model.Event;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.EventRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    public ApiResponse postEvent(EventDto eventDto){
        Optional<User> user1 = userDaoRepository.findById(eventDto.getUserId());
        if(user1.isPresent()){
            User user = user1.get();
            Event event = new Event();
            event.setDate(new Date());
            event.setDescription(eventDto.getDescription());
            event.setImage(eventDto.getImage());
            event.setUrl(eventDto.getUrl());
            event.setUser(user);
            return new ApiResponse(200,"Event posted",eventRepository.save(event));
        }
        return new ApiResponse(400,"User not found",null);
    }

    public ApiResponse getAllEvents(){
        List<Event> events = eventRepository.findAll();
        if(!events.isEmpty()){
            return new ApiResponse(200,"Events found",events);
        }
        return new ApiResponse(400,"No events present",null);
    }

    public ApiResponse getEventById(Long id){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            Event event1 = event.get();
            return new ApiResponse(200,"Event Found",event1);
        }
        return new ApiResponse(400,"Event not found",null);
    }

    public ApiResponse deleteEvent(Long id){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()) {
            eventRepository.deleteById(id);
            return new ApiResponse(200,"Event Deleted",null);
        }
        return new ApiResponse(400,"Event not found",null);
    }

    public ApiResponse updateEvent(Long id, EventDto eventDto){
        Optional<Event> event1 = eventRepository.findById(id);
        if(event1.isPresent()) {
            Event event = event1.get();
            event.setDate(new Date());
            event.setDescription(eventDto.getDescription());
            event.setImage(eventDto.getImage());
            event.setUrl(eventDto.getUrl());
            return new ApiResponse(200,"Event updated",eventRepository.save(event));
        }
        return new ApiResponse(400,"Event not found",null);

    }
}
