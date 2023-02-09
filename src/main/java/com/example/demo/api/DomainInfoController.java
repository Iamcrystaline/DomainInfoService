package com.example.demo.api;

import com.example.demo.api.registration_info_services.DomainInfoService;
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