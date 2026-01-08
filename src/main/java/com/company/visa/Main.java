package com.company.visa;

import com.company.visa.config.RuleLoader;
import com.company.visa.model.*;
import com.company.visa.repository.RuleRepository;
import com.company.visa.service.VisaRuleEvaluator;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        RuleLoader loader = new RuleLoader();
        List<VisaRule> rules = loader.loadRules("src/main/resources/rules.json");


        RuleRepository repository = new RuleRepository(rules);
        VisaRuleEvaluator evaluator = new VisaRuleEvaluator(repository);

        Scanner scanner = new Scanner(System.in);

        try {
            // Display available countries
            System.out.println("Available countries:");
            for (Country c : Country.values()) {
                System.out.print(c + " ");
            }
            System.out.println("\n");

            Country destinationCountry = readEnumIgnoreCase(
                    scanner,
                    Country.class,
                    "Enter destination country: "
            );

            Country passportCountry = readEnumIgnoreCase(
                    scanner,
                    Country.class,
                    "Enter passport country: "
            );


            System.out.println("\nAvailable purposes:");
            for (TravelPurpose p : TravelPurpose.values()) {
                System.out.print(p + " ");
            }
            System.out.println("\n");

            TravelPurpose purpose = readEnumIgnoreCase(
                    scanner,
                    TravelPurpose.class,
                    "Enter travel purpose: "
            );

            System.out.print("Enter stay duration (days): ");
            int stayDays = Integer.parseInt(scanner.nextLine());


            VisaDecision decision = evaluator.evaluate(
                    destinationCountry,
                    passportCountry,
                    purpose,
                    stayDays
            );


            System.out.println("\n===== VISA DECISION =====");
            System.out.println("Visa Required: " + decision.isVisaRequired());
            System.out.println("Visa Type: " + decision.getVisaType());
            System.out.println("Required Documents: " + decision.getRequiredDocuments());
            System.out.println("Estimated Processing Days: " + decision.getEstimatedProcessingDays());

            if (!decision.getWarnings().isEmpty()) {
                System.out.println("Warnings:");
                decision.getWarnings().forEach(w -> System.out.println("- " + w));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }


    private static <E extends Enum<E>> E readEnumIgnoreCase(
            Scanner scanner,
            Class<E> enumClass,
            String prompt
    ) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
