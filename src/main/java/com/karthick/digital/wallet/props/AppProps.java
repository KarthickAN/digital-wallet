package com.karthick.digital.wallet.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AppProps {

	@Value("${digital.wallet.min.balance}")
	private float minBalance;
}
