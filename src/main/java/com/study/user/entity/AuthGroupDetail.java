package com.study.user.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AuthGroupDetail {
	public static final String TYPE_READ = "READ";
	public static final String TYPE_CREATE = "CREATE";
	public static final String TYPE_UPDATE = "UPDATE";
	public static final String TYPE_DELETE = "DELETE";

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer authGroupDetailSeq; // 권한 그룹 상세 일련번호
	private String authType; // 권한 구분
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime registerDatetime; // 등록일시
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDatetime; // 수정일시

	@ManyToOne
	@JoinColumn(name = "auth_group_id")
	@JsonIgnore
	private AuthGroup authGroup;
	@ManyToOne
	@JoinColumn(name = "menu_id")
	@JsonIgnore
	private Menu menu;
}
