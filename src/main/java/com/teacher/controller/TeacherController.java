package com.teacher.controller;

import com.teacher.model.Teacher;
import com.teacher.repository.TeacherRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                 @RequestParam String password,
                                 HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        Teacher teacher = teacherRepository.findByUsername(username);
        
        if (teacher != null && BCrypt.checkpw(password, teacher.getPassword())) {
            session.setAttribute("user_id", teacher.getId());
            session.setAttribute("teacher_name", teacher.getTeacherName());
            
            response.put("success", true);
            response.put("message", "登录成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String teacherName,
                                    @RequestParam String school,
                                    @RequestParam String subject,
                                    @RequestParam String phone,
                                    @RequestParam String className,
                                    @RequestParam String username,
                                    @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        
        if (teacherRepository.existsByUsername(username)) {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return ResponseEntity.ok(response);
        }
        
        Teacher teacher = new Teacher();
        teacher.setTeacherName(teacherName);
        teacher.setSchool(school);
        teacher.setSubject(subject);
        teacher.setPhone(phone);
        teacher.setClassName(className);
        teacher.setUsername(username);
        teacher.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        
        teacherRepository.save(teacher);
        
        response.put("success", true);
        response.put("message", "注册成功");
        return ResponseEntity.ok(response);
    }
} 