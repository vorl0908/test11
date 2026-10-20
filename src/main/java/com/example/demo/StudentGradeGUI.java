package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class StudentGradeGUI extends JFrame {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    // 성적 추가 탭 컴포넌트
    private JTextField addStudentIdField;
    private JComboBox<String> addSubjectComboBox;
    private JTextField addScoreField;
    private JLabel addStatusLabel;

    // 성적 조회 탭 컴포넌트
    private JTextField viewStudentIdField;
    private JButton viewGradesButton;
    private DefaultTableModel tableModel;
    private JLabel viewStatusLabel;
    private JLabel averageLabel;

    public StudentGradeGUI() {
        setTitle("학생 성적 관리 시스템");
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 배치
        getContentPane().setBackground(Color.WHITE);

        // Content Pane 설정
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(16, 16, 16, 16));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        // NORTH - 제목
        JLabel titleLabel = new JLabel("학생 성적 관리 시스템", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 12, 0));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // CENTER - JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // "성적 추가" 탭
        JPanel addGradePanel = createAddGradeTab();
        tabbedPane.addTab("성적 추가", addGradePanel);

        // "성적 조회" 탭
        JPanel viewGradePanel = createViewGradeTab();
        tabbedPane.addTab("성적 조회", viewGradePanel);

        // 이벤트 리스너 연결
        JButton addGradeButton = (JButton) ((JPanel) ((JPanel) addGradePanel.getComponent(0)).getComponent(1)).getComponent(0);
        addGradeButton.addActionListener(e -> addGrade());
        viewGradesButton.addActionListener(e -> viewGrades());
    }

    private JPanel createAddGradeTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 입력 폼 패널 (GridBagLayout)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 12, 0); // 줄 간격 12
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 학번
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel studentIdLabel = new JLabel("학번");
        studentIdLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        formPanel.add(studentIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        addStudentIdField = new JTextField();
        addStudentIdField.setPreferredSize(new Dimension(0, 32));
        addStudentIdField.setBorder(BorderFactory.createLineBorder(Color.decode("#D1D5DB"), 1));
        addStudentIdField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        formPanel.add(addStudentIdField, gbc);

        // 과목
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel subjectLabel = new JLabel("과목");
        subjectLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        formPanel.add(subjectLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        addSubjectComboBox = new JComboBox<>(new String[]{"국어", "영어", "수학", "과학"});
        addSubjectComboBox.setPreferredSize(new Dimension(0, 32));
        addSubjectComboBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        ((JLabel) addSubjectComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(addSubjectComboBox, gbc);

        // 점수
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel scoreLabel = new JLabel("점수");
        scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        formPanel.add(scoreLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        addScoreField = new JTextField();
        addScoreField.setPreferredSize(new Dimension(0, 32));
        addScoreField.setBorder(BorderFactory.createLineBorder(Color.decode("#D1D5DB"), 1));
        addScoreField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        formPanel.add(addScoreField, gbc);

        // 마지막 줄 간격 제거
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 3;
        formPanel.add(new JPanel() {{ setOpaque(false); }}, gbc); // 빈 패널 추가하여 마지막 여백 조절

        panel.add(formPanel, BorderLayout.NORTH);

        // "성적 추가" 버튼 및 상태 라벨
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.setBorder(new EmptyBorder(12, 0, 0, 0)); // 폼 아래 여백

        JButton addGradeButton = new JButton("성적 추가");
        addGradeButton.setBackground(Color.decode("#2563EB"));
        addGradeButton.setForeground(Color.WHITE);
        addGradeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        addGradeButton.setPreferredSize(new Dimension(0, 42));
        addGradeButton.setOpaque(true);
        addGradeButton.setBorderPainted(false);
        southPanel.add(addGradeButton, BorderLayout.NORTH);

        addStatusLabel = new JLabel("", SwingConstants.LEFT);
        addStatusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        addStatusLabel.setBorder(new EmptyBorder(8, 0, 0, 0));
        southPanel.add(addStatusLabel, BorderLayout.CENTER);

        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createViewGradeTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // NORTH - 조회 폼
        JPanel searchFormPanel = new JPanel(new GridBagLayout());
        searchFormPanel.setBackground(Color.WHITE);
        searchFormPanel.setBorder(new EmptyBorder(0, 0, 12, 0)); // 아래 여백 12
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 8); // 컴포넌트 간 간격

        // 학번 레이블
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel studentIdLabel = new JLabel("학번");
        studentIdLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        searchFormPanel.add(studentIdLabel, gbc);

        // 학번 입력 필드
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        viewStudentIdField = new JTextField();
        viewStudentIdField.setPreferredSize(new Dimension(0, 32));
        viewStudentIdField.setBorder(BorderFactory.createLineBorder(Color.decode("#D1D5DB"), 1));
        viewStudentIdField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        searchFormPanel.add(viewStudentIdField, gbc);

        // 성적 조회 버튼
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        viewGradesButton = new JButton("성적 조회");
        viewGradesButton.setBackground(Color.decode("#16A34A"));
        viewGradesButton.setForeground(Color.WHITE);
        viewGradesButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        viewGradesButton.setPreferredSize(new Dimension(80, 32)); // 버튼 크기 조절
        viewGradesButton.setOpaque(true);
        viewGradesButton.setBorderPainted(false);
        searchFormPanel.add(viewGradesButton, gbc);

        panel.add(searchFormPanel, BorderLayout.NORTH);

        // CENTER - JTable
        String[] columnNames = {"과목", "점수"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 편집 불가
            }
        };
        JTable gradeTable = new JTable(tableModel);
        gradeTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        gradeTable.setRowHeight(28);
        gradeTable.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        gradeTable.getTableHeader().setBackground(Color.decode("#F3F4F6")); // 헤더 배경색
        gradeTable.setGridColor(Color.decode("#E5E7EB")); // 그리드 라인 색상
        gradeTable.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(gradeTable);
        scrollPane.setPreferredSize(new Dimension(600, 220));
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        // SOUTH - 조회 상태 및 평균
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.setBorder(new EmptyBorder(12, 0, 0, 0)); // 위 여백 12

        viewStatusLabel = new JLabel("", SwingConstants.LEFT);
        viewStatusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        southPanel.add(viewStatusLabel, BorderLayout.WEST);

        averageLabel = new JLabel("평균: -", SwingConstants.RIGHT);
        averageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        southPanel.add(averageLabel, BorderLayout.EAST);

        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addGrade() {
        String studentId = addStudentIdField.getText().trim();
        String subject = (String) addSubjectComboBox.getSelectedItem();
        String scoreText = addScoreField.getText().trim();

        addStatusLabel.setForeground(Color.RED); // 기본 색상은 빨간색

        if (studentId.isEmpty()) {
            addStatusLabel.setText("학번을 입력해주세요.");
            return;
        }

        int score;
        try {
            score = Integer.parseInt(scoreText);
            if (score < 0 || score > 100) {
                addStatusLabel.setText("점수는 0에서 100 사이의 정수여야 합니다.");
                return;
            }
        } catch (NumberFormatException e) {
            addStatusLabel.setText("점수는 정수로 입력해주세요.");
            return;
        }

        // API 호출을 위한 SwingWorker
        new SwingWorker<HttpResponse<String>, Void>() {
            @Override
            protected HttpResponse<String> doInBackground() throws Exception {
                String requestBody = String.format("{\"subject\":\"%s\",\"score\":%d}", subject, score);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/students/" + studentId + "/grades"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();
                return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }

            @Override
            protected void done() {
                try {
                    HttpResponse<String> response = get();
                    if (response.statusCode() == 200) {
                        JsonNode rootNode = objectMapper.readTree(response.body());
                        String addedSubject = rootNode.path("subject").asText();
                        int addedScore = rootNode.path("score").asInt();
                        addStatusLabel.setForeground(new Color(34, 139, 34)); // 초록색
                        addStatusLabel.setText(String.format("%s %d점을 추가했습니다.", addedSubject, addedScore));
                        addScoreField.setText("");
                        viewStudentIdField.setText(studentId); // 조회 탭 학번 업데이트
                    } else {
                        addStatusLabel.setText("요청 실패: 서버 응답 코드 " + response.statusCode());
                    }
                } catch (IOException | InterruptedException e) {
                    addStatusLabel.setText("서버에 연결하지 못했습니다.");
                    addStatusLabel.setForeground(Color.RED);
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof IOException || cause instanceof InterruptedException) {
                        addStatusLabel.setText("서버에 연결하지 못했습니다.");
                        addStatusLabel.setForeground(Color.RED);
                    } else {
                        addStatusLabel.setText("요청 처리 중 오류가 발생했습니다: " + cause.getMessage());
                        addStatusLabel.setForeground(Color.RED);
                    }
                }
            }
        }.execute();
    }

    private void viewGrades() {
        String studentId = viewStudentIdField.getText().trim();

        viewStatusLabel.setForeground(Color.RED); // 기본 색상은 빨간색

        if (studentId.isEmpty()) {
            viewStatusLabel.setText("학번을 입력해주세요.");
            return;
        }

        // API 호출을 위한 SwingWorker
        new SwingWorker<HttpResponse<String>, Void>() {
            @Override
            protected HttpResponse<String> doInBackground() throws Exception {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/students/" + studentId + "/grades"))
                        .GET()
                        .build();
                return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }

            @Override
            protected void done() {
                try {
                    HttpResponse<String> response = get();
                    if (response.statusCode() == 200) {
                        JsonNode rootNode = objectMapper.readTree(response.body());
                        ArrayNode scoresNode = (ArrayNode) rootNode.path("scores");
                        double average = rootNode.path("average").asDouble();

                        tableModel.setRowCount(0); // 기존 데이터 초기화
                        if (scoresNode.isEmpty()) {
                            averageLabel.setText("평균: -");
                        } else {
                            for (JsonNode scoreEntry : scoresNode) {
                                tableModel.addRow(new Object[]{scoreEntry.path("subject").asText(), scoreEntry.path("score").asInt()});
                            }
                            averageLabel.setText(String.format("평균: %.1f", average));
                        }
                        viewStatusLabel.setForeground(new Color(34, 139, 34)); // 초록색
                        viewStatusLabel.setText("성적을 조회했습니다.");
                    } else {
                        tableModel.setRowCount(0);
                        averageLabel.setText("평균: -");
                        viewStatusLabel.setText("요청 실패: 서버 응답 코드 " + response.statusCode());
                    }
                } catch (IOException | InterruptedException e) {
                    tableModel.setRowCount(0);
                    averageLabel.setText("평균: -");
                    viewStatusLabel.setText("서버에 연결하지 못했습니다.");
                    viewStatusLabel.setForeground(Color.RED);
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof IOException || cause instanceof InterruptedException) {
                        tableModel.setRowCount(0);
                        averageLabel.setText("평균: -");
                        viewStatusLabel.setText("서버에 연결하지 못했습니다.");
                        viewStatusLabel.setForeground(Color.RED);
                    } else {
                        tableModel.setRowCount(0);
                        averageLabel.setText("평균: -");
                        viewStatusLabel.setText("요청 처리 중 오류가 발생했습니다: " + cause.getMessage());
                        viewStatusLabel.setForeground(Color.RED);
                    }
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeGUI gui = new StudentGradeGUI();
            gui.setVisible(true);
        });
    }
}
