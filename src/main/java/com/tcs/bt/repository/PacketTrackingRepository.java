package com.tcs.bt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.bt.model.PacketTracking;

public interface PacketTrackingRepository extends JpaRepository<PacketTracking, Integer>{

	//String save(List<PacketTracking> list);

	
}
