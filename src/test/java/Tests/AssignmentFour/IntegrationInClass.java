package Tests.AssignmentFour;

import domain.Grade;
import domain.LabTopic;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CrudRepository;
import repository.memory.GradeRepository;
import repository.memory.LabTopicRepository;
import repository.memory.StudentRepository;
import service.Service;
import validation.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IntegrationInClass {

    private CrudRepository<String, Student> studentCrudRepository = new StudentRepository();
    private CrudRepository<String, LabTopic> labTopicCrudRepository = new LabTopicRepository();
    private CrudRepository<String, Grade> notaCrudRepository = new GradeRepository();

    private Validator<Grade> notaValidator = new GradeValidator(studentCrudRepository, labTopicCrudRepository);
    private Validator<Student> studentValidator = new StudentValidator();
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
    public void Test_AddStudent() {
        var student = new Student("1", "John Joseph", 1, "joseph@hotmail.com");
        assertDoesNotThrow(() -> {
            service.addStudent(student);
        });
    }

    // Tests.AssignmentFour.IntegrationInClass
    @Test
    public void Test_AddLabTopic() {
        var lab = new LabTopic("1", "A funny desc", 12, 11);

        var result = assertDoesNotThrow(() -> service.addLabTopic(lab));
        assertNull(result);
    }

    @Test
    public void Test_AddGrade() {
        var grade = new Grade("1", "1", "1", 6, 12);

        assertThrows(ValidationException.class,
                () -> service.addGrade(grade, "Not that good of a job"));
    }

    @Test
    public void Test_AddStudent_AddLabTopic_AddGrade() {
        var student = new Student("1", "John Joseph", 1, "joseph@hotmail.com");
        var lab = new LabTopic("1", "A funny desc", 12, 11);
        var grade = new Grade("1", student.getID(), lab.getID(), 6, 12);

        assertDoesNotThrow(() -> {
            service.addStudent(student);
        });

        assertDoesNotThrow(() -> {
            service.addLabTopic(lab);
        });

        assertDoesNotThrow(() -> {
            service.addGrade(grade, "Not that good of a job...");
        });
    }
}
