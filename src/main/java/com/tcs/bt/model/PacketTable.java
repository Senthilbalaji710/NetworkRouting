package com.tcs.bt.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;



@Entity
@Table(name="packet")
public class PacketTable 
{
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int packetId;
  
  @Column(name="source")
  private String source;
  
  @Column(name="destination")
  private String destination;
  @Column(name="payload")
  private char payload;

public int getPacketId() {
	return packetId;
}

public void setPacketId(int packetId) {
	this.packetId = packetId;
}

public String getSource() {
	return source;
}

public void setSource(String source) {
	this.source = source;
}

public char getPayload() {
	return payload;
}

public void setPayload(char payload) {
	this.payload = payload;
}

@Override
public String toString() {
	return "PacketTable [packetId=" + packetId + ", source=" + source + ", destination=" + destination + ", payload="
			+ payload + "]";
}

public String getDestination() {
	return destination;
}

public void setDestination(String destination) {
	this.destination = destination;
}









public PacketTable(int packetId, String source, String destination, char payload) {
	super();
	this.packetId = packetId;
	this.source = source;
	this.destination = destination;
	this.payload = payload;
}

public PacketTable() {
	super();
}


  
  
}
