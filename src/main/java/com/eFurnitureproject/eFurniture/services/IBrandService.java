package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.BrandDto;
import com.eFurnitureproject.eFurniture.models.Brand;

import java.util.List;

public interface IBrandService {
    Brand createBrand (BrandDto brandDto);
    Brand updateBrand(Long id, BrandDto brandDto);
    Brand getBrandById(long id);
    List<Brand> getAllBrand();
}
