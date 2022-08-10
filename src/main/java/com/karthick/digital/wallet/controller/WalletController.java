package com.karthick.digital.wallet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthick.digital.wallet.dto.TransactionDto;
import com.karthick.digital.wallet.dto.TransactionRequestDto;
import com.karthick.digital.wallet.dto.WalletDto;
import com.karthick.digital.wallet.exception.PlayerNotExistsException;
import com.karthick.digital.wallet.exception.TransactionException;
import com.karthick.digital.wallet.service.WalletService;

@RestController
@RequestMapping(value = "/v1/player")
public class WalletController {

	@Autowired
	private WalletService walletService;

	@GetMapping(path = "/{playerId}/wallet")
	public WalletDto getWallet(@PathVariable long playerId) throws PlayerNotExistsException {
		return walletService.getWallet(playerId);
	}

	@GetMapping(path = "/{playerId}/wallet/transaction")
	public List<TransactionDto> getAllWalletTransactions(@PathVariable long playerId) throws PlayerNotExistsException {
		return walletService.getAllWalletTransactions(playerId);
	}

	@PostMapping(path = "/{playerId}/wallet/transaction")
	public void walletTransaction(@PathVariable long playerId, @Valid @RequestBody TransactionRequestDto transactionDto)
			throws PlayerNotExistsException, TransactionException {
		walletService.walletTransaction(playerId, transactionDto);
	}
}
