package com.example.demo.dto;

/**
 * 특정 학생에게 과목 성적을 추가합니다.
 *
 * 설계 관리의 API 명세에 적힌 예시에서 만들어졌습니다.
 */
public record CreateStudentsGradesByIdRequest(
        String subject,
        Long score
) {
}
