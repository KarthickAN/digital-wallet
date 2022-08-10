package com.karthick.digital.wallet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthick.digital.wallet.dto.PlayerCreationRequestDto;
import com.karthick.digital.wallet.dto.PlayerDto;
import com.karthick.digital.wallet.service.PlayerService;

@RestController
@RequestMapping(value = "/v1")
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@GetMapping(path = "/player")
	public List<PlayerDto> getAllPlayers() {
		return playerService.getAllPlayers();
	}

	@PostMapping(path = "/player", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlayerDto createPlayer(@Valid @RequestBody PlayerCreationRequestDto player) {
		return playerService.create(player);
	}

}
