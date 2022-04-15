package repository.xml;

import domain.Grade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GradeXMLRepository extends AbstractXMLRepository<String, Grade> {

    /**
     * Class constructor
     * @param filename - numele fisierului
     */
    public GradeXMLRepository(String filename) {
        super( filename);
    }

    @Override
    public Element createElementfromEntity(Document document, Grade entity) {
        Element e = document.createElement("nota");

        Element idStudent = document.createElement("idStudent");
        idStudent.setTextContent(entity.getIdStudent());
        e.appendChild(idStudent);

        Element idTema = document.createElement("idTema");
        idTema.setTextContent(entity.getIdTema());
        e.appendChild(idTema);

        Element notaProf = document.createElement("notaProf");
        Double i=entity.getNota();
        notaProf.setTextContent(i.toString());
        e.appendChild(notaProf);

        Element data = document.createElement("dataCurenta");
        int d = entity.getWeekGiven();
        data.setTextContent(Integer.toString(d));
        e.appendChild(data);

        return e;
    }


    /**
     * Extrage informatia despre nota dintr-un XML
     * @param element - elem XML din care ia datele notei
     * @return nota
     */
    @Override
    public Grade extractEntity(Element element) {
        String id = element.getAttribute("id");
        NodeList nods = element.getChildNodes();

        String studentId =element.getElementsByTagName("idStudent")
                .item(0)
                .getTextContent();

        String temaId =element.getElementsByTagName("idTema")
                .item(0)
                .getTextContent();

        String notaProf =element.getElementsByTagName("notaProf")
                .item(0)
                .getTextContent();


        String data = element.getElementsByTagName("dataCurenta")
                .item(0)
                .getTextContent();
        int week = Integer.parseInt(data);

        return new Grade(id,studentId,temaId,Double.parseDouble(notaProf),week);
        }
}
