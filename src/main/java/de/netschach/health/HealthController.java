package de.netschach.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
class HealthController {

    @Autowired
    private BuildProperties buildProperties;

    @GetMapping
    String health() {
        return "Version " + buildProperties.getVersion();
    }
}
