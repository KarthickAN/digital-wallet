package com.karthick.digital.wallet.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.karthick.digital.wallet.exception.EnumValidationException;

public enum TransactionType {

	CR("CR"), DB("DB");

	private final String type;

	TransactionType(String type) {
		this.type = type;
	}

	String getType() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}

	@JsonCreator
	public static TransactionType parse(String value) throws EnumValidationException {
		if (value == null) {
			throw new EnumValidationException(value, "TransactionType");
		}
		for (TransactionType v : values()) {
			if (value.equals(v.getType())) {
				return v;
			}
		}
		throw new EnumValidationException(value, "TransactionType");
	}
}
