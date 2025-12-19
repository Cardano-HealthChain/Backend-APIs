package com.cardano.healthchain.cardano.healthchain.utils.audit;

import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.audit.enums.ActorTypeEnum;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Repository
public class AuditRepositoryImpl implements AuditRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public AuditRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void logEvent(ActorTypeEnum actorType, String actorReference, String actionPerformed, String details) {
        String auditInsertSql = "INSERT INTO audit_logs (actor_type,actor_reference,action_performed,details,created_at) VALUES (?,?,?,?,?)";
        Object[] args= new Object[]{actorType.name(), actorReference, actionPerformed, details, LocalDateTime.now()};
        jdbcTemplate.update(auditInsertSql,args);
    }
    @Override
    public ArrayList<AuditDataResponse> getAuditsByActorReference(int page, String actorReference) {
        int pageNumber = Math.max(page, 1);
        int offset = (pageNumber - 1) * 5;
        String getAuditByActorReferenceSql = "SELECT * FROM audit_logs WHERE actor_reference = ? ORDER BY created_at DESC LIMIT 5 OFFSET ?";
        Object[] args = new Object[]{actorReference,offset};
        return (ArrayList<AuditDataResponse>) jdbcTemplate.query(getAuditByActorReferenceSql,new BeanPropertyRowMapper<>(AuditDataResponse.class),args);
    }
}