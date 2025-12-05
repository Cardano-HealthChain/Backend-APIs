package com.cardano.healthchain.cardano.healthchain.utils.audit;

import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditModel;

import java.util.ArrayList;

public interface AuditRepositoryI {
    void logEvent(ACTOR_TYPE actorType, String actorReference, String actionPerformed, String details);
    ArrayList<AuditModel> getAuditsByActorReference(int page,String actorReference);
}
