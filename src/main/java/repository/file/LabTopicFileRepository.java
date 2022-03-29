package repository.file;

import domain.LabTopic;

public class LabTopicFileRepository extends AbstractFileRepository<String, LabTopic> {

    /**
     * Class constructor
     * @param filename - numele fisierului
     */
    public LabTopicFileRepository(String filename){
        super(filename);
    }

    /**
     * Extrage informatia despre tema dintr-un string
     * @param linie - stringul din care ia datele temei
     * @return tema
     */
    @Override
    public LabTopic extractEntity(String linie) {
        String[] cuvinte = linie.split(",");
        return new LabTopic(cuvinte[0], cuvinte[1], Integer.parseInt(cuvinte[2]), Integer.parseInt(cuvinte[3]));
    }
}
