package com.objetivo.audit;

import com.objetivo.auth.Usuario;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

import static java.util.Objects.nonNull;

public class EnversListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Revision revision = (Revision) revisionEntity;
        Usuario user = new Usuario();

        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            user.setLogin("anonymousUser");
            user.setNome("anonymousUser");
            user.setId(999999999L);
        } else {
            user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        revision.setUserName(user.getNome());
        revision.setLogin(user.getLogin());
        revision.setRemoteIpAddress(getIpFromRequest());
        revision.setUserId(user.getId());
        revision.setRevisionDate(new Date());
    }

    private static String getIpFromRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (nonNull(requestAttributes) && requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes)requestAttributes).getRequest().getRemoteAddr();
        }
        return "Não Obtido!!!";
    }

}
