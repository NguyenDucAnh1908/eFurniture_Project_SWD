package com.eFurnitureproject.eFurniture.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eFurnitureproject.eFurniture.dtos.DesignDto;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.repositories.DesignRepository;
import com.eFurnitureproject.eFurniture.repositories.ProjectBookingRepository;
import com.eFurnitureproject.eFurniture.services.IDesignService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DesignService implements IDesignService {

    private final DesignRepository designRepository;
    private final Cloudinary cloudinary;


    @Override
    public List<Design> getAllDesigns() {
        return designRepository.findAll();
    }

    @Override
    public Optional<Design> getDesignById(Long id) {
        return designRepository.findById(id);
    }

    @Override
    public Design createDesign(Design design) {
        return designRepository.save(design);
    }

    private String uploadToCloudinaryAndReturnURL(byte[] fileData) {
        try {
            Map uploadResult = cloudinary.uploader().upload(fileData, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Design updateDesign(Long id, Design designDetails) {
        return designRepository.save(designDetails);
    }

    @Override
    public void deleteDesign(Long id) {
        Optional<Design> designOptional = designRepository.findById(id);
        if (designOptional.isPresent()) {
            Design design = designOptional.get();
            design.setStatus("Failed");
            designRepository.save(design);
        }
    }

    @Override
    public List<Design> getDesignsByProjectBookingId(Long projectId) {
        return designRepository.findByProjectBookingId(projectId);
    }

    @Override
    public String findLatestCodeDesign() {
        // Thực hiện truy vấn cơ sở dữ liệu để lấy mã thiết kế lớn nhất
        Optional<Design> latestDesignOptional = designRepository.findTopByOrderByCodeDesignDesc();
        if (latestDesignOptional.isPresent()) {
            String latestCodeDesign = latestDesignOptional.get().getCodeDesign();
            return latestCodeDesign != null ? latestCodeDesign : "DES00000";
        }
        return "DES00000";
    }


}
