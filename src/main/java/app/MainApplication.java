package app;


import repository.xml.StudentXMLRepo;
import repository.xml.TemaXMLRepo;
import repository.xml.GradeXMLRepository;
import service.Service;
import validation.GradeValidator;
import validation.StudentValidator;
import validation.LabTopicValidator;
import view.UI;



public class MainApplication {

    public static void main(String[] args) {
        StudentValidator studentValidator = new StudentValidator();
        LabTopicValidator labTopicValidator = new LabTopicValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

       //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        GradeValidator gradeValidator = new GradeValidator(studentXMLRepository, temaXMLRepository);
        GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, labTopicValidator, gradeXMLRepository, gradeValidator);
        UI ui = new UI(service);
        ui.run();
    }

}
