package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.BrandResponse;
import com.eFurnitureproject.eFurniture.components.LocalizationUtils;
import com.eFurnitureproject.eFurniture.dtos.BrandDto;
import com.eFurnitureproject.eFurniture.models.Brand;
import com.eFurnitureproject.eFurniture.services.impl.BrandService;
import com.eFurnitureproject.eFurniture.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/brand")
@RequiredArgsConstructor
@CrossOrigin
public class BrandController {
    private final BrandService brandService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<BrandResponse> createBrand(
            @Valid @RequestBody BrandDto brandDto,
            BindingResult result
            ){
        BrandResponse brandResponse = new BrandResponse();
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            brandResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            brandResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(brandResponse);
        }
        Brand brand = brandService.createBrand(brandDto);
        brandResponse.setBrand(brand);
        return ResponseEntity.ok(brandResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandDto brandDto,
            BindingResult result
    ){
        BrandResponse brandResponse = new BrandResponse();
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            brandResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_BRAND_FAILED));
            brandResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(brandResponse);
        }
        Brand brand = brandService.updateBrand(id, brandDto);
        brandResponse.setBrand(brand);
        return ResponseEntity.ok(brandResponse);
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<Brand>> getAllBrand() {
        List<Brand> brands = brandService.getAllBrand();
        return ResponseEntity.ok(brands);
    }
}
