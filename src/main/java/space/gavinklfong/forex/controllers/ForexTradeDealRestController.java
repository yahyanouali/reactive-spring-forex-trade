package space.gavinklfong.forex.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.gavinklfong.forex.dto.ForexTradeDealReq;
import space.gavinklfong.forex.exceptions.InvalidRequestException;
import space.gavinklfong.forex.models.ForexTradeDeal;
import space.gavinklfong.forex.services.ForexTradeService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/deals")
public class ForexTradeDealRestController {
	
	@Autowired
	private ForexTradeService tradeService;
	
	/**
	 * Expose API for customers to retrieve their forex trade deals
	 * 
	 * API - GET /deals
	 * 
	 * @param customerId Exception will be thrown if customer id is empty,
	 * the exception will be translated to HTTP response with 4xx status
	 * 
	 * @return List of forex trade deal records. The framework formats it into JSON format when sending HTTP response
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ForexTradeDeal> getDeals(@RequestParam Long customerId, @RequestParam Optional<String>dealRef) {
		
		if (customerId == null) {
			return Flux.error(new InvalidRequestException("customerId", "customer Id cannot be blank"));
		}

		return tradeService.retrieveTradeDealByCustomer(customerId, dealRef);
	}
	
	/**
	 * Expose API for customers to post forex trade deal
	 * 
	 * API - POST /deals
	 * 
	 * @param req - Java bean contains trade deal information.
	 * '@RequestBody' annotation instructs the framework to convert JSON format into Java bean
	 * while '@Valid' annotation tells the framework to validate the object otherwise exception will be thrown
	 * and send HTTP response with 4xx status back to client
	 * 
	 * 
	 * @return Trade deal object. The framework formats it into JSON format when sending HTTP response
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ForexTradeDeal> submitDeal(@Valid @RequestBody ForexTradeDealReq req) {

		log.info("submitDeal() {}", req);

		// submit trade deal
		return tradeService.postTradeDeal(req);

	}
	

}