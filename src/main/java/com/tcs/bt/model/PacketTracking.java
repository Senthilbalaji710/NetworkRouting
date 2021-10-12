package com.tcs.bt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="packettracking")
public class PacketTracking 
{
	 @Id
	  @Column(name = "id")
	  @GeneratedValue(strategy = GenerationType.AUTO)
     private int id;
	 @Column(name="source")
     private String source;
	 @Column(name="destination")
     private String destination;
	 @Column(name="nexthop")
     private String nextHop;
	 public PacketTracking() {
		super();
	}
	public PacketTracking( String source, String destination, String nextHop, int time, char data) {
		super();
		
		this.source = source;
		this.destination = destination;
		this.nextHop = nextHop;
		this.time = time;
		this.data = data;
	}
	@Override
	public String toString() {
		return "PacketTracking [id=" + id + ", source=" + source + ", destination=" + destination + ", nextHop="
				+ nextHop + ", time=" + time + ", data=" + data + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getNextHop() {
		return nextHop;
	}
	public void setNextHop(String nextHop) {
		this.nextHop = nextHop;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public char getData() {
		return data;
	}
	public void setData(char data) {
		this.data = data;
	}
	@Column(name="time")
     private int time;
	 @Column(name="data")
     private char data;
}
