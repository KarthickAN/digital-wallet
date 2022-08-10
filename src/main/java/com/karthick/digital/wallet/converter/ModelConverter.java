package com.karthick.digital.wallet.converter;

import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.karthick.digital.wallet.dto.PlayerCreationRequestDto;
import com.karthick.digital.wallet.dto.PlayerDto;
import com.karthick.digital.wallet.dto.TransactionDto;
import com.karthick.digital.wallet.dto.TransactionRequestDto;
import com.karthick.digital.wallet.dto.WalletDto;
import com.karthick.digital.wallet.dto.WalletTransactionDto;
import com.karthick.digital.wallet.entity.Player;
import com.karthick.digital.wallet.entity.PlayerStatus;
import com.karthick.digital.wallet.entity.Transaction;
import com.karthick.digital.wallet.entity.Wallet;

@Component
public class ModelConverter {

	@Autowired
	private ModelMapper mapper;

	public Player playerCreationDtoToEntity(PlayerCreationRequestDto playerDto) {
		Player player = mapper.map(playerDto, Player.class);
		player.setJoinedOn(Instant.now().getEpochSecond());
		player.setStatus(PlayerStatus.ACTIVE);
		Wallet w = mapper.map(playerDto, Wallet.class);
		player.setWallet(w);
		return player;
	}

	public PlayerDto playerEntityToDto(Player player) {
		return mapper.map(player, PlayerDto.class);
	}

	public List<PlayerDto> playerEntityToDto(List<Player> players) {
		return players.stream().map(this::playerEntityToDto).toList();
	}

	public WalletDto walletEntityToDto(Wallet wallet) {
		return mapper.map(wallet, WalletDto.class);
	}

	public Transaction transactionRequestDtoToEntity(TransactionRequestDto transactionDto) {
		Transaction transaction = mapper.map(transactionDto, Transaction.class);
		transaction.setTime(Instant.now().getEpochSecond());
		return transaction;
	}

	public TransactionDto transactionEntityToDto(Transaction transaction) {
		return mapper.map(transaction, TransactionDto.class);
	}

	public WalletTransactionDto walletTransactionToDto(Wallet wallet) {
		return mapper.map(wallet, WalletTransactionDto.class);
	}

	public List<TransactionDto> transactionEntityToDto(List<Transaction> transactions) {
		return transactions.stream().map(this::transactionEntityToDto).toList();
	}
}
