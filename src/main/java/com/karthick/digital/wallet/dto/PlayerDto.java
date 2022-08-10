package com.karthick.digital.wallet.dto;

import com.karthick.digital.wallet.entity.PlayerStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

	private long playerId;

	private String firstName;

	private String lastName;

	private long joinedOn;

	private PlayerStatus status;

	private String country;
}
