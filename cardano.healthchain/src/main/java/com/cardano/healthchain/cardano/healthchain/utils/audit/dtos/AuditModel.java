package com.cardano.healthchain.cardano.healthchain.utils.audit.dtos;

public class AuditModel {
    private String actor_type;
    private String actor_reference;
    private String action_performed;
    private String details;
    public AuditModel() {
    }
    public AuditModel(String actor_type, String actor_reference, String action_performed, String details) {
        this.actor_type = actor_type;
        this.actor_reference = actor_reference;
        this.action_performed = action_performed;
        this.details = details;
    }

    public String getActor_type() {
        return actor_type;
    }

    public void setActor_type(String actor_type) {
        this.actor_type = actor_type;
    }

    public String getActor_reference() {
        return actor_reference;
    }

    public void setActor_reference(String actor_reference) {
        this.actor_reference = actor_reference;
    }

    public String getAction_performed() {
        return action_performed;
    }

    public void setAction_performed(String action_performed) {
        this.action_performed = action_performed;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "AuditModel{" +
                "actor_type='" + actor_type + '\'' +
                ", actor_reference='" + actor_reference + '\'' +
                ", action_performed='" + action_performed + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}