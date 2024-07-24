package com.lblandi.homebanking.api.models;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorModel {
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();

	@Builder.Default
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	private String details;
}
