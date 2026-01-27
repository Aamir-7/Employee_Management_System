package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.repository.EmployeeRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class EmployeeFileService {

    private final EmployeeRepo employeeRepo;

    private static final String PHOTO_DIR= "uploads/photos/";
    private static final String RESUME_DIR= "uploads/resumes/";

    public EmployeeFileService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public String uploadPhoto(UUID employeeId, MultipartFile file) {

        validateImage(file);

        Employee emp=employeeRepo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(()->new RuntimeException("employee not found"));

        String fileName="photo_" + employeeId + getExtension(file);
        Path path= Paths.get(PHOTO_DIR +fileName);

        saveFile(path,file);

        emp.setPhotoPath(path.toString());
        employeeRepo.save(emp);
        
        return "Photo uploaded successfully";
    }



    /*
           Helpers
      */

    private String getExtension(MultipartFile file) {
        String name= file.getOriginalFilename();
        return name != null && name.contains(".")
                ? name.substring(name.lastIndexOf("."))
                : "";
    }

    private void saveFile(Path path, MultipartFile file) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path,file.getBytes());

        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }

    private void validateImage(MultipartFile file) {
        System.out.println("CONTENT TYPE: " + file.getContentType());

        if (file.isEmpty()){
            throw new RuntimeException("file can not be empty ");
        }

        String type= file.getContentType();
        if (type == null ||
                (!type.equalsIgnoreCase("image/jpeg")
                        && !type.equalsIgnoreCase("image/jpg")
                        && !type.equalsIgnoreCase("image/png"))) {

            throw new RuntimeException("Only JPG or PNG allowed");
        }
    }
}
