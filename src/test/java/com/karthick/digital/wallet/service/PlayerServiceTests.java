package com.karthick.digital.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.karthick.digital.wallet.converter.ModelConverter;
import com.karthick.digital.wallet.dto.PlayerCreationRequestDto;
import com.karthick.digital.wallet.dto.PlayerDto;
import com.karthick.digital.wallet.entity.Player;
import com.karthick.digital.wallet.entity.PlayerStatus;
import com.karthick.digital.wallet.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTests {

	@Mock
	private ModelConverter converter;

	@Mock
	private PlayerRepository playerRepository;

	@InjectMocks
	private PlayerServiceImpl playerService;

	@Test
	void createPlayerTest() {
		PlayerCreationRequestDto playerCreationRequest = PlayerCreationRequestDto.builder().country("India")
				.currency("INR").firstName("Karthick").lastName("Natarajan").build();
		PlayerDto playerDto = PlayerDto.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).build();
		Player player = Player.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).build();
		when(converter.playerCreationDtoToEntity(playerCreationRequest)).thenReturn(player);
		when(playerRepository.save(player)).thenReturn(player);
		when(converter.playerEntityToDto(player)).thenReturn(playerDto);
		assertEquals(playerDto, playerService.create(playerCreationRequest));
	}

	@Test
	void getAllPlayersTest() {
		List<PlayerDto> playerDtos = new ArrayList<>();
		playerDtos.add(PlayerDto.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).build());
		List<Player> players = new ArrayList<>();
		players.add(Player.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).build());
		when(playerRepository.findAll()).thenReturn(players);
		when(converter.playerEntityToDto(players)).thenReturn(playerDtos);
		assertEquals(1, playerService.getAllPlayers().size());
	}

}
