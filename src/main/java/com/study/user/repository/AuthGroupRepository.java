package com.study.user.repository;

import com.study.user.entity.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthGroupRepository extends JpaRepository<AuthGroup, String> {
}
