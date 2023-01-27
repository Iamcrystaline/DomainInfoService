package com.example.demo;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DomainInfoParser {

    public String parseDomainInfo(String domainInfo) {
        if (domainInfo.contains("\"dataError\": \"MISSING_WHOIS_DATA\"")) {
            return "Not found";
        }
        JSONObject JSONObj = new JSONObject(domainInfo);
        JSONObject resultJSONObj = new JSONObject();
        JSONObject whoisRecord = JSONObj.getJSONObject("WhoisRecord");
        String registrarName = whoisRecord.getString("registrarName");
        String expiresDateNormalized = whoisRecord.getJSONObject("registryData")
                .getString("expiresDateNormalized");
        resultJSONObj.put("registrarName", registrarName);
        resultJSONObj.put("expiresDateNormalized", expiresDateNormalized);
        return resultJSONObj.toString();
    }
}
