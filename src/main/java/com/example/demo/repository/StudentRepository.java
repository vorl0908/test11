package com.example.demo.repository;

import com.example.demo.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * students 조회.
 *
 * 설계 관리에서 생성되었습니다.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
