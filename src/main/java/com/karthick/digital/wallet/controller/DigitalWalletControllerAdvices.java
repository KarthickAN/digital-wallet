package com.karthick.digital.wallet.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.karthick.digital.wallet.dto.ErrorDto;
import com.karthick.digital.wallet.exception.EnumValidationException;
import com.karthick.digital.wallet.exception.PlayerNotExistsException;
import com.karthick.digital.wallet.exception.TransactionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class DigitalWalletControllerAdvices {

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDto handleException(HttpServletRequest req, MethodArgumentNotValidException ex) {
		log.error(ex.getMessage());
		List<String> messages = ex.getBindingResult().getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
		return ErrorDto.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value())
				.error(messages.toString()).path(req.getRequestURI()).build();
	}

	@ExceptionHandler(PlayerNotExistsException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorDto handlePlayerNotExistsException(HttpServletRequest req, PlayerNotExistsException ex) {
		log.error(ex.getMessage());
		return ErrorDto.builder().timestamp(new Date()).status(HttpStatus.NOT_FOUND.value()).error(ex.getMessage())
				.path(req.getRequestURI()).build();
	}

	@ExceptionHandler(TransactionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDto handleTransactionException(HttpServletRequest req, TransactionException ex) {
		log.error(ex.getMessage());
		return ErrorDto.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value()).error(ex.getMessage())
				.path(req.getRequestURI()).build();
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDto handleHttpMessageNotReadableException(HttpServletRequest req,
			HttpMessageNotReadableException ex) {
		log.error(ex.getMessage());
		EnumValidationException exception = (EnumValidationException) ex.getMostSpecificCause();
		return ErrorDto.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value())
				.error(exception.getEnumValue() + " is an invalid " + exception.getEnumName()).path(req.getRequestURI())
				.build();
	}
}
