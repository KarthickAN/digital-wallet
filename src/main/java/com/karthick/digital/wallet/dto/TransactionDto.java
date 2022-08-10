package com.karthick.digital.wallet.dto;

import com.karthick.digital.wallet.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

	private long transactionId;

	private TransactionType type;

	private String currency;

	private float amount;

	private long time;
}
