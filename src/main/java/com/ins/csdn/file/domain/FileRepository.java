package com.ins.csdn.file.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileRepository extends JpaRepository<FileInfoEntity,String> , QuerydslPredicateExecutor {
}
