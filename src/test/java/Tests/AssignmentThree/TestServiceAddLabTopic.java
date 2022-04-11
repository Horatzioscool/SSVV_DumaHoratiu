package Tests.AssignmentThree;

import domain.LabTopic;
import domain.Nota;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CrudRepository;
import service.Service;
import validation.LabTopicValidator;
import validation.ValidationException;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestServiceAddLabTopic {

    @Mock
    private CrudRepository<String, Student> studentCrudRepository;
    @Mock
    private CrudRepository<String, LabTopic> labTopicCrudRepository;
    @Mock
    private CrudRepository<String, Nota> notaCrudRepository;
    @Mock
    private Validator<Nota> notaValidator;
    @Mock
    private Validator<Student> studentValidator;

    private Validator<LabTopic> labTopicValidator = new LabTopicValidator();
    private Service service;

    @BeforeEach
    public void BeforeAll(){
        MockitoAnnotations.openMocks(this);
        service = new Service(studentCrudRepository, studentValidator,
                labTopicCrudRepository, labTopicValidator,
                notaCrudRepository, notaValidator
        );
    }

    @Test
    public void TestAddLabTopic_IdNull_ShouldThrow() {
        var lab = new LabTopic(null, "A funny desc", 12, 11);

        assertThrows(ValidationException.class,
                () -> service.addLabTopic(lab));
    }

    @Test
    public void TestAddLabTopic_IdEmpty_ShouldThrow() {
        var lab = new LabTopic("", "A funny desc", 12, 11);

         assertThrows(ValidationException.class,
                () -> service.addLabTopic(lab));
    }

    @Test
    public void TestAddLabTopic_DescriptionNull_ShouldThrow() {
        var lab = new LabTopic("1", null, 12, 11);

        assertThrows(ValidationException.class,
                () -> service.addLabTopic(lab));
    }

    @Test
    public void TestAddLabTopic_DescriptionEmpty_ShouldThrow() {
        var lab = new LabTopic("1", "", 12, 11);

        assertThrows(ValidationException.class,
                () -> service.addLabTopic(lab));
    }

    @Test
    public void TestAddLabTopic_DeadlineZero_ShouldThrow() {
        var lab = new LabTopic("1", "A funny desc", 0, 11);

        assertThrows(ValidationException.class,
                () -> service.addLabTopic(lab));
    }

    @Test
    public void TestAddLabTopic_ReceivedFifteen_ShouldThrow() {
        var lab = new LabTopic("1", "A funny desc", 12, 15);

        assertThrows(ValidationException.class,
                () -> service.addLabTopic(lab));
    }

    @Test
    public void TestAddLabTopic_ShouldNotThrow() {
        var lab = new LabTopic("1", "A funny desc", 12, 11);

        when(labTopicCrudRepository.save(lab))
                .thenReturn(null);

        var result = assertDoesNotThrow(() -> service.addLabTopic(lab));
        assertNull(result);
    }
}
