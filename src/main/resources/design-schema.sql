-- 설계 관리에서 생성된 테이블 정의입니다.
-- 다시 생성하면 이 파일은 덮어써집니다.

-- 학생 정보를 저장하는 테이블
CREATE TABLE IF NOT EXISTS `students` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '학번',
    `name` VARCHAR(255) NOT NULL COMMENT '이름',
    `department` VARCHAR(255) NOT NULL COMMENT '학과',
    PRIMARY KEY (`id`)
);

-- 학생의 과목별 성적을 저장하는 테이블
CREATE TABLE IF NOT EXISTS `scores` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '성적 ID',
    `student_id` BIGINT NOT NULL COMMENT '학생 ID (students 테이블 참조)',
    `subject` VARCHAR(255) NOT NULL COMMENT '과목',
    `score` INT NOT NULL COMMENT '점수',
    PRIMARY KEY (`id`)
);

