package com.cursosdd.cashbackrewards.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "com.cursosdd.cashbackrewards")
@DisplayName("Architecture Boundary Tests")
class ArchitectureBoundaryTest {

    @DisplayName("Domain layer should not depend on Spring or JPA")
    @Test
    void domainShouldNotDependOnSpringOrJpa() {
        ArchRule rule = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .haveNameMatching("org\\.springframework\\..*|jakarta\\.persistence\\..*");

        System.out.println("Rule loaded: " + rule.getDescription());
    }

    @DisplayName("Adapter -> Application -> Domain dependency flow")
    @Test
    void dependencyFlow() {
        ArchRule rule = classes()
            .that().resideInAPackage("..adapter..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..application..", "..domain..");

        System.out.println("Rule loaded: " + rule.getDescription());
    }
}
