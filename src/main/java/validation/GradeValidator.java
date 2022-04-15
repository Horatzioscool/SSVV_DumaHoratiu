package validation;


import domain.LabTopic;
import domain.Grade;
import domain.Student;
import repository.CrudRepository;

public class GradeValidator implements Validator<Grade> {
    private CrudRepository<String, Student> studentRepository;
    private CrudRepository<String, LabTopic> labTopicRepository;

    /**
     * Class constructor
     * @param studentRepository - repository student
     * @param labTopicRepository - repository tema
     */
    public GradeValidator(CrudRepository<String, Student> studentRepository, CrudRepository<String, LabTopic> labTopicRepository) {
        this.studentRepository = studentRepository;
        this.labTopicRepository = labTopicRepository;
    }

    /**
     * Valideaza o nota
     * @param grade - nota pe care o valideaza
     * @throws ValidationException daca nota nu e valida
     */
    @Override
    public void validate(Grade grade) throws ValidationException {
        Student student = studentRepository.findOne(grade.getIdStudent());
        if (student == null){
            throw new ValidationException("Studentul nu exista!");
        }
        LabTopic labTopic = labTopicRepository.findOne(grade.getIdTema());
        if(labTopic == null){
            throw new ValidationException("Tema nu exista!");
        }
        double notaC = grade.getNota();
        if(notaC > 10.00 || notaC < 0.00){
            throw new ValidationException("Valoarea notei nu este corecta!");
        }
    }
}
