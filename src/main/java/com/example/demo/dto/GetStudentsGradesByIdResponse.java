package com.example.demo.dto;

import java.util.List;

/**
 * 특정 학생의 과목별 점수와 평균을 조회합니다.
 *
 * 설계 관리의 API 명세에 적힌 예시에서 만들어졌습니다.
 */
public record GetStudentsGradesByIdResponse(
        Long student_id,
        String student_name,
        String department,
        List<Object> grades,
        Double average
) {
}
