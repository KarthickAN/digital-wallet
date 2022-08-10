package com.karthick.digital.wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

	@Id
	@Column(name = "transaction_id")
	private long transactionId;

	@Column(name = "type")
	private TransactionType type;

	@Column(name = "currency")
	private String currency;

	@Column(name = "amount")
	private float amount;

	@Column(name = "time")
	private long time;

}
