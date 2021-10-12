package com.tcs.bt.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bt.exception.ResourceNotFoundException;
import com.tcs.bt.model.PacketTable;
import com.tcs.bt.model.PacketTracking;
import com.tcs.bt.model.ROUTINGTABLE;
import com.tcs.bt.repository.PacketTrackingRepository;
import com.tcs.bt.repository.PacketsRepository;
import com.tcs.bt.repository.RoutingRepository;

@RestController
@RequestMapping("/api/v1")
public class RoutingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoutingController.class);
	
	@Autowired
	private RoutingRepository routingRepository;
	
	@Autowired
	private PacketsRepository packetsRepository;

	
	@Autowired
	private PacketTrackingRepository ptrackRepository;
	
	@GetMapping("/routing")
	public List<ROUTINGTABLE> getAllRoutingTable() {
		LOGGER.info("Getting all the data of Routing Table");
		return routingRepository.findAll();
	}

	@GetMapping("/routing/{id}")
	public ResponseEntity<ROUTINGTABLE> getEmployeeById(@PathVariable(value = "id") Integer routeId)
			throws ResourceNotFoundException {
		LOGGER.debug(" Id for receiving data",routeId);
		ROUTINGTABLE routingtable = routingRepository.findById(routeId)
				.orElseThrow(() -> new ResourceNotFoundException("Routingnode not found for this id :: " + routeId));
		LOGGER.info("Destination:"+routingtable.getDestination());
		LOGGER.info("routeId:"+routingtable.getRouteId());
		LOGGER.info("nextHop:"+routingtable.getNextHop());
		LOGGER.info("LinkNode:"+routingtable.getLinkNode());
		return ResponseEntity.ok().body(routingtable);
	}
	
	@PostMapping("/routingInsert") 
	public ROUTINGTABLE createrRouting(@Valid @RequestBody ROUTINGTABLE routing) 
	{ 
		LOGGER.info("Inserting new routing data information");
		LOGGER.debug("Debug of Routing addition:"+routing);
		return routingRepository.save(routing); 
		
		
	}
	  
	@PutMapping("/routing/{id}")
	public ResponseEntity<ROUTINGTABLE> updateRouting(@PathVariable(value = "id") Integer routeId,
			@Valid @RequestBody ROUTINGTABLE routing) throws ResourceNotFoundException {
		ROUTINGTABLE routing1 = routingRepository.findById(routeId)
				.orElseThrow(() -> new ResourceNotFoundException("Routingnode not found for this id :: " + routeId));
        LOGGER.info("Updating the existing routing table");
        LOGGER.info("Getting Router Id:"+routeId);
		routingRepository.save(routing);
		LOGGER.debug("Updated id:"+routing.getRouteId());
		return ResponseEntity.ok(routing);
	}
	  
	@DeleteMapping("/routing/{id}")
	public Map<String, Boolean> deleteRouting(@PathVariable(value = "id") Integer routeId)
			throws ResourceNotFoundException {
		ROUTINGTABLE routing = routingRepository.findById(routeId)
				.orElseThrow(() -> new ResourceNotFoundException("RoutingId not found for this id :: " + routeId));
        LOGGER.info("Deleting particular routeId from routetable");
        LOGGER.info("Getting the id for deleting:"+routeId);
		routingRepository.delete(routing);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		LOGGER.info("Deleted Successfully");
		return response;
	}
	
	
	 
	@PostMapping("/PacketStore")
	public PacketTable createPackets(@Valid @RequestBody PacketTable packets)
	{
		LOGGER.info("Storing information of packets");
		return 	packetsRepository.save(packets);
	}
	
	@GetMapping("/PacketStoreList")
    public List<PacketTable> getPacketById()
    {
		LOGGER.info("Retrieving all data of Packets");
		//LOGGER.debug(packetsRepository);
        return packetsRepository.findAll();
    }
	
	@PutMapping("/updatePacketStore/{id}")
	public ResponseEntity<PacketTable> updatePackets(@PathVariable(value = "id") Integer packetId,
			@Valid @RequestBody PacketTable pTable) throws ResourceNotFoundException {
		PacketTable pTable1 = packetsRepository.findById(packetId)
				.orElseThrow(() -> new ResourceNotFoundException("Routingnode not found for this id :: " + packetId));
        LOGGER.info("Updating the packets details"+packetId);
		packetsRepository.save(pTable);
		return ResponseEntity.ok(pTable);
	}
	  
	@DeleteMapping("/deletePacketStore/{id}")
	public Map<String, Boolean> deletePacket(@PathVariable(value = "id") Integer packetId)
			throws ResourceNotFoundException {
		LOGGER.info("Deleting the packet details of Id:"+packetId);
		PacketTable packets = packetsRepository.findById(packetId)
				.orElseThrow(() -> new ResourceNotFoundException("RoutingId not found for this id :: " + packetId));

		packetsRepository.delete(packets);
		LOGGER.info("Packets Deleted.........");
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
   @GetMapping("/routing/init/{id}")
   public String rootHop(@PathVariable(value = "id") Integer packetId)throws ResourceNotFoundException{
	   {
		LOGGER.info("Initial Packet Setup");   
	   PacketTable packets=packetsRepository.findById(packetId).orElseThrow(() -> new ResourceNotFoundException("RoutingId not found for this id :: " + packetId));;
	   String destiny=packets.getDestination();
	   
		   
		   return "redirect:/routing/rootNextHop/"+destiny;
	   }
   }
   //root next hop like R1or R2
	@GetMapping("/routing/rootNextHop/{destination}")
	public String rootNextHop(@PathVariable(value="destination") String destination1)throws ResourceNotFoundException{
		   {
			   LOGGER.info("Finding the root nexthop");
			   //PacketTable packets=packetsRepository.findById(packetId).orElseThrow(() -> new ResourceNotFoundException("RoutingId not found for this id :: " + packetId));;
			  // String destiny=packets.getDestination();
	{
		ROUTINGTABLE routingtable1=new ROUTINGTABLE();
        
		List<ROUTINGTABLE> routingtable= routingRepository.findByDestination(destination1);
		//System.out.println(routingtable.getNextHop());
	    routingtable1=routingtable.get(0);
		return "redirect:/routing/transferpacket/" +routingtable1.getNextHop()+"/"+destination1;
	}
	   
	
		   }}
	//root nexthop
	@PostMapping("/routing/transferpacket/{nextHop}/{destination}")
	public @Valid String nextHop(@PathVariable(value="nextHop") String nextHop,@PathVariable(value="destination")String destination )throws ResourceNotFoundException{
		{
			PacketTracking packetTracking = new PacketTracking();
			Instant start = Instant.now();
			PacketTable packet1=new PacketTable();
			String source = "root";
			String destination1 =nextHop;
			List<PacketTable> packet=packetsRepository.findByDestination(destination);
			packet1=packet.get(0);
			char c=packet1.getPayload();
			//List<PacketTracking> list=new ArrayList<PacketTracking>();
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			int timeElapsed1=(int) timeElapsed.toMillis();
			//int time=(int) timeElasped;
			packetTracking.setSource(source);
			packetTracking.setDestination(destination);
			packetTracking.setNextHop(destination1);
			packetTracking.setTime(timeElapsed1);
			packetTracking.setData(c);
			ptrackRepository.save(packetTracking);
			return "redirect:/routing/"+destination1+"/"+destination;
		}
		
		
	}
	//Nexthop and finding link node
     @PostMapping("/routing/{nextHop}/{destination}")
     public @Valid String linkNode(@PathVariable (value="nextHop") String nextHop,@PathVariable(value="destination")String destination )throws ResourceNotFoundException{
    	 PacketTracking packetTracking = new PacketTracking();
    	 {
    		 LOGGER.info("Finding the next link node");
    		 ROUTINGTABLE routingtable = new ROUTINGTABLE();
    		 PacketTable ptTable=new PacketTable();
    		 List<ROUTINGTABLE> routingtable1= routingRepository.findByDestination(destination);
    		 routingtable = routingtable1.get(0);
    		 String linknode1=routingtable.getLinkNode();
    		 System.out.println("Linknode:"+linknode1);
    		 if(!linknode1.equals("NO"))
    		 {
    			 LOGGER.info("Sending packets to Link node...");
    			 Instant start = Instant.now();
    			 String linknode=routingtable.getLinkNode();
    			 String source = nextHop;
    			 List<PacketTable> packet=packetsRepository.findByDestination(destination);
    			 ptTable=packet.get(0);
    			char c=ptTable.getPayload();
    			Instant end = Instant.now();
    			Duration timeElapsed = Duration.between(start, end);
    			int timeElapsed1=(int) timeElapsed.toMillis();
    			packetTracking.setSource(source);
    			packetTracking.setDestination(destination);
    			packetTracking.setNextHop(linknode);
    			packetTracking.setTime(timeElapsed1);
    			packetTracking.setData(c);
    			ptrackRepository.save(packetTracking);
    			return"redirect:/routing1/"+linknode+"/"+destination;  		 }
    		 else
    		 {
    			 LOGGER.info("Sending pakets to destination if no link node is available");
    			 Instant start = Instant.now();
    			 String source=nextHop;
    			 List<PacketTable> packet=packetsRepository.findByDestination(destination);
    			 ptTable=packet.get(0);
    			char c=ptTable.getPayload();
    			Instant end = Instant.now();
    			Duration timeElapsed = Duration.between(start, end);
    			int timeElapsed1=(int) timeElapsed.toMillis();
    			packetTracking.setSource(source);
    			packetTracking.setDestination(destination);
    			packetTracking.setNextHop("NA" );
    			packetTracking.setTime(timeElapsed1);
    			packetTracking.setData(c);
    			ptrackRepository.save(packetTracking);
    			return "redirect:/packet/success";
    		 }
    		
    	 }
    	  
     }
     @GetMapping("/packet/success")
     public String packetSuccess()
     {
    	 
    	 return "Packet transfer is successful";
     }
     @PostMapping("/routing1/{nextHop}/{destination}")
     public String destinationHop(@PathVariable (value = "nextHop") String nextHop,
    		 @PathVariable (value ="destination") String destination)throws ResourceNotFoundException{
		PacketTable ptTable = new PacketTable();
		PacketTracking packetTracking = new PacketTracking();
    	 {
    		 LOGGER.info("Sending packets to Destination...");
    		 Instant start = Instant.now();
			 String linknode="N0";
			 String source = nextHop;
			 List<PacketTable> packet=packetsRepository.findByDestination(destination);
			 ptTable=packet.get(0);
			char c=ptTable.getPayload();
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			int timeElapsed1=(int) timeElapsed.toMillis();
			packetTracking.setSource(source);
			packetTracking.setDestination(destination);
			packetTracking.setNextHop(linknode);
			packetTracking.setTime(timeElapsed1);
			packetTracking.setData(c);
			LOGGER.info("Source:"+" "+source+" "+"destination"+" "+destination+" "+"nextHop"+" "+linknode+" "
					+"time:"+" "+timeElapsed1+"Data:"+" "+c);
			
			ptrackRepository.save(packetTracking);
    		return "redirect:/packet/success";
    	 }
    	
    	 }
}     
	
