package com.example.demo.services;

import com.example.demo.beans.Card;
import com.example.demo.beans.User;
import com.example.demo.exceptions.CardException;
import com.example.demo.exceptions.InsufficentFundsException;
import com.example.demo.exceptions.MachineException;
import com.example.demo.repositories.CardRepository;
import com.example.demo.exceptions.UserException;
import com.example.demo.utils.CryptoUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Log4j2
public class DrinkCardServices {
	private final CardRepository cardRepository;
	private final UserServices userServices;
	private final MachineService machineService;
	@Value("${card.generator.password}")
	private String cardPassword;
	@Value("${pointvolumeratio}")
	private String pointsVolumeRatio;
	private Double pointsRatio;
	private Double volumeRatio;
	private static final String INVALID_CARD = "Invalid Card ";
	private static final String INVALID_CARD_NO = "Invalid Card Number ";
	private static final String INVALID_MACHINE_NO = "Invalid machine id ";

	@Autowired
	public DrinkCardServices(CardRepository cardRepository, UserServices userServices, MachineService machineService) {
		this.cardRepository = cardRepository;
		this.userServices = userServices;
		this.machineService = machineService;
	}

	@PostConstruct
	public void parse() {
		String[] vals = pointsVolumeRatio.split(":");
		pointsRatio = Double.parseDouble(vals[0]);
		volumeRatio = Double.parseDouble(vals[1]);
	}

	public Card addUnits(Long units, String cardId, Long machineId) throws CardException, MachineException {
		// Spend the money
		if (cardId == null || cardId.isEmpty()) {
			throw new CardException(INVALID_CARD + cardId);
		}
		if (machineId == null || machineId == 0) {
			throw new MachineException(INVALID_MACHINE_NO);
		}
		log.info("Added " + units + " to " + cardId + " at machine " + machineId);
		Card card = cardRepository.findById(cardId).orElse(new Card().setId(cardId).setAmount(units));
		card.setAmount(card.getAmount() + units);
		card.setMaxVolume(getVolume(card.getAmount()));
		return cardRepository.save(card);
	}

	@Transactional
	public Card spendUnits(long units, String cardId, Long machineId) throws InsufficentFundsException, CardException, MachineException {
		// Spend the money
		if (cardId == null || cardId.isEmpty()) {
			throw new CardException(INVALID_CARD + cardId);
		}
		Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardException("Card not found"));
		if (card.getAmount() < units) {
			throw new InsufficentFundsException(card.getId() + " has insufficent funds (" + card.getAmount() + " < " + units + ")");
		}
		card.setAmount(card.getAmount() - units);
		Card savedCard = cardRepository.save(card);
		// Get how much volume is reduced by
		savedCard.setMaxVolume(getVolume(savedCard.getAmount()));
		// Reduce the remaining volume in the machine
		Double volume = getVolume(units);
		Double remaining = machineService.decrementVolume(machineId, volume);
		log.info("Card " + cardId + " spent " + units + " and now has " + card.getAmount() + " units. Machine " + machineId + " now has " + remaining);
		return savedCard;
	}

	@Transactional
	public Card createCard(String id, Long userId) throws UserException, CardException {
		if (userId != null && userId > 0) {
			User user = userServices.getUser(userId);
			if (user == null) {
				throw new UserException("Invalid user with ID: " + userId);
			}
		}
		if (!CryptoUtils.matches(cardPassword, id)) {
			throw new CardException(INVALID_CARD_NO + id);
		}
		Card card = new Card().setId(id).setAmount(0L).setUserId(userId).setMaxVolume(0.0);
		return cardRepository.save(card);
	}

	private String generateValidCardId() {
		return CryptoUtils.encode(cardPassword);
	}

	private boolean validCardNumber(String id) {
		return (CryptoUtils.matches(cardPassword, id));
	}

	public Long getBalance(String id) throws CardException {
		Card card = getCard(id);
		return card.getAmount();
	}

	public Card getCard(String id) throws CardException {
		if (id == null) {
			throw new CardException(INVALID_CARD + id);
		}
		if (!validCardNumber(id)) {
			throw new CardException(INVALID_CARD_NO + id);
		}
		Card card = cardRepository.findById(id).orElse(new Card().setId(id).setAmount(0L));
		Double volume = getVolume(card.getAmount());
		card.setMaxVolume(volume);
		return card;
	}

	public boolean haveEnoughPoints(String cardId, Double volume) {
		if (cardId == null || volume == null) {
			return false;
		}
		try {
			Long balance = getBalance(cardId);
			return haveEnoughPoints(balance, volume);
		} catch (CardException ex) {
			return false;
		}
	}

	private boolean haveEnoughPoints(Long cardBalance, Double volume) {
		if (volume == 0.0) {
			return true;
		}
		long points = getPoints(volume);
		return (cardBalance != null && cardBalance >= points);
	}

	public double getVolume(Long points) {
		return getVolume(points, pointsRatio, volumeRatio);
	}

	public long getPoints(Double amount) {
		return getPoints(amount, pointsRatio, volumeRatio);
	}

	static long getPoints(Double volume, Double pRatio, Double vRatio) {
		if (volume == null || volume == 0) {
			return 0L;
		}
		return (long) Math.ceil(volume * pRatio / vRatio);
	}

	static double getVolume(Long points, Double pRatio, Double vRatio) {
		if (points == null || points == 0) {
			return 0.0;
		}
		return points * vRatio / pRatio;
	}
}
