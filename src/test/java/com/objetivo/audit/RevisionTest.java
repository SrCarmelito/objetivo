package com.objetivo.audit;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RevisionTest {

    @Test
    public void revisionTest() {
        Revision revision = new Revision();
        revision.setRevisionNumber(1L);
        revision.setRevisionDate(new Date(2022, 11, 11));
        revision.setLogin("junior");
        revision.setUserName("Junior");
        revision.setRemoteIpAddress("0.0.0.0.0.1");
        revision.setUserId(1L);
        assertTrue(revision.getRevisionNumber().equals(1L));
        assertTrue(revision.getRevisionDate().after(Date.from(Instant.now())));
        assertTrue(revision.getLogin().equals("junior"));
        assertTrue(revision.getUserName().equals("Junior"));
        assertTrue(revision.getRemoteIpAddress().equals("0.0.0.0.0.1"));
        assertTrue(revision.getUserId().equals(1L));
    }
}
