package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.ProductImageDto;
import com.eFurnitureproject.eFurniture.models.ProductImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageService {
    //ProductImages createProductImage(Long productId, ProductImageDto productImageDTO, MultipartFile file) throws Exception;
    List<ProductImageDto> uploadFiles(List<MultipartFile> gifs, Long productId);
}
