package com.eFurnitureproject.eFurniture.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eFurnitureproject.eFurniture.Responses.DesignResponse;
import com.eFurnitureproject.eFurniture.converter.DesignConverter;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.repositories.ProjectBookingRepository;
import com.eFurnitureproject.eFurniture.services.IDesignService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/designs")
@RequiredArgsConstructor
public class DesignController {

    private final IDesignService designService;
    private final Cloudinary cloudinary;
    private final ProjectBookingRepository projectBookingRepository;

    @CrossOrigin
    @GetMapping("/get-designs-by-project/{projectId}")
    public ResponseEntity<List<Design>> getDesignsByProjectBookingId(@PathVariable Long projectId) {
        List<Design> designs = designService.getDesignsByProjectBookingId(projectId);
        return ResponseEntity.ok(designs);
    }
    @CrossOrigin
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Design> getDesignById(@PathVariable Long id) {
        Optional<Design> design = designService.getDesignById(id);
        return design.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping("/create-design")
    public ResponseEntity<DesignResponse> createDesign(@RequestParam("imageUrls") MultipartFile imageUrls,
                                                       @RequestParam("status") String status,
                                                       @RequestParam("note") String note,
                                                       @RequestParam("staffName") String staffName,
                                                       @RequestParam("fileData") MultipartFile urlFileData,
                                                       @RequestParam("projectBookingId") Long projectBookingId) {
        ProjectBooking projectBooking = projectBookingRepository.findById(projectBookingId)
                .orElseThrow(() -> new EntityNotFoundException("ProjectBooking not found with id: " + projectBookingId));

        String latestCodeDesign = designService.findLatestCodeDesign();

        String newCodeDesign = incrementCodeDesign(latestCodeDesign);

        String imageUrl = uploadToCloudinaryAndReturnURL(imageUrls);
        byte[] fileData = getFileDataBytes(urlFileData);
        String fileName = urlFileData.getOriginalFilename();
        String fileType = getFileType(urlFileData);


        Design newDesign = Design.builder()
                .codeDesign(newCodeDesign)
                .imageUrls(imageUrl)
                .status(status)
                .note(note)
                .fileData(fileData)
                .fileName(fileName)
                .fileType(fileType)
                .staffName(staffName)
                .projectBooking(projectBooking)
                .build();

        Design savedDesign = designService.createDesign(newDesign);
        DesignResponse designResponse = DesignConverter.toResponse(savedDesign);

        return ResponseEntity.status(HttpStatus.CREATED).body(designResponse);
    }


    private String incrementCodeDesign(String latestCodeDesign) {
        String prefix = "DES";
        int latestNumber = Integer.parseInt(latestCodeDesign.substring(prefix.length())) + 1;
        return prefix + String.format("%05d", latestNumber);
    }



    private String getFileType(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // Lấy phần sau dấu chấm
        }
        return null;
    }

    private String uploadToCloudinaryAndReturnURL(MultipartFile file) {
        try {Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] getFileDataBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/update-design/{id}")
    public ResponseEntity<DesignResponse> updateDesign(@PathVariable Long id,
                                                       @RequestParam(value = "imageUrls", required = false) MultipartFile imageUrls,
                                                       @RequestParam(value = "status", required = false) String status,
                                                       @RequestParam(value = "note", required = false) String note,
                                                       @RequestParam(value = "staffName", required = false) String staffName,
                                                       @RequestParam(value = "fileData", required = false) MultipartFile urlFileData) {
        Design existingDesign = designService.getDesignById(id)
                .orElseThrow(() -> new EntityNotFoundException("Design not found with id: " + id));

        if (imageUrls != null) {
            String imageUrl = uploadToCloudinaryAndReturnURL(imageUrls);
            existingDesign.setImageUrls(imageUrl);
        }
        if (status != null) {
            existingDesign.setStatus(status);
        }
        if (note != null) {
            existingDesign.setNote(note);
        }
        if (staffName != null) {
            existingDesign.setStaffName(staffName);
        }
        if (urlFileData != null) {
            try {
                byte[] fileData = urlFileData.getBytes();
                existingDesign.setFileData(fileData);
                String fileName = urlFileData.getOriginalFilename();
                String fileType = getFileType(urlFileData);
                existingDesign.setFileName(fileName);
                existingDesign.setFileType(fileType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Design updatedDesign = designService.updateDesign(id, existingDesign);
        DesignResponse designResponse = DesignConverter.toResponse(updatedDesign);

        return ResponseEntity.ok(designResponse);
    }

    @CrossOrigin
    @GetMapping("/download-file/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        Design design = designService.getDesignById(id)
                .orElseThrow(() -> new EntityNotFoundException("Design not found with id: " + id));

        try {
            String fileName = design.getFileName() + "." + design.getFileType();
            byte[] fileData = design.getFileData();

            ByteArrayResource resource = new ByteArrayResource(fileData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(fileData.length)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to download file. Please try again later.");
        }
    }



    @CrossOrigin
    @DeleteMapping("/delete-design/{id}")
    public ResponseEntity<String> deleteDesign(@PathVariable Long id) {
        try {
            Optional<Design> design = designService.getDesignById(id);
            if (design.isPresent()) {
                designService.deleteDesign(id);
                return ResponseEntity.ok("Design with ID " + id + " has been successfully deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Design not found with ID " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete design with ID " + id + ". Please try again later.");
        }
    }

@CrossOrigin
    @GetMapping("/download-pdf/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {

        Design design = designService.getDesignById(id)
                .orElseThrow(() -> new EntityNotFoundException("Design not found with id: " + id));

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "design.pdf");

        // Return the byte array of the PDF file
        return new ResponseEntity<>(design.getFileData(), headers, HttpStatus.OK);
    }

}
