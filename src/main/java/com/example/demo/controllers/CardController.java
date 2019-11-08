package com.example.demo.controllers;

import com.example.demo.beans.Card;
import com.example.demo.beans.User;
import com.example.demo.exceptions.CardException;
import com.example.demo.exceptions.InsufficentFundsException;
import com.example.demo.exceptions.MachineException;
import com.example.demo.exceptions.UserException;
import com.example.demo.services.DrinkCardServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class CardController {
	@Autowired
	private DrinkCardServices drinkCardServices;

	@PostMapping("/card/register")
	public ResponseEntity<Card> createCard(@RequestParam String id, @RequestParam(required = false) User user) {
		Long userId = null;
		if (user != null) {
			userId = user.getId();
		}
		try {
			return new ResponseEntity<>(drinkCardServices.createCard(id, userId), HttpStatus.OK);
		} catch (UserException ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (CardException ex) {
			log.error("Issue with registering a card");
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/card/balance")
	public ResponseEntity<Long> getCardBalance(@RequestParam String id) {
		try {
			return new ResponseEntity<>(drinkCardServices.getBalance(id), HttpStatus.OK);
		} catch (CardException ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/card")
	public ResponseEntity<Card> getCard(@RequestParam String id) {
		try {
			return new ResponseEntity<>(drinkCardServices.getCard(id), HttpStatus.OK);
		} catch (CardException ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/card/load")
	public ResponseEntity<Card> loadVal(@RequestParam String id, @RequestParam Long amount, @RequestParam Long machineId) {
		try {
			return new ResponseEntity<>(drinkCardServices.addUnits(amount, id, machineId), HttpStatus.OK);
		} catch (CardException | MachineException ex) {
			log.error("Issue loading card " + id + " with " + amount + " on machine " + machineId);
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		}
	}

	@GetMapping("/card/volume")
	public ResponseEntity<Double> pointRatio(@RequestParam long amount) {
		return new ResponseEntity<>(drinkCardServices.getVolume(amount), HttpStatus.OK);
	}

	@GetMapping("/card/points")
	public ResponseEntity<Long> pointRatio(@RequestParam Double volume) {
		return new ResponseEntity<>(drinkCardServices.getPoints(volume), HttpStatus.OK);
	}

	@GetMapping("/card/canPurchase")
	public ResponseEntity<Boolean> haveEnoughPoints(@RequestParam String cardId, Double volume) {
		return new ResponseEntity<>(drinkCardServices.haveEnoughPoints(cardId, volume), HttpStatus.OK);
	}

	@PostMapping("/card/spend")
	public ResponseEntity<Card> spend(@RequestParam String id, @RequestParam long amount, @RequestParam Long machineId) {
		try {
			return new ResponseEntity<>(drinkCardServices.spendUnits(amount, id, machineId), HttpStatus.OK);
		} catch (CardException ex) {
			log.error("Issue with card validation", ex);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (InsufficentFundsException ex) {
			log.error("Unable to spend " + amount + " because there is not enough credits", ex);
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		} catch (MachineException ex) {
			log.error("Machine " + machineId + " is unable to update it's volume", ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
