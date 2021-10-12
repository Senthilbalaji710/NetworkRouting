package com.tcs.bt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.bt.model.ROUTINGTABLE;


@Repository
public interface RoutingRepository extends JpaRepository<ROUTINGTABLE, Integer>{
      
	List<ROUTINGTABLE> findByDestination(String destination);
}
