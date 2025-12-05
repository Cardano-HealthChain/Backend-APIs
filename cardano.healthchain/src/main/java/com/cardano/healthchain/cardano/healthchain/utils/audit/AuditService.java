package com.cardano.healthchain.cardano.healthchain.utils.audit;

import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuditService {
    private final AuditRepositoryI auditRepository;
    public AuditService(AuditRepositoryI auditRepository) {
        this.auditRepository = auditRepository;
    }
    public void logAuditEvent(ACTOR_TYPE actorType, String actorReference, String action_performed, String details){
        auditRepository.logEvent(actorType,actorReference,action_performed,details);
    }
    public ArrayList<AuditModel> getAuditsByActorReference(int page, String actorReference){
        return auditRepository.getAuditsByActorReference(page,actorReference);
    }
}