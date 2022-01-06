package com.study.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryEntity;
import com.study.user.entity.AuthGroupDetail;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthGroupDTO {
	@NotNull
	private String authGroupId; // 권한 그룹 아이디
	@NotNull
	private String authGroupName; // 권한 그룹 이름
	@Hidden
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime registerDatetime; // 등록일시
	@Hidden
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDatetime; // 수정일시
	@Hidden
	private Boolean useFlag; // 사용여부
	private List<AuthGroupDetailDTO> authGroupDetails;
}
