package com.company.visa.model;

import java.util.List;

public final class VisaDecision {

    private final boolean visaRequired;
    private final VisaType visaType;
    private final List<DocumentType> requiredDocuments;
    private final int estimatedProcessingDays;
    private final List<String> warnings;

    public VisaDecision(
            boolean visaRequired,
            VisaType visaType,
            List<DocumentType> requiredDocuments,
            int estimatedProcessingDays,
            List<String> warnings
    ) {
        this.visaRequired = visaRequired;
        this.visaType = visaType;
        this.requiredDocuments = List.copyOf(requiredDocuments);
        this.estimatedProcessingDays = estimatedProcessingDays;
        this.warnings = List.copyOf(warnings);
    }

    public boolean isVisaRequired() {
        return visaRequired;
    }

    public VisaType getVisaType() {
        return visaType;
    }

    public List<DocumentType> getRequiredDocuments() {
        return requiredDocuments;
    }

    public int getEstimatedProcessingDays() {
        return estimatedProcessingDays;
    }

    public List<String> getWarnings() {
        return warnings;
    }
}
