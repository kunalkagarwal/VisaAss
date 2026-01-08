package com.company.visa.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public final class VisaRule {

    private final Country destinationCountry;
    private final Set<Country> passportCountries;
    private final TravelPurpose purpose;
    private final int maxStayDays;
    private final boolean visaRequired;
    private final VisaType visaType;
    private final List<DocumentType> requiredDocuments;
    private final int processingDays;

    @JsonCreator
    public VisaRule(
            @JsonProperty("destinationCountry") Country destinationCountry,
            @JsonProperty("passportCountries") Set<Country> passportCountries,
            @JsonProperty("purpose") TravelPurpose purpose,
            @JsonProperty("maxStayDays") int maxStayDays,
            @JsonProperty("visaRequired") boolean visaRequired,
            @JsonProperty("visaType") VisaType visaType,
            @JsonProperty("requiredDocuments") List<DocumentType> requiredDocuments,
            @JsonProperty("processingDays") int processingDays
    ) {
        this.destinationCountry = destinationCountry;
        this.passportCountries = Set.copyOf(passportCountries);
        this.purpose = purpose;
        this.maxStayDays = maxStayDays;
        this.visaRequired = visaRequired;
        this.visaType = visaType;
        this.requiredDocuments = List.copyOf(requiredDocuments);
        this.processingDays = processingDays;
    }

    public Country getDestinationCountry() {
        return destinationCountry;
    }

    public Set<Country> getPassportCountries() {
        return passportCountries;
    }

    public TravelPurpose getPurpose() {
        return purpose;
    }

    public int getMaxStayDays() {
        return maxStayDays;
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

    public int getProcessingDays() {
        return processingDays;
    }
}
