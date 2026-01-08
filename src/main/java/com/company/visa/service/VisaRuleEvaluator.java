package com.company.visa.service;

import com.company.visa.model.*;
import com.company.visa.repository.RuleRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VisaRuleEvaluator {

    private final RuleRepository repository;

    public VisaRuleEvaluator(RuleRepository repository) {
        this.repository = repository;
    }

    public VisaDecision evaluate(
            Country destinationCountry,
            Country passportCountry,
            TravelPurpose purpose,
            int stayDays
    ) {


        if (stayDays <= 0) {
            throw new IllegalArgumentException("Stay duration must be greater than zero");
        }

        List<VisaRule> matchedRules = new ArrayList<>();

        for (VisaRule rule : repository.getAllRules()) {
            if (rule.getDestinationCountry() == destinationCountry &&
                    rule.getPassportCountries().contains(passportCountry) &&
                    rule.getPurpose() == purpose &&
                    stayDays <= rule.getMaxStayDays()) {

                matchedRules.add(rule);
            }
        }

        if (matchedRules.isEmpty()) {
            return new VisaDecision(
                    true,
                    VisaType.NONE,
                    List.of(),
                    0,
                    List.of("No matching visa rule found")
            );
        }


        VisaRule selectedRule = matchedRules.stream()
                .min(Comparator.comparingInt(VisaRule::getMaxStayDays))
                .get();

        return new VisaDecision(
                selectedRule.isVisaRequired(),
                selectedRule.getVisaType(),
                selectedRule.getRequiredDocuments(),
                selectedRule.getProcessingDays(),
                List.of()
        );
    }
}
