package com.company.visa.repository;

import com.company.visa.model.VisaRule;

import java.util.Collections;
import java.util.List;

public class RuleRepository {

    private final List<VisaRule> rules;

    public RuleRepository(List<VisaRule> rules) {
        this.rules = List.copyOf(rules); // immutable copy
    }

    public List<VisaRule> getAllRules() {
        return Collections.unmodifiableList(rules);
    }
}
