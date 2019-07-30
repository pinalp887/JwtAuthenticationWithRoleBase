package com.cignex.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class JwtTokenResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -311902264425366289L;

	private String token;
	
}
