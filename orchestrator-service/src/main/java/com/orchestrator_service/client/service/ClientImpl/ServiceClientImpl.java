package com.orchestrator_service.client.service.ClientImpl;

import com.orchestrator_service.client.dto.request.AuthFeignRequest;
import com.orchestrator_service.client.dto.request.ClientFeignRequest;
import com.orchestrator_service.client.dto.request.ClientRequest;
import com.orchestrator_service.client.dto.response.AuthFeignResponse;
import com.orchestrator_service.client.dto.response.ClientFeingResponse;
import com.orchestrator_service.client.dto.response.ClientResponse;
import com.orchestrator_service.client.feign.AuthFeign;
import com.orchestrator_service.client.feign.ClientFeign;
import com.orchestrator_service.client.service.ClientDAO.ServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceClientImpl implements ServiceClient {

    private final ClientFeign clienteFeign;
    private final AuthFeign authFeign;

    @Override
    public ClientResponse createClient(ClientRequest clientRequest) {
        try {
            // 1. Crear usuario en auth-service
            AuthFeignRequest authRequest = AuthFeignRequest.builder()
                    .username(clientRequest.getUsername())
                    .password(clientRequest.getPassword())
                    .rol(clientRequest.getRol())
                    .build();

            System.out.println(authRequest);

            AuthFeignResponse authFeignResponse = authFeign.createUser(authRequest);

            System.out.println(authFeignResponse);

            // 2. Crear cliente en client-service
            ClientFeignRequest clientFeignRequest = ClientFeignRequest.builder()
                    .userCode(authFeignResponse.getUserCode())
                    .firstName(clientRequest.getFirstName())
                    .lastName(clientRequest.getLastName())
                    .email(clientRequest.getEmail())
                    .phoneNumber(clientRequest.getPhoneNumber())
                    .build();

            System.out.println(clientFeignRequest);

            ClientFeingResponse clientFeingResponse = clienteFeign.createClient(clientFeignRequest);

            System.out.println(clientFeingResponse);

            // 3. Construir y devolver respuesta combinada
            return ClientResponse.builder()
                    .usercode(authFeignResponse.getUserCode())
                    .username(authFeignResponse.getUsername())
                    .active(authFeignResponse.isActive())
                    .rol(authFeignResponse.getRol())
                    .fullname(clientFeingResponse.getFullName())
                    .email(clientFeingResponse.getEmail())
                    .phoneNumber(clientFeingResponse.getPhoneNumber())
                    .registrationDate(clientFeingResponse.getRegistrationDate())
                    .purchaseCount(clientFeingResponse.getPurchaseCount())
                    .totalSpent(clientFeingResponse.getTotalSpent())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error durante el registro de usuario y cliente: " + e.getMessage(), e);
        }
    }


    @Override
    public ClientResponse updateClient(ClientRequest clientRequest) {
        return null;
    }
}
