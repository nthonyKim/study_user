package com.study.user.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthGroupDetail {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer authGroupDetailSeq; // 권한 그룹 상세 일련번호
	private String authType; // 권한 구분
	private String authGroupId; // 권한 그룹 아이디
	private String menuId; // 메뉴 아이디
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime registerDatetime; // 등록일시
	private LocalDateTime updateDateTime; // 수정일시

}
