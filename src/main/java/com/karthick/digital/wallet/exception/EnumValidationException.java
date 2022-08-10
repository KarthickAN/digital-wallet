package com.karthick.digital.wallet.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnumValidationException extends Exception {

	private static final long serialVersionUID = -1302594191109203339L;

	private final String enumValue;

	private final String enumName;

	public EnumValidationException(String enumValue, String enumName) {
		super(enumValue);
		this.enumValue = enumValue;
		this.enumName = enumName;
	}

	public EnumValidationException(String enumValue, String enumName, Throwable cause) {
		super(enumValue, cause);
		this.enumValue = enumValue;
		this.enumName = enumName;
	}

}
