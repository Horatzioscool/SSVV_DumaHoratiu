package Tests.AssignmentThree;

import domain.LabTopic;
import org.junit.jupiter.api.Test;
import repository.RepositoryException;
import repository.memory.AbstractCrudRepository;
import repository.memory.LabTopicRepository;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRepositoryAddLabTopic {

    private final AbstractCrudRepository<String, LabTopic> labTopicRepository = new LabTopicRepository();

    @Test
    public void TestAddLabTopic_LabTopicAddedTwice_ShouldThrow() {
        var lab = new LabTopic(null, "A funny desc", 12, 11);

        assertNull(labTopicRepository.save(lab));

        assertThrows(RepositoryException.class, () -> labTopicRepository.save(lab));
    }

}
