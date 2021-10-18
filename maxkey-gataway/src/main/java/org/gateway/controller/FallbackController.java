package org.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {
    @RequestMapping("/defaultFallback")
    public Map<String , Object> defaultFallback() {
        Map<String , Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("message", "服务异常");
        return map;
    }
}
