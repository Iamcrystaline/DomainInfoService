package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DomainInfoController {

    private final DomainInfoService service;

    @GetMapping("/domainInfo")
    @CrossOrigin
    public String getDomainInfo(@RequestParam("domainName") String domainName) {
        return service.getDomainInfo(new Domain(domainName));
    }
}
