package com.karthick.digital.wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.karthick.digital.wallet.converter.ModelConverter;
import com.karthick.digital.wallet.dto.TransactionDto;
import com.karthick.digital.wallet.dto.TransactionRequestDto;
import com.karthick.digital.wallet.dto.WalletDto;
import com.karthick.digital.wallet.dto.WalletTransactionDto;
import com.karthick.digital.wallet.entity.Player;
import com.karthick.digital.wallet.entity.Transaction;
import com.karthick.digital.wallet.entity.TransactionType;
import com.karthick.digital.wallet.entity.Wallet;
import com.karthick.digital.wallet.exception.PlayerNotExistsException;
import com.karthick.digital.wallet.exception.TransactionException;
import com.karthick.digital.wallet.props.AppProps;
import com.karthick.digital.wallet.repository.PlayerRepository;
import com.karthick.digital.wallet.repository.TransactionRepository;
import com.karthick.digital.wallet.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private AppProps props;

	@Autowired
	private ModelConverter converter;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	@Transactional(readOnly = true)
	public WalletDto getWallet(long playerId) throws PlayerNotExistsException {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isEmpty())
			throw new PlayerNotExistsException("Player not found");
		Wallet w = player.get().getWallet();
		return converter.walletEntityToDto(w);
	}

	@Override
	@Transactional
	public WalletTransactionDto walletTransaction(long playerId, TransactionRequestDto transactionDto)
			throws PlayerNotExistsException, TransactionException {
		if (transactionRepository.existsById(transactionDto.getTransactionId()))
			throw new TransactionException("Transaction already exists");
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isEmpty())
			throw new PlayerNotExistsException("Player not found");
		Wallet w = player.get().getWallet();
		if (TransactionType.CR.equals(transactionDto.getTransactionType())) {
			w.setCreditedAmount(w.getCreditedAmount() + transactionDto.getAmount());
		} else {
			if (w.getCreditedAmount() - transactionDto.getAmount() < props.getMinBalance())
				throw new TransactionException("Insufficient balance");
			w.setCreditedAmount(w.getCreditedAmount() - transactionDto.getAmount());
		}
		Transaction transaction = converter.transactionRequestDtoToEntity(transactionDto);
		w.getTransactions().add(transaction);
		return converter.walletTransactionToDto(walletRepository.save(w));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TransactionDto> getAllWalletTransactions(long playerId) throws PlayerNotExistsException {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isEmpty())
			throw new PlayerNotExistsException("Player not found");
		Wallet w = player.get().getWallet();
		return converter.transactionEntityToDto(w.getTransactions());
	}

}
