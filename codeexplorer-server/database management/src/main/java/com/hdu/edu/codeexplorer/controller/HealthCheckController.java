package com.hdu.edu.codeexplorer.controller;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/health")
public class HealthCheckController {


    @RequestMapping("/check")
    public HashMap<String, Object> healthCheck() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", 200);
        result.put("message", "");
        return result;
    }

}
