package com.cardano.healthchain.cardano.healthchain.utils.audit;

import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.audit.enums.ActorTypeEnum;

import java.util.ArrayList;

public interface AuditRepositoryI {
    void logEvent(ActorTypeEnum actorType, String actorReference, String actionPerformed, String details);
    ArrayList<AuditDataResponse> getAuditsByActorReference(int page, String actorReference);
}
