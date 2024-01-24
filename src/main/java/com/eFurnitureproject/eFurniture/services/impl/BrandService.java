package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.BrandConverter;
import com.eFurnitureproject.eFurniture.dtos.BrandDto;
import com.eFurnitureproject.eFurniture.models.Brand;
import com.eFurnitureproject.eFurniture.repositories.BrandRepository;
import com.eFurnitureproject.eFurniture.services.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;
    @Override
    @Transactional
    public Brand createBrand(BrandDto brandDto) {
        String generatedCode = generateCodeFromName(brandDto.getName());
        Brand newBrand = Brand
                .builder()
                .name(brandDto.getName())
                .logo(brandDto.getLogo())
                .websiteUrl(brandDto.getWebsiteUrl())
                .code(generatedCode)
                .build();
        return brandRepository.save(newBrand);
    }
    // Phương thức hỗ trợ để tạo mã từ tên
    private String generateCodeFromName(String name) {
        // Thực hiện logic tạo mã của bạn ở đây
        // Đơn giản, bạn có thể sử dụng một logic cơ bản như loại bỏ khoảng trắng và chuyển đổi thành chữ in hoa
        return name.replaceAll("\\s", "-").toLowerCase();
    }

    @Override
    @Transactional
    public Brand updateBrand(Long id, BrandDto brandDto) {
        String generatedCode = generateCodeFromName(brandDto.getName());
        Brand existingBrand = getBrandById(id);
        BrandConverter.toEntity(brandDto, existingBrand);
        existingBrand.setCode(generatedCode);
        existingBrand = brandRepository.save(existingBrand);
        return existingBrand;
    }
    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(long id) {
        return brandRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Brand not found"));
    }


}
