package com.objetivo.audit;

public interface Auditable {

    AuditInfo getAudit();
    void setAudit(AuditInfo auditInfo);

}
