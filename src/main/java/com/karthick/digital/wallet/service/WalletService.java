package com.karthick.digital.wallet.service;

import java.util.List;

import com.karthick.digital.wallet.dto.TransactionDto;
import com.karthick.digital.wallet.dto.TransactionRequestDto;
import com.karthick.digital.wallet.dto.WalletDto;
import com.karthick.digital.wallet.dto.WalletTransactionDto;
import com.karthick.digital.wallet.exception.PlayerNotExistsException;
import com.karthick.digital.wallet.exception.TransactionException;

public interface WalletService {

	public WalletDto getWallet(long playerId) throws PlayerNotExistsException;

	public List<TransactionDto> getAllWalletTransactions(long playerId) throws PlayerNotExistsException;

	public WalletTransactionDto walletTransaction(long playerId, TransactionRequestDto transactionDto)
			throws PlayerNotExistsException, TransactionException;
}
