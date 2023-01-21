package com.eteration.simplebanking.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApprovalCodeService {
    public static String generateCode() {
        return UUID.randomUUID().toString();
    }
}
