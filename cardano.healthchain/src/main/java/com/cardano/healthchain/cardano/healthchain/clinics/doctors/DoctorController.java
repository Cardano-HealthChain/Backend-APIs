package com.cardano.healthchain.cardano.healthchain.clinics.doctors;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final PermissionService permissionService;
    public DoctorController(DoctorService doctorService, PermissionService permissionService) {
        this.doctorService = doctorService;
        this.permissionService = permissionService;
    }
    @PostMapping("request-read-permission")
    public void doctorRequestPermissionOnBehalfOfClinicRead(Principal principal, @RequestParam String userId){
        permissionService.doctorRequestUserPermissionRead(principal.getName(),userId);
    }
    @PostMapping("request-write-permission")
    public void doctorRequestPermissionOnBehalfOfClinicWrite(Principal principal, @RequestParam String userId){
        permissionService.doctorRequestUserPermissionWrite(principal.getName(),userId);
    }
    @PostMapping("request-readwrite-permission")
    public void doctorRequestPermissionOnBehalfOfClinicReadAndWrite(Principal principal, @RequestParam String userId){
        permissionService.doctorRequestUserPermissionReadAndWrite(principal.getName(),userId);
    }
    @PostMapping("upload-record")
    public void doctorUploadRecordForUser(Principal principal, @RequestParam String userId, MedicalDataUploadRequest medicalDataUploadRequest){
        doctorService.doctorUploadRecordForUser(principal.getName(),userId,medicalDataUploadRequest);
    }
    @PostMapping("view-user-records")
    public ArrayList<MedicalDataResponse> doctorViewRecordsForUser(Principal principal, @RequestParam String userId, @RequestParam int page){
        return doctorService.doctorViewRecordsForUser(principal.getName(),userId, page);
    }
    @PostMapping("view-user-recordsfiltered")
    public ArrayList<MedicalDataResponse> doctorViewRecordsForUserFiltered(Principal principal, @RequestParam String userId, @RequestParam String filterTerm, @RequestParam int page){
        return doctorService.doctorViewRecordsForUserFiltered(principal.getName(),userId,filterTerm,page);
    }
}