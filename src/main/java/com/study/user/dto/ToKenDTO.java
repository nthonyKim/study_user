package com.study.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ToKenDTO {
	private String accessToken;
	private String refreshToken;
}
