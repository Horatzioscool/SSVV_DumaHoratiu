package repository.memory;

import domain.Student;
import repository.CrudRepository;

public class StudentRepository extends AbstractCrudRepository<String, Student> implements CrudRepository<String, Student> {
}
