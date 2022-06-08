package com.myhotels.guestservice.service;

import com.myhotels.guestservice.dto.CreditCardRequest;
import com.myhotels.guestservice.dto.GuestRequest;
import com.myhotels.guestservice.dto.StayInfoRequest;
import com.myhotels.guestservice.entities.CreditCard;
import com.myhotels.guestservice.entities.Guest;
import com.myhotels.guestservice.entities.StayInfo;
import com.myhotels.guestservice.exceptions.GuestCreditCardNotFoundException;
import com.myhotels.guestservice.exceptions.GuestNotFoundException;
import com.myhotels.guestservice.exceptions.GuestStayInfoNotFoundException;
import com.myhotels.guestservice.repository.CreditCardRepository;
import com.myhotels.guestservice.repository.GuestRepository;
import com.myhotels.guestservice.repository.StayInfoRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private StayInfoRepository stayInfoRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    public List<Guest> getAllGuestProfiles() {
        log.info("Inside get all guests profiles service");
        return guestRepository.findAll();
    }

    public Guest getGuestProfile(Integer guestId) throws GuestNotFoundException {
        log.info("Inside get all guests profiles service with guest id: " + guestId);
        return guestRepository.findByGuestId(guestId).orElseThrow(() -> new GuestNotFoundException("Guest Not found " +
                "with guest id: " + guestId));
    }

    public StayInfo getGuestStayInfo(Integer guestId, Integer stayId) throws GuestNotFoundException,
            GuestStayInfoNotFoundException {
        log.info(String.format("Inside get stay info service with guest id: %s, stay id: %s", guestId, stayId));
        Optional<Guest> guest = guestRepository.findByGuestId(guestId);
        if (!guest.isPresent()) {
            throw new GuestNotFoundException(String.format("Guest with guest id: %s is not found", guestId));
        }
        Optional<StayInfo> stayInfoDB =
                guest.get().getStayHistory().stream().filter(stayInfo -> Objects.equals(stayInfo.getStayId(), stayId)).findFirst();
        if (!stayInfoDB.isPresent()) {
            throw new GuestStayInfoNotFoundException(String.format("Guest with guest id: %s and stay id: %s not " +
                    "found", guestId, stayId));
        }
        return stayInfoDB.get();
    }

    public CreditCard getGuestCreditCard(Integer guestId, Long cardId) throws GuestNotFoundException,
            GuestCreditCardNotFoundException {
        log.info(String.format("Inside get credit card info service with guest id: %s, cardId id: %s", guestId,
                cardId));
        Optional<Guest> guest = guestRepository.findByGuestId(guestId);
        if (!guest.isPresent()) {
            throw new GuestNotFoundException(String.format("Guest with guest id: %s is not found", guestId));
        }
        Optional<CreditCard> cardDB =
                guest.get().getCreditCards().stream().filter(card -> Objects.equals(card.getCardNumber(), cardId)).findFirst();
        if (!cardDB.isPresent()) {
            throw new GuestCreditCardNotFoundException(String.format("Guest with guest id: %s and credit card id: %s " +
                    "not found", guestId, cardId));
        }
        return cardDB.get();
    }

    @Transactional
    public StayInfo saveGuestStayInfo(Integer guestId, StayInfoRequest stayInfoRequest) throws GuestNotFoundException {
        log.info(String.format("Inside save stay information service with guest id: %s and stay info request: %s",
                guestId,
                stayInfoRequest));
        Optional<Guest> guest = guestRepository.findByGuestId(guestId);
        if (!guest.isPresent()) {
            throw new GuestNotFoundException(String.format("Guest with guest id: %s is not found", guestId));
        }
        StayInfo stayInfo = StayInfo.builder().
                hotelId(stayInfoRequest.getHotelId()).
                roomNumbers(stayInfoRequest.getRoomNumbers()).
                guest(Guest.builder().guestId(guest.get().getGuestId()).build()).
                build();
        return stayInfoRepository.save(stayInfo);
    }


    @Transactional
    public CreditCard saveCreditCardInfo(Integer guestId, CreditCardRequest creditcardRequest) throws GuestNotFoundException {
        log.info(String.format("Inside save credit card information service with guest id: %s and credit card info " +
                "request: %s", guestId, creditcardRequest));
        Optional<Guest> guest = guestRepository.findByGuestId(guestId);
        if (!guest.isPresent()) {
            throw new GuestNotFoundException(String.format("Guest with guest id: %s is not found", guestId));
        }
        CreditCard creditcard = CreditCard.builder().
                cvv(creditcardRequest.getCvv()).
                expiryMonth(creditcardRequest.getExpiryMonth()).
                expiryYear(creditcardRequest.getExpiryYear()).
                guest(Guest.builder().guestId(guest.get().getGuestId()).build()).
                build();

        return creditCardRepository.save(creditcard);
    }

    @Transactional
    public ResponseEntity<String> deleteGuestStayInfo(Integer guestId, Integer stayId) throws GuestNotFoundException, GuestStayInfoNotFoundException {
        log.info(String.format("Inside delete stay information service with guest id: %s and stay id: %s", guestId,
                stayId));
        Optional<Guest> guest = guestRepository.findByGuestId(guestId);
        if (!guest.isPresent()) {
            throw new GuestNotFoundException(String.format("Guest with guest id: %s is not found", guestId));
        }
        Optional<StayInfo> stayDB =
                guest.get().getStayHistory().stream().filter(stay -> Objects.equals(stay.getStayId(), stayId)).findFirst();

        if(!stayDB.isPresent()) {
            throw new GuestStayInfoNotFoundException(String.format("Stay information not found with guest id: %s and " +
                    "stay id: ", guestId, stayId ));
        }
        stayInfoRepository.deleteByStayId(stayId);
        return ResponseEntity.ok(String.format("Stay information with guest id: %s and stay id: %s is deleted " +
                "successfully", guestId, stayId));
    }

    public ResponseEntity<String> deleteGuestCreditCardInfo(Integer guestId, Long cardId) throws GuestNotFoundException,
            GuestCreditCardNotFoundException {
        log.info(String.format("Inside delete credit card information service with guest id: %s and card id: %s",
                guestId, cardId));
        Optional<Guest> guest = guestRepository.findByGuestId(guestId);
        if (!guest.isPresent()) {
            throw new GuestNotFoundException(String.format("Guest with guest id: %s is not found", guestId));
        }
        Optional<CreditCard> cardDB =
                guest.get().getCreditCards().stream().filter(stay -> Objects.equals(stay.getCardNumber(), cardId)).findFirst();

        if(!cardDB.isPresent()) {
            throw new GuestCreditCardNotFoundException(String.format("Stay information is not found with guest id: %s" +
                    " and " +
                    "credit card id: ", guestId, cardId));
        }
        creditCardRepository.deleteByCardNumber(cardId);
        return ResponseEntity.ok(String.format("Stay information with guest id: %s and credit card id: %s is deleted " +
                "successfully", guestId, cardId));
    }

    public Guest saveGuestProfile(GuestRequest guestRequest) {
        log.info("Inside save guest profile service with request body: " + guestRequest);
        Set<StayInfo> stayInfo = new HashSet<>();
        for(StayInfoRequest stay: guestRequest.getStayHistory()) {
            StayInfo info = StayInfo.builder().
                    hotelId(stay.getHotelId()).
                    roomNumbers(stay.getRoomNumbers()).
                    build();
            stayInfo.add(info);
        }
        Set<CreditCard> cardInfo = new HashSet<>();
        for(CreditCardRequest card : guestRequest.getCreditCards()) {
            CreditCard c = CreditCard.builder().
                    cvv(card.getCvv()).
                    expiryMonth(card.getExpiryMonth()).
                    expiryYear(card.getExpiryYear()).
                    build();
            cardInfo.add(c);
        }
        Guest guest = Guest.builder().
                guestName(guestRequest.getGuestName()).
                guestEmail(guestRequest.getGuestEmail()).
                guestAddress(guestRequest.getGuestAddress()).
                guestPhoneNumber(guestRequest.getGuestPhoneNumber()).
                stayHistory(stayInfo).
                creditCards(cardInfo).
                build();
        return guestRepository.save(guest);
    }

    public Map<String, String> getUserNameAndPassword(Integer guestId) throws GuestNotFoundException {
        log.info(String.format("Inside get username and password service with guest id: %s ", guestId));
        Optional<Guest> guest = guestRepository.findByGuestId(guestId);
        if (!guest.isPresent()) {
            throw new GuestNotFoundException(String.format("Guest with guest id: %s is not found", guestId));
        }
        return Map.of("username", guest.get().getGuestName(), "password", guest.get().getGuestPassword());
    }
}