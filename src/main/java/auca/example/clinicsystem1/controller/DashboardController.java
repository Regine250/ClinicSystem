package auca.example.clinicsystem1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.example.clinicsystem1.service.DashboardService;

@RestController
@RequestMapping({"/api/dashboard", "/api/dashboards"})
public class DashboardController {

    @Autowired
    private DashboardService dashboardServ;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        return new ResponseEntity<>(dashboardServ.getSummary(), HttpStatus.OK);
    }
}
