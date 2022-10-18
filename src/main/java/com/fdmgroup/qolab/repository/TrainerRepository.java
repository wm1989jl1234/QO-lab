package com.fdmgroup.qolab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.qolab.model.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
	@Query("select t from Trainer t where t.username = :username")
	public Trainer findByUserName(String username);
}
