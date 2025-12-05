package com.cardano.healthchain.cardano.healthchain.utils.audit;

import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public class AuditRepositoryImpl implements AuditRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public AuditRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void logEvent(ACTOR_TYPE actorType, String actorReference, String actionPerformed, String details) {
        String auditInsertSql = "INSERT INTO audit_logs (actor_type,actor_reference, action_performed, details) values = (?,?,?,?)";
        Object[] args= new Object[]{actorType,actorReference,actionPerformed,details};
        jdbcTemplate.update(auditInsertSql,args);
    }
    @Override
    public ArrayList<AuditModel> getAuditsByActorReference(int page,String actorReference) {
        int pageNumber = Math.max(page, 1); // at least 1
        int offset = (pageNumber - 1) * 5;
        String getAuditByActorReferenceSql = "SELECT * FROM audit_logs ORDER BY created_at DESC OFFSET ? LIMIT 5";
        Object[] args = new Object[]{actorReference,offset};
        return (ArrayList<AuditModel>) jdbcTemplate.query(getAuditByActorReferenceSql,new BeanPropertyRowMapper<>(AuditModel.class),args);
    }
}
