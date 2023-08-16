package com.Practice.MyFirstProject.Repository;
import com.Practice.MyFirstProject.Entity.AirItLabs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiritLabsRepository extends JpaRepository <AirItLabs,Integer>{
    // no code at all
}
