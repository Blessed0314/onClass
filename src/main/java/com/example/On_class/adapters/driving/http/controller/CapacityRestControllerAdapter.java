package com.example.On_class.adapters.driving.http.controller;

import com.example.On_class.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.On_class.adapters.driving.http.dto.response.CapacityResponse;
import com.example.On_class.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.example.On_class.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.example.On_class.domain.api.ICapacityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/capacity")
@RequiredArgsConstructor
public class CapacityRestControllerAdapter {
    private final ICapacityServicePort capacityServicePort;
    private final ICapacityRequestMapper capacityRequestMapper;
    private final ICapacityResponseMapper capacityResponseMapper;

    @PostMapping("/")
    public ResponseEntity<Void> addCapacity(@Valid @RequestBody AddCapacityRequest request){
        capacityServicePort.saveCapacity(capacityRequestMapper.addRequestToCapacity(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<CapacityResponse>> getCapacityList(@RequestParam Integer page, @RequestParam Integer size, @RequestParam boolean orderFlag, boolean ascendingFlag){
        return ResponseEntity.ok(capacityResponseMapper.toCapacityResponseList(capacityServicePort.getAllCapacities(page, size, orderFlag, ascendingFlag)));
    }
}
