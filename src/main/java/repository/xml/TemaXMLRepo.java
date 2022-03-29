package repository.xml;

import domain.LabTopic;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TemaXMLRepo extends AbstractXMLRepository<String, LabTopic> {

    /**
     * Class constructor
     * @param filename - numele fisierului
     */
    public TemaXMLRepo(String filename){
        super(filename);
    }

    /**
     * Extrage informatia despre tema dintr-un elem XML
     * @param element - stringul din care ia datele temei
     * @return tema
     */
    @Override
    public LabTopic extractEntity(Element element) {
        String nrTema = element.getAttribute("nrTema");
        NodeList nods = element.getChildNodes();
        String descriere =element.getElementsByTagName("descriere")
                .item(0)
                .getTextContent();
        String deadline =element.getElementsByTagName("deadline")
                .item(0)
                .getTextContent();
        String primire =element.getElementsByTagName("primire")
                .item(0)
                .getTextContent();

        return new LabTopic(nrTema, descriere, Integer.parseInt(deadline), Integer.parseInt(primire));
    }

    /**
     * Creeaza un element XML dintr o entitate Tema
     * @param document
     * @param entity
     * @return
     */
    @Override
    public Element createElementfromEntity(Document document, LabTopic entity) {
        Element e = document.createElement("nrTema");
        e.setAttribute("nrTema", entity.getID());

        Element descriere = document.createElement("descriere");
        descriere.setTextContent(entity.getDescription());
        e.appendChild(descriere);

        Element deadline = document.createElement("deadline");
        Integer g=entity.getDeadline();
        deadline.setTextContent(g.toString());
        e.appendChild(deadline);

        Element primire = document.createElement("primire");
        Integer p=entity.getReceived();
        primire.setTextContent(p.toString());
        e.appendChild(primire);

        return e;
    }
}
