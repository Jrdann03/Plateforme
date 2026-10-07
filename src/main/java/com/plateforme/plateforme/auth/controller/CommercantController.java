package com.plateforme.plateforme.auth.controller;

import com.plateforme.plateforme.auth.dto.CreateCommercantRequest;
import com.plateforme.plateforme.auth.entity.Commercant;
import com.plateforme.plateforme.auth.service.CommercantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commercants")
@RequiredArgsConstructor
public class CommercantController {

    private final CommercantService commercantService;

    @GetMapping("/h")
    public String Hello () {
        return "hello depuis springboot";
    }

    @PostMapping
    public ResponseEntity<Commercant> create(
            @Valid @RequestBody CreateCommercantRequest request
    ) {
        Commercant commercant = commercantService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commercant);
    }
}