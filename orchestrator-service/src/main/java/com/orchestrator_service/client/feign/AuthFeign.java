package com.orchestrator_service.client.feign;


import com.orchestrator_service.client.dto.request.AuthFeignRequest;
import com.orchestrator_service.client.dto.response.AuthFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "http://localhost:8081")
public interface AuthFeign {

    @PostMapping("/auth/register")
    AuthFeignResponse createUser(@RequestBody AuthFeignRequest authFeignRequest);
}
