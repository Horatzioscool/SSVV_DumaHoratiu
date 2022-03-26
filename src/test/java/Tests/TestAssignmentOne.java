package Tests;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CrudRepository;
import service.Service;
import validation.ValidationException;
import validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestAssignmentOne {

    @Mock
    private CrudRepository<String, Student> studentCrudRepository;
    @Mock
    private Validator<Student> studentValidator;
    @Mock
    private CrudRepository<String, Tema> temaCrudRepository;
    @Mock
    private Validator<Tema> temaValidator;
    @Mock
    private CrudRepository<String, Nota> notaCrudRepository;
    @Mock
    private Validator<Nota> notaValidator;

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
    public void TestAddStudent_NewStudent_ShouldReturnSameStudent() {
        var student = new Student("1", "John Joseph", 922, "joseph@hotmail.com");
        var result = service.addStudent(student);
        assertNull(result);
    }

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void TestAddAssignment_NewAssignment_ShouldReturnSameAssignment() throws ParseException {
        var tema = new Tema("1", "Assignment 1",
                (int) simpleDateFormat.parse("20-03-2022").getTime(),
                (int) simpleDateFormat.parse("27-03-2022").getTime());

        var result = service.addTema(tema);
        assertNull(result);
    }

    @Test
    public void AddGrade() throws ParseException {
        var nota = new Nota("1", "1", "1", 7.5, LocalDate.of(2022, 4, 30));

        Mockito.when(temaCrudRepository.findOne("1"))
                .thenReturn(new Tema("1", "Assignment 1",
                        (int) simpleDateFormat.parse("20-03-2022").getTime(),
                        (int) simpleDateFormat.parse("27-03-2022").getTime()));

        assertThrows(ValidationException.class, () -> service.addNota(nota, "Some feedback"));
    }
}
