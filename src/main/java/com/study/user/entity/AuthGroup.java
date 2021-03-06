package com.study.user.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthGroup {
	@Id
	private String authGroupId; // 권한 그룹 아이디
	private String authGroupName; // 권한 그룹 이름
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime registerDatetime; // 등록일시
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDatetime; // 수정일시
	private Boolean useFlag; // 사용여부

	@OneToMany(mappedBy = "authGroup", fetch = FetchType.LAZY, cascade= CascadeType.REMOVE, orphanRemoval = true)
	private List<AuthGroupDetail> authGroupDetails;

	public void softDelete(){
		this.setUseFlag(false);
	}

	@PrePersist
	private void insertSetting(){
		if(this.useFlag == null) { this.useFlag = true; }
	}
}
