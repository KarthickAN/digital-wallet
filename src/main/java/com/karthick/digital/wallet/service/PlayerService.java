package com.karthick.digital.wallet.service;

import java.util.List;

import com.karthick.digital.wallet.dto.PlayerCreationRequestDto;
import com.karthick.digital.wallet.dto.PlayerDto;

public interface PlayerService {

	public List<PlayerDto> getAllPlayers();

	public PlayerDto create(PlayerCreationRequestDto player);

}
