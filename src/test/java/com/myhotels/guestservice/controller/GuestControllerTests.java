package com.myhotels.guestservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myhotels.guestservice.dto.CreditCardRequest;
import com.myhotels.guestservice.dto.GuestRequest;
import com.myhotels.guestservice.dto.StayInfoRequest;
import com.myhotels.guestservice.entities.CardType;
import com.myhotels.guestservice.entities.CreditCard;
import com.myhotels.guestservice.entities.Guest;
import com.myhotels.guestservice.entities.StayInfo;
import com.myhotels.guestservice.service.GuestService;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(GuestController.class)
@ExtendWith({SpringExtension.class})
@Import(GuestService.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class GuestControllerTests {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestService guestService;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void testGetGuestProfile() throws Exception {
        when(guestService.getAllGuestProfiles()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/guests")).andExpect(status().isOk());
    }

    @Test
    void testGetGuestUsernameAndPassword() throws Exception {
        when(guestService.getUserNameAndPassword(anyInt())).thenReturn(Map.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/guests/guest-authdetails/{guest_id}", 500)).
                andExpect(status().isOk());
    }

    @Test
    void testSaveGuestProfile() throws Exception {
        when(guestService.saveGuestProfile(any())).thenReturn(Guest.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/guests").contentType(APPLICATION_JSON).
                content(mapper.writeValueAsString(buildGuestRequest()))).andExpect(status().isCreated());
    }

    @Test
    void testGetOneGuestProfile() throws Exception {
        when(guestService.getGuestProfile(anyInt())).thenReturn(Guest.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/guests/guest/{guest_id}", 500)).
                andExpect(status().isOk());
    }

    @Test
    void testGetGuestStayInfo() throws Exception {
        when(guestService.getGuestStayInfo(anyInt(), anyInt())).thenReturn(StayInfo.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/guests/guest/{guest_id}/stay/{stay_id}",
                500, 5000)).andExpect(status().isOk());
    }

    @Test
    void testGetGuestCreditCard() throws Exception {
        when(guestService.getGuestCreditCard(anyInt(), anyLong())).thenReturn(CreditCard.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/guests/guest/{guest_id}/card/{card_id}",
                500, 522452245224L)).andExpect(status().isOk());
    }

    @Test
    void testPostGuestStayInfo() throws Exception {
        when(guestService.saveGuestStayInfo(anyInt(), any())).thenReturn(StayInfo.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/guests/guest/{guest_id}/stay", 500).
                contentType(APPLICATION_JSON).
                content(mapper.writeValueAsString(buildStayInfoRequest()))).andExpect(status().isCreated());
    }

    @Test
    void testPostGuestCreditCardInfo() throws Exception {
        when(guestService.saveCreditCardInfo(anyInt(), any())).thenReturn(CreditCard.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/guests/guest/{guest_id}/creditcard", 500).
                contentType(APPLICATION_JSON).
                content(mapper.writeValueAsString(buildCreditCardRequest()))).andExpect(status().isCreated());
    }

    @Test
    void testDeleteGuestStayInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/guests/guest/{guest_id}/stay/{stay_id}", 500, 5000)).
                andExpect(status().isOk());
    }

    @Test
    void testDeleteGuestCreditCardInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/guests/guest/{guest_id}/card/{card_id}", 500,
                        500050005000L)).
                andExpect(status().isOk());
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
