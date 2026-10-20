package com.example.demo.repository;

import com.example.demo.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * scores 조회.
 *
 * 설계 관리에서 생성되었습니다.
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
