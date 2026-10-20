package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * 학생 정보를 저장하는 테이블
 *
 * 관련 요구사항: R-02 학생 성적 조회, R-03 학생 선택, R-04 학생 목록 조회
 *
 * 설계 관리에서 생성되었습니다. 직접 고쳐도 되지만, 다시 생성하면 덮어씁니다.
 */
@Entity
@Table(name = "students")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    /** 학번 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 이름 */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /** 학과 */
    @Column(name = "department", nullable = false, length = 255)
    private String department;
}
