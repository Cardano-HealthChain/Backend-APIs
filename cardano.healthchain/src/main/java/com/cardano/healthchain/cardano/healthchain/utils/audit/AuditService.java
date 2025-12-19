package com.cardano.healthchain.cardano.healthchain.utils.audit;

import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.audit.enums.ActorTypeEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuditService {
    private final AuditRepositoryI auditRepository;
    public AuditService(AuditRepositoryI auditRepository) {
        this.auditRepository = auditRepository;
    }
    public void logAuditEvent(ActorTypeEnum actorType, String actorReference, String action_performed, String details){
        auditRepository.logEvent(actorType,actorReference,action_performed,details);
    }
    public ArrayList<AuditDataResponse> getAuditsByActorReference(String actorReference, int page){
        return auditRepository.getAuditsByActorReference(page,actorReference);
    }
}