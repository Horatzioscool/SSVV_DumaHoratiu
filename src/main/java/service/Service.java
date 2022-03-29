package service;

import curent.Curent;
import domain.Nota;
import domain.Student;
import domain.LabTopic;
import repository.CrudRepository;
import validation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Clasa Service
 */
public class Service {
    private CrudRepository<String, Student> studentFileRepository;
    private Validator<Student> studentValidator;
    private CrudRepository<String, LabTopic> labTopicRepository;
    private Validator<LabTopic> labTopic;
    private CrudRepository<String, Nota> notaFileRepository;
    private Validator<Nota> notaValidator;

    /**
     * Class Constructor
     * @param studentFileRepository - repository student
     * @param studentValidator - validator student
     * @param labTopicRepository - repository tema
     * @param labTopic - validator tema
     * @param notaFileRepository - repository nota
     * @param notaValidator - validator nota
     */
    public Service(CrudRepository<String, Student> studentFileRepository, Validator<Student> studentValidator, CrudRepository<String, LabTopic> labTopicRepository, Validator<LabTopic> labTopic, CrudRepository<String, Nota> notaFileRepository, Validator<Nota> notaValidator) {
        this.studentFileRepository = studentFileRepository;
        this.studentValidator = studentValidator;
        this.labTopicRepository = labTopicRepository;
        this.labTopic = labTopic;
        this.notaFileRepository = notaFileRepository;
        this.notaValidator = notaValidator;
    }

    /**
     * adauga un Student in memorie
     * @param student - studentul pe care il adauga
     * @return null daca studentul a fost adaugat cu succes sau studentul din memorie daca acesta exista deja
     */
    public Student addStudent(Student student) {
        studentValidator.validate(student);
        return studentFileRepository.save(student);
    }

    /**
     * Sterge un student
     * @param id - id-ul studentului
     * @return studentul daca acesta a fost sters sau null daca studentul nu exista
     */
    public Student deleteStudent(String id){
        if(id == null || id.equals("")) {
            throw new ValidationException("Id-ul nu poate fi null!");
        }
        return studentFileRepository.delete(id);
    }

    /**
     * Cauta un student dupa id
     * @param id - id-ul studentului
     * @return studentul daca acesta exista sau null altfel
     */
    public Student findStudent(String id){
        if(id == null || id.equals("")){
            throw new ValidationException("Id-ul nu poate fi null!");
        }
        return studentFileRepository.findOne(id);
    }

    /**
     * Modifica un student
     * @param student - noul student
     * @return noul student daca s-a facut modificarea sau null daca acesta nu exista
     */
    public Student updateStudent(Student student){
        studentValidator.validate(student);
        return studentFileRepository.update(student);
    }

    /**
     * @return toti studentii din memorie
     */
    public Iterable<Student> getAllStudenti(){
        return studentFileRepository.findAll();
    }

    /**
     * Adauga o tema noua
     * @param labTopic  - tema pe care o adauga
     * @return null daca s-a facut adaugarea sau tema daca aceasta exista deja
     */
    public LabTopic addLabTopic(LabTopic labTopic){
        this.labTopic.validate(labTopic);
        return labTopicRepository.save(labTopic);
    }

    public LabTopic deleteLabTopic(String id){
        if(id == null || id.equals("")) {
            throw new ValidationException("Id-ul nu poate fi null!");
        }
        return labTopicRepository.delete(id);
    }

    public LabTopic findLabTopic(String id){
        if(id == null || id.equals("")){
            throw new ValidationException("Id-ul nu poate fi null!");
        }return labTopicRepository.findOne(id);
    }

    public LabTopic updateLabTopic(LabTopic labTopic){
        this.labTopic.validate(labTopic);
        return labTopicRepository.update(labTopic);
    }

    public Iterable<LabTopic> getAllLabTopics(){
        return labTopicRepository.findAll();
    }

    /**
     * Adauga o nota
     * @param nota - nota
     * @param feedback - feedback-ul notei
     * @return null daca nota a fost adaugata sau nota daca aceasta exista deja
     */
    public double addNota(Nota nota, String feedback){
        notaValidator.validate(nota);
        Student student = studentFileRepository.findOne(nota.getIdStudent());
        LabTopic labTopic = labTopicRepository.findOne(nota.getIdTema());
        int predare = calculeazaSPredare(nota.getData());
        if(predare != labTopic.getDeadline()){
            if (predare- labTopic.getDeadline() == 1){
                nota.setNota(nota.getNota()-2.5);
            }
            else{
                throw new ValidationException("Studentul nu mai poate preda aceasta tema!");
            }
        }
        notaFileRepository.save(nota);
        String filename = "fisiere/" + student.getNume() + ".txt";
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true))){
            bufferedWriter.write("\nTema: " + labTopic.getID());
            bufferedWriter.write("\nNota: " + nota.getNota());
            bufferedWriter.write("\nPredata in saptamana: " + predare);
            bufferedWriter.write("\nDeadline: " + labTopic.getDeadline());
            bufferedWriter.write("\nFeedback: " +feedback);
            bufferedWriter.newLine();
        } catch (IOException exception){
            throw new ValidationException(exception.getMessage());
        }
        return nota.getNota();
    }

    /**
     * Sterge o nota
     * @param id - id-ul notei
     * @return nota daca aceasta a fost stearsa sau null daca nota nu exista
     */
    public Nota deleteNota(String id){
        if(id == null || id.equals("")) {
            throw new ValidationException("Id-ul nu poate fi null!");
        }
        return notaFileRepository.delete(id);
    }

    /**
     * Cauta o nota
     * @param id - id-ul notei
     * @return nota sau null daca aceasta nu exista
     */
    public Nota findNota(String id){
        if(id == null || id.equals("")){
            throw new ValidationException("Id-ul nu poate fi null!");
        }
        return notaFileRepository.findOne(id);
    }

    /**
     * @return toate notele
     */
    public Iterable<Nota> getAllNote(){
        return notaFileRepository.findAll();
    }

    /**
     * Prelungeste deadline-ul unei teme
     * @param nrTema - nr-ul temei
     * @param deadline - noul deadline
     */
    public void prelungireDeadline(String nrTema, int deadline){
        int diff= Curent.getCurrentWeek();
        LabTopic labTopic = labTopicRepository.findOne(nrTema);
        if(labTopic == null){
            throw new ValidationException("Tema inexistenta!");
        }
        if(labTopic.getDeadline() >= diff) {
            labTopic.setDeadline(deadline);
            labTopicRepository.save(labTopic);
        }
        else{
            throw new ValidationException("Nu se mai poate prelungi deadline-ul!");
        }
    }

    /**
     * Calculeaza saptamana de predare
     * @param predare - data predarii unei teme
     * @return saptamana in care a fost predata tema
     */
    private int calculeazaSPredare(LocalDate predare) {
        LocalDate startDate = Curent.getStartDate();
        long days = DAYS.between(startDate, predare);
        double saptamanaPredare = Math.ceil((double)days/7);
        return (int)saptamanaPredare;
    }
}
