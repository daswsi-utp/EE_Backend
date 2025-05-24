package com.orchestrator_service.client.controller;

import com.orchestrator_service.client.dto.request.ClientRequest;
import com.orchestrator_service.client.dto.response.ClientResponse;
import com.orchestrator_service.client.service.ClientImpl.ServiceClientImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orchestrator")
@RequiredArgsConstructor
public class ClientController {

    private final ServiceClientImpl serviceClient;

    @PostMapping("/register")
    public ResponseEntity<ClientResponse> registerClient(@RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = serviceClient.createClient(clientRequest);
        return ResponseEntity.ok(clientResponse);
    }
}
