import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.Slice;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureDiagnosticsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchitectureDiagnosticsTest.class);

    private final JavaClasses classes = new ClassFileImporter()
            .importPackages("edu.architecture.modularmonolith.consolidate..");

    @Test
    void shouldDetectIllegalCrossModuleDependencies() {
        DescribedPredicate<Slice> areNotSharedOrWebhook =
                new DescribedPredicate<Slice>("are not 'shared' or 'webhook' module") {
                    @Override
                    public boolean test(Slice input) {
                        String description = input.getDescription();
                        return !description.equals("shared") && !description.equals("webhook");
                    }
                };

        ArchRule rule = SlicesRuleDefinition.slices()
                .matching("edu.architecture.modularmonolith.consolidate.(*)..")
                .that(areNotSharedOrWebhook)
                .should().notDependOnEachOther();

        LOGGER.debug("Verifying 'slices should not depend on each other' rule...");
        rule.check(classes);
    }

    @Test
    void shouldDetectSharedModuleDependingOnOtherModules() {
        ArchRule sharedDependencyRule = noClasses()
                .that().resideInAPackage("..consolidate.shared..")
                .should().accessClassesThat()
                .resideInAnyPackage(
                        "..consolidate.submission..",
                        "..consolidate.analysis..",
                        "..consolidate.points..",
                        "..consolidate.leaderboard..",
                        "..consolidate.webhook.."
                );

        LOGGER.debug("Verifying 'shared' module dependencies...");
        sharedDependencyRule.check(classes);
    }
}
