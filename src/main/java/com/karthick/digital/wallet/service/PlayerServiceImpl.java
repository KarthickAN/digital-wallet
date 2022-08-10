package com.karthick.digital.wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthick.digital.wallet.converter.ModelConverter;
import com.karthick.digital.wallet.dto.PlayerCreationRequestDto;
import com.karthick.digital.wallet.dto.PlayerDto;
import com.karthick.digital.wallet.entity.Player;
import com.karthick.digital.wallet.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private ModelConverter converter;

	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public PlayerDto create(PlayerCreationRequestDto playerCreationDto) {
		Player player = converter.playerCreationDtoToEntity(playerCreationDto);
		player = playerRepository.save(player);
		return converter.playerEntityToDto(player);
	}

	@Override
	public List<PlayerDto> getAllPlayers() {
		return converter.playerEntityToDto(playerRepository.findAll());
	}

}
