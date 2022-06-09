package com.myhotels.guestservice.controller;

import com.myhotels.guestservice.dto.CreditCardRequest;
import com.myhotels.guestservice.dto.GuestRequest;
import com.myhotels.guestservice.dto.StayInfoRequest;
import com.myhotels.guestservice.entities.CreditCard;
import com.myhotels.guestservice.entities.Guest;
import com.myhotels.guestservice.entities.StayInfo;
import com.myhotels.guestservice.exceptions.GuestCreditCardNotFoundException;
import com.myhotels.guestservice.exceptions.GuestNotFoundException;
import com.myhotels.guestservice.exceptions.GuestStayInfoNotFoundException;
import com.myhotels.guestservice.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${myhotel-microservices.guest-service.endpoints.base-uri}")
@Slf4j
@RefreshScope
@Tag(name = "Guest Service API", description = "Provides API to manage guest profile like adding guest, " +
        "removing guest, getting guest stay info, credit card info, etc..")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @Operation(summary = "Get All Guests Profiles", description = "Retrieves all guests profiles with their stay info" +
            " and credit card infos", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(description = "Get Guest Profiles Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestProfile", implementation =
                    Guest.class)))
    })
    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuestProfiles() {
        log.info("Inside get all guests profiles controller");
        List<Guest> guests = guestService.getAllGuestProfiles();
        return ResponseEntity.ok(guests);
    }

    @Operation(summary = "Post Guest Profile", description = "Save guest profile", tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(description = "Post Guest Profile Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestProfile", implementation =
                    Guest.class)))
    })
    @PostMapping
    public ResponseEntity<Guest> saveGuestProfile(@Valid @RequestBody GuestRequest guestRequest) {
        log.info("Inside save guest profile controller");
        Guest guest = guestService.saveGuestProfile(guestRequest);
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Guest Profile", description = "Get guest profile with given guest id", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(description = "Get Guest Profile Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestProfile", implementation =
                    Guest.class))),
            @ApiResponse(description = "Guest Profile Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "${myhotel-microservices.guest-service.endpoints.get-guest-profile}")
    public ResponseEntity<Guest> getGuestProfile(@PathVariable("guest_id") Integer guestId) throws GuestNotFoundException {
        log.info("Inside get guest profile controller with guest id: " + guestId);
        return ResponseEntity.ok(guestService.getGuestProfile(guestId));
    }

    @Operation(summary = "Get Guest Username And Password", description = "Retrieves guest username and password",
            tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(description = "Get Guest UserName Password Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestProfile", implementation =
                    Map.class)))
    })
    @GetMapping(value = "${myhotel-microservices.guest-service.endpoints.get-guest-username-password}")
    public ResponseEntity<Map<String, String>> getUserNameAndPassword(@PathVariable("guest_id") Integer guestId) throws GuestNotFoundException {
        log.info("Inside get guest username and password controller");
        Map<String, String> userDetails = guestService.getUserNameAndPassword(guestId);
        return ResponseEntity.ok(userDetails);
    }

    @Operation(summary = "Get Guest Stay Information", description = "Get guest stay information with given guest id " +
            "and stay id", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(description = "Get Guest Stay Information Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestStayInformation", implementation =
                    StayInfo.class))),
            @ApiResponse(description = "Guest Stay Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "${myhotel-microservices.guest-service.endpoints.get-guest-stay}")
    public ResponseEntity<StayInfo> getGuestStayInformation(@PathVariable("guest_id") Integer guestId,
                                                            @PathVariable("stay_id") Integer stayId)
            throws GuestNotFoundException, GuestStayInfoNotFoundException {
        log.info(String.format("Inside get guest stay information controller with guest id: %s and stay id: %s",
                guestId, stayId));
        return ResponseEntity.ok(guestService.getGuestStayInfo(guestId, stayId));
    }

    @Operation(summary = "Get Guest Creditcard Information", description = "Get guest credit card information with " +
            "guest id and credit card id", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(description = "Get Guest Creditcard Information Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestCreditcardInformation",
                    implementation = CreditCard.class))),
            @ApiResponse(description = "Guest Creditcard Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json")),
            @ApiResponse(description = "Guest Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "${myhotel-microservices.guest-service.endpoints.get-guest-creditcard}")
    public ResponseEntity<CreditCard> getGuestCreditcardInformation(@PathVariable("guest_id") Integer guestId,
                                                                    @PathVariable("card_id") Long cardId)
            throws GuestNotFoundException, GuestCreditCardNotFoundException {
        log.info(String.format("Inside get guest credit card information controller with guest id: %s and card id: %s",
                guestId, cardId));
        return ResponseEntity.ok(guestService.getGuestCreditCard(guestId, cardId));
    }

    @Operation(summary = "Post Guest Stay Information", description = "Save guest stay information with " +
            "guest id and stay information", tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(description = "Post Guest Stay Information Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestStayInformation",
                    implementation = StayInfo.class))),
            @ApiResponse(description = "Guest Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "${myhotel-microservices.guest-service.endpoints.post-guest-stay}")
    public ResponseEntity<StayInfo> saveGuestStayInfo(@PathVariable("guest_id") Integer guestId, @Valid
    @RequestBody StayInfoRequest stayInfoRequest)
            throws GuestNotFoundException {
        log.info(String.format("Inside save guest stay information controller with guest id: %s and stay information " +
                "request: %s", guestId, stayInfoRequest));
        return new ResponseEntity<>(guestService.saveGuestStayInfo(guestId, stayInfoRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Post Guest Credit Card Information", description = "Save guest credit card information with" +
            "guest id and card information", tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(description = "Post Guest Credit Card Information Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(name = "GuestCreditCardInformation",
                    implementation = CreditCard.class))),
            @ApiResponse(description = "Guest Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "${myhotel-microservices.guest-service.endpoints.post-guest-creditcard}")
    public ResponseEntity<CreditCard> saveGuestCreditCardInfo(@PathVariable("guest_id") Integer guestId, @Valid
    @RequestBody CreditCardRequest cardRequest)
            throws GuestNotFoundException {
        log.info(String.format("Inside save guest credit card information controller with guest id: %s and card " +
                "information request: %s", guestId, cardRequest));
        return new ResponseEntity<>(guestService.saveCreditCardInfo(guestId, cardRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Guest Stay Information", description = "Delete guest stay information with given " +
            "guest id and stay id", tags = "Delete")
    @ApiResponses(value = {
            @ApiResponse(description = "Delete Guest Stay Information Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(description = "Guest Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json")),
            @ApiResponse(description = "Guest Stay Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json"))
    })
    @DeleteMapping(value = "${myhotel-microservices.guest-service.endpoints.delete-guest-stay}")
    public ResponseEntity<String> deleteGuestStayInformation(@PathVariable("guest_id") Integer guestId,
                                                             @PathVariable("stay_id") Integer stayId)
            throws GuestNotFoundException, GuestStayInfoNotFoundException {
        log.info(String.format("Inside delete guest stay information controller with guest id: %s and stay id: %s",
                guestId, stayId));
        return guestService.deleteGuestStayInfo(guestId, stayId);
    }

    @Operation(summary = "Delete Guest Credit Card Information", description = "Delete guest credit card information " +
            "with given guest id and credit card id", tags = "Delete")
    @ApiResponses(value = {
            @ApiResponse(description = "Delete Guest Credit Card Information Success", responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(description = "Guest Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json")),
            @ApiResponse(description = "Guest Credit Card Information Not Found", responseCode = "404", content =
            @Content(mediaType = "application/json"))
    })
    @DeleteMapping(value = "${myhotel-microservices.guest-service.endpoints.delete-guest-creditcard}")
    public ResponseEntity<String> deleteGuestCreditCardInformation(@PathVariable("guest_id") Integer guestId,
                                                                   @PathVariable("card_id") Integer cardId)
            throws GuestNotFoundException, GuestCreditCardNotFoundException {
        log.info(String.format("Inside delete guest stay information controller with guest id: %s and card id: %s",
                guestId, cardId));
        return guestService.deleteGuestCreditCardInfo(guestId, cardId);
    }


}
