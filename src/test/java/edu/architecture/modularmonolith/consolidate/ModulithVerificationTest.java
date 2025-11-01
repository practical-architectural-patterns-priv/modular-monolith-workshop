package edu.architecture.modularmonolith.consolidate;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModulithVerificationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModulithVerificationTest.class);

    @Test
    void verifyModularityAndGenerateDocs() {

        ApplicationModules modules = ApplicationModules.of(ConSolidAte.class);

        LOGGER.debug("--- DETECTED MODULES ---");
        modules.forEach(module -> LOGGER.debug(module.toString()));

        LOGGER.debug("Generating documentation...");
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
        LOGGER.debug("Documentation generated in target/spring-modulith-docs/");

        LOGGER.debug("Verifying module boundaries...");
        modules.verify();
    }
}