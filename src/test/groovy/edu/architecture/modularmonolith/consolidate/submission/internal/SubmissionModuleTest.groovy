package edu.architecture.modularmonolith.consolidate.submission.internal

import edu.architecture.modularmonolith.consolidate.shared.events.EventBus
import edu.architecture.modularmonolith.consolidate.submission.api.SubmissionRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import spock.lang.Specification
import static org.mockito.Mockito.*


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class SubmissionModuleTest extends Specification {

    @Autowired
    SubmissionService submissionService

    @Autowired
    SubmissionRepository submissionRepository

    @MockitoBean
    EventBus eventBus

    def "test create submission and publish event"() {
        given: "user and pull request URL"
        def userId = "ghost.pirate.lechuck@monkeyisland.com"
        def repoUrl = "https://github.com/repos/con-solid-ate/pull/1"

        when: "submission is created"
        def businessKey = submissionService.create(userId, repoUrl)

        then: "submission is persisted"
        submissionRepository.count() == 1
        def submission = submissionRepository.findAll().first()
        submission.businessKey == businessKey;
        submission.userId == userId
        submission.getUrl() == repoUrl

        and: "event is published"
        verify(eventBus, times(1)).publish( isA(SubmissionRegistered.class))
    }

    def "test for invalid pull request URL"() {
        when: "invalid URL is provided"
        submissionService.create("ghost.pirate.lechuck@monkeyisland.com", "not-a-valid-url")

        then: "error is raised"
        def ex = thrown(IllegalArgumentException)
        ex.message == "Invalid repo URL"

        and: "no submissions are stored"
        submissionRepository.count() == 0

        and: "no event is published"
        verifyNoInteractions(eventBus)
    }
}
