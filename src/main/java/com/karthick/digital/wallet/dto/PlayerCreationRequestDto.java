package com.karthick.digital.wallet.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCreationRequestDto {

	@NotEmpty(message = "first name shouldn't be empty or null")
	private String firstName;

	@NotEmpty(message = "last name shouldn't be empty or null")
	private String lastName;

	@NotEmpty(message = "country shouldn't be empty or null")
	private String country;

	@NotEmpty(message = "currency shouldn't be empty or null")
	private String currency;
}
