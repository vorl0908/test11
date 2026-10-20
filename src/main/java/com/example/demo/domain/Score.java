package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * 학생의 과목별 성적을 저장하는 테이블
 *
 * 관련 요구사항: R-01 학생 성적 추가, R-02 학생 성적 조회, R-03 학생 선택
 *
 * 설계 관리에서 생성되었습니다. 직접 고쳐도 되지만, 다시 생성하면 덮어씁니다.
 */
@Entity
@Table(name = "scores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {

    /** 성적 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 학생 ID (students 테이블 참조) */
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    /** 과목 */
    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    /** 점수 */
    @Column(name = "score", nullable = false)
    private Integer score;
}
