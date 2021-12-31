package com.study.user.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
	@Id
	private String userId; // 사용자 아이디
	private String userName; // 사용자 이름
	private String userPw; // 사용자 비밀번호
	private String authGroupId; // 권한 그룹 아이디
	private LocalDateTime lastLoginDatetime; // 마지막 로그인 일시
	private LocalDateTime lastChangePwDatetime; // 마지막 비밀번호 변경일시
	private Integer wrongPwCnt; // 비밀번호 오류 횟수
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime registerDatetime; // 등록일시
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDatetime; // 수정일시
	private Integer useFlag; // 사용여부

}
