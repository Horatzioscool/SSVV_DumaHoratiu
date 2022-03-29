package Tests.AssignmentTwo;

import domain.Nota;
import domain.Student;
import domain.LabTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CrudRepository;
import service.Service;
import validation.StudentValidator;
import validation.ValidationException;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceAddStudent {

    @Mock
    private CrudRepository<String, Student> studentCrudRepository;
    @Mock
    private CrudRepository<String, LabTopic> temaCrudRepository;
    @Mock
    private Validator<LabTopic> temaValidator;
    @Mock
    private CrudRepository<String, Nota> notaCrudRepository;
    @Mock
    private Validator<Nota> notaValidator;

    private Validator<Student> studentValidator = new StudentValidator();
    private Service service;

    @BeforeEach
    public void BeforeAll(){
        MockitoAnnotations.openMocks(this);
        service = new Service(studentCrudRepository, studentValidator,
                temaCrudRepository, temaValidator,
                notaCrudRepository, notaValidator
        );
    }

    @Test
    public void TestAddStudent_ShouldNotThrow() {
        var student = new Student("1", "John Joseph", 922, "joseph@hotmail.com");

        Mockito.when(studentCrudRepository.save(student))
                .thenReturn(null);

        var result = assertDoesNotThrow(() -> service.addStudent(student));
        assertNull(result);
    }

    @Test
    public void Test_AddStudent_IdNotSet_ShouldThrow() {
        var student = new Student(null, "John Joseph", 922, "joseph@hotmail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void Test_AddStudent_NameNotSet_ShouldThrow() {
        var student = new Student("1", null, 922, "joseph@hotmail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void Test_AddStudent_GroupLowerThanZero_ShouldThrow() {
        var student = new Student("1", "John Joseph", -1, "joseph@hotmail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void Test_AddStudent_GroupZero_ShouldThrow() {
        var student = new Student("1", "John Joseph", 0, "joseph@hotmail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void Test_AddStudent_GroupOne_ShouldThrowNotThrow() {
        var student = new Student("1", "John Joseph", 1, "joseph@hotmail.com");
        assertDoesNotThrow(() -> {
            service.addStudent(student);
        });
    }

    @Test
    public void Test_AddStudent_EmailNotSet_ShouldThrow() {
        var student = new Student("1", "John Joseph", 922, null);
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void Test_AddStudent_EmailNotAnEmail_ShouldThrow() {
        var student = new Student("1", "John Joseph", 922, "John Joseph");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }
}
