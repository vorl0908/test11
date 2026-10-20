package com.example.demo.controller;

import com.example.demo.dto.CreateStudentsGradesByIdRequest;
import com.example.demo.dto.CreateStudentsGradesByIdResponse;
import com.example.demo.dto.GetStudentsGradesByIdResponse;
import com.example.demo.domain.Student;
import com.example.demo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 설계 관리에서 생성된 컨트롤러입니다.
 *
 * 표준 CRUD 인 API 는 몸통까지 만들어 서비스를 부릅니다.
 * 그 밖의 API 는 무엇을 어떻게 처리할지 기계가 알 수 없어 스텁으로 두었으니
 * 직접 채워 주세요. 각 메서드 주석에 그 API 가 어느 요구사항에서 나왔고
 * 어느 화면이 부르는지 적혀 있습니다.
 */
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 특정 학생에게 과목 성적을 추가합니다.
     *
     * 요구사항: R-01 학생 성적 추가
     * 부르는 화면: 학생 성적 상세 (/students/:studentId)
     */
    @PostMapping("/api/students/{studentId}/grades")
    public ResponseEntity<CreateStudentsGradesByIdResponse> createStudentsGradesById(@PathVariable("studentId") String studentId, @RequestBody CreateStudentsGradesByIdRequest request) {
        // TODO: 구현해 주세요.
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }

    /**
     * 특정 학생의 과목별 점수와 평균을 조회합니다.
     *
     * 요구사항: R-02 학생 성적 조회, R-03 학생 선택
     * 부르는 화면: 학생 성적 상세 (/students/:studentId)
     */
    @GetMapping("/api/students/{studentId}/grades")
    public ResponseEntity<GetStudentsGradesByIdResponse> getStudentsGradesById(@PathVariable("studentId") String studentId) {
        // TODO: 구현해 주세요.
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }

    /**
     * 등록된 학생들의 목록을 조회합니다.
     *
     * 요구사항: R-04 학생 목록 조회
     * 부르는 화면: 학생 목록 (/students)
     */
    @GetMapping("/api/students")
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }
}
