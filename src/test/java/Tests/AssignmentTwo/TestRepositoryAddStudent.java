package Tests.AssignmentTwo;

import domain.Student;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.RepositoryException;
import repository.StudentRepository;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestRepositoryAddStudent {

    private final CrudRepository<String, Student> repository = new StudentRepository();

    @Test
    public void Test_AddStudent_NewStudent_ShouldReturnNull() {
        var student = new Student("1", "John Joseph", 922, "joseph@hotmail.com");
        var result = assertDoesNotThrow(() -> repository.save(student));
        assertNull(result);
    }

    @Test
    public void Test_AddStudent_ExistingStudent_ShouldThrow() {
        var student1 = new Student("1", "John Joseph", 922, "joseph@hotmail.com");
        var result = repository.save(student1);
        assertNull(result);

        var student2 = new Student("1", "Joseph John", 923, "john@hotmail.com");

        assertThrows(RepositoryException.class, () -> {
            repository.save(student2);
        });
    }

    @Test
    public void Test_AddStudent_MultipleNewStudents_ShouldReturnNull() {
        var student1 = new Student("1", "John Joseph", 922, "joseph@hotmail.com");
        var student2 = new Student("2", "John Joseph", 922, "joseph@hotmail.com");
        var student3 = new Student("3", "John Joseph", 922, "joseph@hotmail.com");

        var results = Stream.of(student1, student2, student3)
                .map(repository::save);

        assertAll(results.map(r -> () -> assertNull(r)));
    }
}
