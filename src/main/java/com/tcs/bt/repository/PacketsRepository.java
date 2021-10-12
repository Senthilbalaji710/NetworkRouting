package com.tcs.bt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.bt.model.PacketTable;




@Repository
public interface PacketsRepository extends JpaRepository<PacketTable, Integer>{
	List<PacketTable> findByDestination(String destination);
}
