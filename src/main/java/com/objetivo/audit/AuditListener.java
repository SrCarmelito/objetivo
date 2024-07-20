package com.objetivo.audit;

import com.objetivo.auth.Usuario;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

public class AuditListener {

    @PrePersist
    public void setCreatedOn(Auditable auditable) {

        final AuditInfo audit = Optional.ofNullable(auditable.getAudit()).orElse(new AuditInfo());
        Usuario user =  (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        audit.setDataCriacao(LocalDateTime.now());
        audit.setDataAlteracao(LocalDateTime.now());
        if (user == null) {
            audit.setUsuarioCriacao("System");
            audit.setUsuarioAlteracao("System");
        } else {
            audit.setUsuarioCriacao(user.getNome());
            audit.setUsuarioAlteracao(user.getNome());
        }
        auditable.setAudit(audit);
    }

    @PreUpdate
    public void setUpdatedOn(Auditable auditable) {

        if (isNull(auditable.getAudit())) {
            setCreatedOn(auditable);
        } else {
            auditable.getAudit().setUsuarioAlteracao(SecurityContextHolder.getContext().getAuthentication().getName());
            auditable.getAudit().setDataAlteracao(LocalDateTime.now());
        }

    }

}

