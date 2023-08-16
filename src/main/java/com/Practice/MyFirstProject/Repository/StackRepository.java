package com.Practice.MyFirstProject.Repository;
import com.Practice.MyFirstProject.Entity.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StackRepository extends JpaRepository<Stack,Integer> {
    // no code at all
}
