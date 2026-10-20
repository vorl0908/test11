package com.example.demo.service;

import com.example.demo.domain.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

/**
 * students 의 표준 CRUD 입니다.
 *
 * 설계에서 이 표에 연결된 API 중 CRUD 모양인 것만 만들었습니다.
 * 그 밖의 처리는 컨트롤러에 스텁으로 남아 있으니 그쪽을 채워 주세요.
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
