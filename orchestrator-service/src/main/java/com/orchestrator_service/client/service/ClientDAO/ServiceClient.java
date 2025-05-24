package com.orchestrator_service.client.service.ClientDAO;

import com.orchestrator_service.client.dto.request.ClientRequest;
import com.orchestrator_service.client.dto.response.ClientResponse;

public interface ServiceClient {
    public ClientResponse createClient(ClientRequest clientRequest);
    public ClientResponse updateClient(ClientRequest clientRequest);
}
