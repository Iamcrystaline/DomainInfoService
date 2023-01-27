package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DomainInfoController {

    private DomainInfoService service;

    @Autowired
    public DomainInfoController(DomainInfoService service) {
        this.service = service;
    }

    @GetMapping("/domainInfo")
    @CrossOrigin
    public String getDomainInfo(@RequestParam("domainName") String domainName) {
        return service.getDomainInfo(domainName);
    }
}
