package com.example.excelProj.Repository;

import com.example.excelProj.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    @Query(value = "select * from event order by date DESC",nativeQuery = true)
    public List<Event> findAllEvents();
}
