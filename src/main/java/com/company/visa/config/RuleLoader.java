package com.company.visa.config;

import com.company.visa.model.VisaRule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class RuleLoader {

    public List<VisaRule> loadRules(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File file = new File(filePath);


            if (!file.isAbsolute()) {
                file = new File(System.getProperty("user.dir"), filePath);
            }

            if (!file.exists()) {
                throw new IllegalStateException(
                        "Rules config file not found at: " + file.getAbsolutePath()
                );
            }

            List<VisaRule> rules = mapper.readValue(
                    file,
                    new TypeReference<List<VisaRule>>() {}
            );


            for (VisaRule rule : rules) {
                validateRule(rule);
            }

            return rules;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load visa rules", e);
        }
    }

    private void validateRule(VisaRule rule) {
        if (rule.getDestinationCountry() == null ||
                rule.getPassportCountries() == null ||
                rule.getPassportCountries().isEmpty() ||
                rule.getPurpose() == null ||
                rule.getVisaType() == null) {

            throw new IllegalStateException(
                    "Invalid rule configuration: missing mandatory fields"
            );
        }
    }
}
