package com.karthick.digital.wallet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionDto {

	private String currency;

	private float bonusAmount;

	private float creditedAmount;

	private float winningsAmount;

	private float totalBalance;

	private List<TransactionDto> transactions;
}
