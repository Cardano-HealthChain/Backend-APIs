package com.cardano.healthchain.cardano.healthchain.clinics.doctors;

import com.cardano.healthchain.cardano.healthchain.user.UserService;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserDataProfileResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final PermissionService permissionService;
    private final UserService userService;
    public DoctorController(DoctorService doctorService, PermissionService permissionService, UserService userService) {
        this.doctorService = doctorService;
        this.permissionService = permissionService;
        this.userService = userService;
    }
    @PostMapping("users/search")
    public ArrayList<UserDataProfileResponse> getUsersSimilarToSearchTerm(@RequestParam String searchTerm, int page){
        return userService.getUsersSimilarToSearchTerm(searchTerm,page);
    }
    @PostMapping("request-read-permission")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void doctorRequestPermissionOnBehalfOfClinicRead(Principal principal, @RequestParam String userId){
        permissionService.doctorRequestUserPermissionRead(principal.getName(),userId);
    }
    @PostMapping("request-write-permission")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void doctorRequestPermissionOnBehalfOfClinicWrite(Principal principal, @RequestParam String userId){
        permissionService.doctorRequestUserPermissionWrite(principal.getName(),userId);
    }
    @PostMapping("request-readwrite-permission")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void doctorRequestPermissionOnBehalfOfClinicReadAndWrite(Principal principal, @RequestParam String userId){
        permissionService.doctorRequestUserPermissionReadAndWrite(principal.getName(),userId);
    }
    @PostMapping("upload-record")
    @ResponseStatus(HttpStatus.CREATED)
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