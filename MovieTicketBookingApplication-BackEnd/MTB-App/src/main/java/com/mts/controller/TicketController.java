package com.mts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mts.exception.AccessForbiddenException;
import com.mts.exception.TicketNotFoundException;
import com.mts.model.Ticket;
import com.mts.service.TicketService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tickets")
public class TicketController {

	Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;
	@Autowired
	LoginController loginController;

	
	@PostMapping("/ticket")
	public ResponseEntity<Ticket> addATicket(@RequestBody Ticket ticket,@RequestParam(required = false) Integer bookingId)
			throws AccessForbiddenException, TicketNotFoundException {
		
		ticket = ticketService.addTicket(ticket,bookingId);
		/* System.out.println(ticket.getSeats()); */
		logger.info("-------Ticked Created Successfully---------");
		return new ResponseEntity<>(ticket, HttpStatus.CREATED);
	}

	
	@GetMapping("/findall")
	public ResponseEntity<List<Ticket>> viewTicketList() throws AccessForbiddenException, TicketNotFoundException {
		
		logger.info("-------List of Tickets Found Successfully---------");
		return ResponseEntity.ok(ticketService.viewTicketList());
	}

	
	@GetMapping("/{ticketId}")
	public Ticket findATicket(@PathVariable int ticketId) throws TicketNotFoundException, AccessForbiddenException {	
		Ticket t = null;
		try {
			t = ticketService.findTicket(ticketId);
			logger.info("-------Ticket with ticketId " + ticketId + " Foound Successfully---------");
		} catch (Exception e) {
			throw new TicketNotFoundException("Invalid Ticket ID");
		}
		return t;

	}

}