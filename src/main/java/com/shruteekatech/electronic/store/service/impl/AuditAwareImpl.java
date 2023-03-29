package com.shruteekatech.electronic.store.service.impl;

import com.shruteekatech.electronic.store.helper.AppConstant;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.of("radha");
    }
}
