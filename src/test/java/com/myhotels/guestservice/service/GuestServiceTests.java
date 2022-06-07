package com.myhotels.guestservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.myhotels.guestservice.dto.CreditCardRequest;
import com.myhotels.guestservice.dto.GuestRequest;
import com.myhotels.guestservice.dto.StayInfoRequest;
import com.myhotels.guestservice.entities.CardType;
import com.myhotels.guestservice.entities.CreditCard;
import com.myhotels.guestservice.entities.Guest;
import com.myhotels.guestservice.entities.StayInfo;
import com.myhotels.guestservice.exceptions.GuestCreditCardNotFoundException;
import com.myhotels.guestservice.exceptions.GuestNotFoundException;
import com.myhotels.guestservice.exceptions.GuestStayInfoNotFoundException;
import com.myhotels.guestservice.repository.CreditCardRepository;
import com.myhotels.guestservice.repository.GuestRepository;
import com.myhotels.guestservice.repository.StayInfoRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({GuestService.class})
@ExtendWith({SpringExtension.class})
public class GuestServiceTests extends Exception {

    @Autowired
    private GuestService guestService;

    @MockBean
    private GuestRepository guestRepository;
    @MockBean
    private StayInfoRepository stayInfoRepository;
    @MockBean
    private CreditCardRepository creditCardRepository;

    @Test
    void testGetAllGuestsProfiles() {
        when(guestRepository.findAll()).thenReturn(List.of(buildGuest()));
        List<Guest> allGuestProfiles = guestService.getAllGuestProfiles();
        assertNotEquals(Collections.emptyList(), allGuestProfiles);
    }

    @Test
    void testGetGuestProfile() throws GuestNotFoundException {
        Guest guest = buildGuest();
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(guest));
        assertNotNull(guestService.getGuestProfile(500));
    }

    @Test
    void testGetGuestProfileWhenNoGuestFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.getGuestProfile(500));
    }

    @Test
    void testGetGuestStayInfo() throws GuestStayInfoNotFoundException, GuestNotFoundException {
        Guest guest = buildGuest();
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(guest));
        StayInfo guestStayInfo = guestService.getGuestStayInfo(500, 5000);
        assertNotNull(guestStayInfo);
    }

    @Test
    void testGetGuestStayInfoWhenNoGuestFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.getGuestStayInfo(500, 5000));
    }

    @Test
    void testGetGuestStayInfoWhenNoGuestStayInfoFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuestWithEmptyStayInfo()));
        assertThrows(GuestStayInfoNotFoundException.class, () -> guestService.getGuestStayInfo(500, 5000));
    }

    @Test
    void testGetGuestCreditCardInfo() throws GuestCreditCardNotFoundException, GuestNotFoundException {
        Guest guest = buildGuest();
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(guest));
        CreditCard guestStayInfo = guestService.getGuestCreditCard(500, 500050005000L);
        assertNotNull(guestStayInfo);
    }

    @Test
    void testGetGuestCreditCardInfoWhenNoGuestFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.getGuestCreditCard(500, 500050005000L));
    }

    @Test
    void testGetGuestCreditCardInfoWhenNoCreditCardInfoFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuestWithEmptyCreditCardInfo()));
        assertThrows(GuestCreditCardNotFoundException.class, () -> guestService.getGuestCreditCard(500, 500050005000L));
    }

    @Test
    void testSaveGuestStayInfo() throws GuestNotFoundException {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuest()));
        when(stayInfoRepository.save(any())).thenReturn(buildStayInfo());
        assertNotNull(guestService.saveGuestStayInfo(500, buildStayInfoRequest()));
    }

    @Test
    void testSaveGuestStayInfoWhenNoGuestFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.saveGuestStayInfo(500, buildStayInfoRequest()));
    }

    @Test
    void testSaveGuestCreationInfo() throws GuestNotFoundException {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuest()));
        when(creditCardRepository.save(any())).thenReturn(buildCreditCard());
        assertNotNull(guestService.saveCreditCardInfo(500, buildCreditCardRequest()));
    }

    @Test
    void testSaveCreditCardInfoWhenNoGuestFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.saveCreditCardInfo(500,
                buildCreditCardRequest()));
    }

    @Test
    void testDeleteStayInfo() throws GuestStayInfoNotFoundException, GuestNotFoundException {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuest()));
        assertNotNull(guestService.deleteGuestStayInfo(500, 5000));
    }

    @Test
    void testDeleteStayInfoWhenNoGuestFound()  {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.deleteGuestStayInfo(500, 5000));
    }

    @Test
    void testDeleteStayInfoWhenNoStayInfoFound()  {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuestWithEmptyStayInfo()));
        assertThrows(GuestStayInfoNotFoundException.class, () -> guestService.deleteGuestStayInfo(500, 5000));
    }

    @Test
    void testDeleteCreditCardInfo() throws GuestCreditCardNotFoundException, GuestNotFoundException {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuest()));
        assertNotNull(guestService.deleteGuestCreditCardInfo(500, 500050005000L));
    }

    @Test
    void testDeleteCreditCardInfoWhenNoGuestFound()  {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.deleteGuestCreditCardInfo(500, 500050005000L));
    }

    @Test
    void testDeleteCreditCardInfoWhenNoCreditCardInfoFound()  {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuestWithEmptyCreditCardInfo()));
        assertThrows(GuestCreditCardNotFoundException.class, () -> guestService.deleteGuestCreditCardInfo(500, 500050005000L));
    }

    @Test
    void testSaveGuestProfile() {
        when(guestRepository.save(any())).thenReturn(buildGuest());
        assertNotNull(guestService.saveGuestProfile(buildGuestRequest()));
    }

    @Test
    void testGetUserNameAndPaasword() throws GuestNotFoundException {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.of(buildGuest()));
        assertNotNull(guestService.getUserNameAndPassword(500));
    }

    @Test
    void testGetUserNameAndPaaswordWhenNoGuestFound() {
        when(guestRepository.findByGuestId(anyInt())).thenReturn(Optional.empty());
        assertThrows(GuestNotFoundException.class, () -> guestService.getUserNameAndPassword(500));
    }

    private Guest buildGuestWithEmptyCreditCardInfo() {
        return Guest.builder().
                guestName("ABC").
                guestEmail("a@abc.com").
                guestId(500).
                guestAddress("Hyderabad").
                guestPhoneNumber(9999999999L).
                guestPassword("password").
                creditCards(Set.of()).
                stayHistory(Set.of(buildStayInfo())).
                build();
    }

    private Guest buildGuestWithEmptyStayInfo() {
        return Guest.builder().
                guestName("ABC").
                guestEmail("a@abc.com").
                guestId(500).
                guestAddress("Hyderabad").
                guestPhoneNumber(9999999999L).
                guestPassword("password").
                creditCards(Set.of(buildCreditCard())).
                stayHistory(Set.of()).
                build();
    }

    private Guest buildGuest() {
        return Guest.builder().
                guestName("ABC").
                guestEmail("a@abc.com").
                guestId(500).
                guestAddress("Hyderabad").
                guestPhoneNumber(9999999999L).
                guestPassword("password").
                creditCards(Set.of(buildCreditCard())).
                stayHistory(Set.of(buildStayInfo())).
                build();
    }

    private StayInfo buildStayInfo() {
        return StayInfo.builder().
                hotelId(100).
                roomNumbers(Set.of(100101, 100102)).
                stayId(5000).
                build();
    }

    private CreditCard buildCreditCard() {
        return CreditCard.builder().
                cvv(343).
                cardType(CardType.VISA_CARD).
                expiryMonth(12).
                expiryYear(2033).
                cardNumber(500050005000L).
                build();
    }

    private GuestRequest buildGuestRequest() {
        return GuestRequest.builder().
                guestName("Venkat").
                guestPassword("pass").
                guestAddress("Hyderabad").
                guestEmail("a@abc.com").
                guestPhoneNumber(9999999999L).
                stayHistory(Set.of(buildStayInfoRequest())).
                creditCards(Set.of(buildCreditCardRequest())).
                build();
    }

    private CreditCardRequest buildCreditCardRequest() {
        return CreditCardRequest.builder().
                cvv(734).
                cardType(CardType.VISA_CARD).
                expiryMonth(12).
                expiryYear(2034).
                build();
    }

    private StayInfoRequest buildStayInfoRequest() {
        return StayInfoRequest.builder().
                hotelId(101).
                roomNumbers(Set.of(100101, 100105)).
                build();
    }

}
