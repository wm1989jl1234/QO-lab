package com.fdmgroup.qolab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.qolab.model.Trainee;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer>{
	@Query("select t from Trainee t where t.name = :name and t.room = :roomId")
	public Trainee findByNameAndRoomId(String name, String roomId);
	
	@Query("select t from Trainee t where t.id = :id")
	public Trainee findById(int id);
	
	@Query("select t from Trainee t where t.room.roomId = :roomId")
	public List<Trainee> findByRoomId(String roomId);
}
