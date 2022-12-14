package com.karthick.digital.wallet.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

	private Date timestamp;
	private int status;
	private String error;
	private String path;

}
