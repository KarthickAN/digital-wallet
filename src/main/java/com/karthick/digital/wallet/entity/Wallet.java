package com.karthick.digital.wallet.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "wallets")
public class Wallet {

	@Id
	@Setter
	@Column(name = "wallet_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long walletId;

	@Setter
	@Column(name = "currency")
	private String currency;

	@Column(name = "bonus_amount")
	private float bonusAmount;

	@Column(name = "credited_amount")
	private float creditedAmount;

	@Column(name = "winnings_amount")
	private float winningsAmount;

	@Column(name = "total_balance")
	private float totalBalance;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "walletId")
	@Setter
	private List<Transaction> transactions;

	public void setBonusAmount(float bonusAmount) {
		this.bonusAmount = bonusAmount;
		this.setTotalBalance();
	}

	public void setCreditedAmount(float creditedAmount) {
		this.creditedAmount = creditedAmount;
		this.setTotalBalance();
	}

	public void setWinningsAmount(float winningsAmount) {
		this.winningsAmount = winningsAmount;
		this.setTotalBalance();
	}

	private void setTotalBalance() {
		this.totalBalance = this.getBonusAmount() + this.getCreditedAmount() + this.getWinningsAmount();
	}

}
