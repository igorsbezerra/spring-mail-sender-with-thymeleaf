package com.example.mailsender.service;

import com.example.mailsender.mail.StudentsMailComponent;
import com.example.mailsender.model.Student;
import com.example.mailsender.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;
    private StudentsMailComponent studentsMailComponent;

    public Student save(Student student) {
        this.studentRepository.save(student);
        this.studentsMailComponent.sendWelcomeEmail(student);
        return student;
    }
}
