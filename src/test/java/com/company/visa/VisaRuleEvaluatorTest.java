package com.company.visa;

import com.company.visa.config.RuleLoader;
import com.company.visa.model.*;
import com.company.visa.repository.RuleRepository;
import com.company.visa.service.VisaRuleEvaluator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VisaRuleEvaluatorTest {

    private VisaRuleEvaluator evaluator;

    @BeforeEach
    void setUp() {
        RuleLoader loader = new RuleLoader();

        String rulesPath = Paths.get(
                System.getProperty("user.dir"),
                "src",
                "main",
                "resources",
                "rules.json"
        ).toString();

        List<VisaRule> rules = loader.loadRules(rulesPath);
        RuleRepository repository = new RuleRepository(rules);
        evaluator = new VisaRuleEvaluator(repository);
    }


    @Test
    void validRuleMatch() {
        VisaDecision decision = evaluator.evaluate(
                Country.GERMANY,
                Country.INDIA,
                TravelPurpose.TOURISM,
                30
        );

        assertTrue(decision.isVisaRequired());
        assertEquals(VisaType.TOURIST, decision.getVisaType());
        assertTrue(decision.getWarnings().isEmpty());
    }

    @Test
    void noRuleFound() {
        VisaDecision decision = evaluator.evaluate(
                Country.USA,
                Country.INDIA,
                TravelPurpose.TOURISM,
                30
        );

        assertTrue(decision.isVisaRequired());
        assertEquals(VisaType.NONE, decision.getVisaType());
        assertFalse(decision.getWarnings().isEmpty());
    }

    @Test
    void invalidStayDuration() {
        assertThrows(
                IllegalArgumentException.class,
                () -> evaluator.evaluate(
                        Country.GERMANY,
                        Country.INDIA,
                        TravelPurpose.TOURISM,
                        0
                )
        );
    }
}
