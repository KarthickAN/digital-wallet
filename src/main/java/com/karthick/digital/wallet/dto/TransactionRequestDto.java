package com.karthick.digital.wallet.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.karthick.digital.wallet.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {

	@Min(value = 1, message = "transaction id should be greater than zero")
	private long transactionId;

	private TransactionType transactionType;

	@Min(value = 1, message = "transaction amount should be greater than zero")
	private float amount;

	@NotEmpty(message = "currency shouldn't be empty or null")
	private String currency;
}
