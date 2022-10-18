package com.fdmgroup.qolab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.qolab.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
	@Query("select r from Room r where r.roomId = :roomId")
	public Room findRoomByKey(String roomId);
}
