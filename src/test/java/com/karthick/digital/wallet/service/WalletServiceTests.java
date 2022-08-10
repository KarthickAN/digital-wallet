package com.karthick.digital.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.karthick.digital.wallet.converter.ModelConverter;
import com.karthick.digital.wallet.dto.TransactionDto;
import com.karthick.digital.wallet.dto.TransactionRequestDto;
import com.karthick.digital.wallet.dto.WalletDto;
import com.karthick.digital.wallet.dto.WalletTransactionDto;
import com.karthick.digital.wallet.entity.Player;
import com.karthick.digital.wallet.entity.PlayerStatus;
import com.karthick.digital.wallet.entity.Transaction;
import com.karthick.digital.wallet.entity.TransactionType;
import com.karthick.digital.wallet.entity.Wallet;
import com.karthick.digital.wallet.exception.PlayerNotExistsException;
import com.karthick.digital.wallet.exception.TransactionException;
import com.karthick.digital.wallet.props.AppProps;
import com.karthick.digital.wallet.repository.PlayerRepository;
import com.karthick.digital.wallet.repository.TransactionRepository;
import com.karthick.digital.wallet.repository.WalletRepository;

@ExtendWith(MockitoExtension.class)
class WalletServiceTests {

	@Mock
	private AppProps props;

	@Mock
	private ModelConverter converter;

	@Mock
	private PlayerRepository playerRepository;

	@Mock
	private WalletRepository walletRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private WalletServiceImpl walletService;

	@Test
	void getWalletTest() throws PlayerNotExistsException {
		Wallet w = Wallet.builder().walletId(1).build();
		Player player = Player.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).wallet(w).build();
		WalletDto walletDto = WalletDto.builder().build();
		when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
		when(converter.walletEntityToDto(w)).thenReturn(walletDto);
		assertEquals(walletDto, walletService.getWallet(1L));
	}

	@Test
	void getWalletExceptionTest() throws PlayerNotExistsException {
		assertThrows(PlayerNotExistsException.class, () -> {
			walletService.getWallet(0);
		});
	}

	@Test
	void walletTransactionCredit() throws PlayerNotExistsException, TransactionException {
		TransactionRequestDto transactionRequest = TransactionRequestDto.builder().amount(100).currency("INR")
				.transactionId(1).transactionType(TransactionType.CR).build();
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(Transaction.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.CR).build());
		Wallet w = Wallet.builder().walletId(1).transactions(transactions).build();
		Player player = Player.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).wallet(w).build();
		List<TransactionDto> transactionDtos = new ArrayList<>();
		transactionDtos.add(TransactionDto.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.CR).build());
		WalletTransactionDto dto = WalletTransactionDto.builder().creditedAmount(100).transactions(transactionDtos)
				.build();
		when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
		when(converter.transactionRequestDtoToEntity(transactionRequest))
				.thenReturn(Transaction.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
						.transactionId(1).type(TransactionType.CR).build());
		when(walletRepository.save(w)).thenReturn(w);
		when(converter.walletTransactionToDto(w)).thenReturn(dto);
		assertEquals(dto, walletService.walletTransaction(1, transactionRequest));
	}

	@Test
	void walletTransactionDebit() throws PlayerNotExistsException, TransactionException {
		TransactionRequestDto transactionRequest = TransactionRequestDto.builder().amount(100).currency("INR")
				.transactionId(1).transactionType(TransactionType.DB).build();
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(Transaction.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.DB).build());
		Wallet w = Wallet.builder().walletId(1).transactions(transactions).creditedAmount(200).build();
		Player player = Player.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).wallet(w).build();
		List<TransactionDto> transactionDtos = new ArrayList<>();
		transactionDtos.add(TransactionDto.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.DB).build());
		WalletTransactionDto dto = WalletTransactionDto.builder().creditedAmount(100).transactions(transactionDtos)
				.build();
		when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
		when(converter.transactionRequestDtoToEntity(transactionRequest))
				.thenReturn(Transaction.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
						.transactionId(1).type(TransactionType.DB).build());
		when(walletRepository.save(w)).thenReturn(w);
		when(converter.walletTransactionToDto(w)).thenReturn(dto);
		assertEquals(dto, walletService.walletTransaction(1, transactionRequest));
	}

	@Test
	void walletTransactionDebitInsufficientBalance() throws PlayerNotExistsException, TransactionException {
		TransactionRequestDto transactionRequest = TransactionRequestDto.builder().amount(100).currency("INR")
				.transactionId(1).transactionType(TransactionType.DB).build();
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(Transaction.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.DB).build());
		Wallet w = Wallet.builder().walletId(1).transactions(transactions).build();
		Player player = Player.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).wallet(w).build();
		List<TransactionDto> transactionDtos = new ArrayList<>();
		transactionDtos.add(TransactionDto.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.DB).build());
		when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
		assertThrows(TransactionException.class, () -> {
			walletService.walletTransaction(1, transactionRequest);
		});
	}

	@Test
	void walletTransactionExistsTest() {
		TransactionRequestDto transactionRequest = TransactionRequestDto.builder().amount(100).currency("INR")
				.transactionId(1).transactionType(TransactionType.CR).build();
		when(transactionRepository.existsById(1L)).thenReturn(true);
		assertThrows(TransactionException.class, () -> {
			walletService.walletTransaction(1, transactionRequest);
		});
	}

	@Test
	void walletTransactionPlayerNotExistsTest() {
		TransactionRequestDto transactionRequest = TransactionRequestDto.builder().amount(100).currency("INR")
				.transactionId(1).transactionType(TransactionType.CR).build();
		assertThrows(PlayerNotExistsException.class, () -> {
			walletService.walletTransaction(1, transactionRequest);
		});
	}

	@Test
	void getAllWalletTransactions() throws PlayerNotExistsException {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(Transaction.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.CR).build());
		Wallet w = Wallet.builder().walletId(1).transactions(transactions).build();
		Player player = Player.builder().country("India").firstName("Karthick").lastName("Natarajan")
				.joinedOn(Instant.now().getEpochSecond()).playerId(1).status(PlayerStatus.ACTIVE).wallet(w).build();
		List<TransactionDto> transactionDtos = new ArrayList<>();
		transactionDtos.add(TransactionDto.builder().amount(100).currency("INR").time(Instant.now().getEpochSecond())
				.transactionId(1).type(TransactionType.CR).build());
		when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
		when(converter.transactionEntityToDto(transactions)).thenReturn(transactionDtos);
		assertEquals(transactionDtos, walletService.getAllWalletTransactions(1L));
	}

	@Test
	void getAllWalletTransactionExcepionTest() throws PlayerNotExistsException {
		assertThrows(PlayerNotExistsException.class, () -> {
			walletService.getAllWalletTransactions(0);
		});
	}
}
