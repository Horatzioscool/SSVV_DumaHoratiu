package validation;

import domain.LabTopic;

public class LabTopicValidator implements Validator<LabTopic> {

    @Override
    public void validate(LabTopic entity) throws ValidationException {
        if(entity.getID() == null || entity.getID().equals("") ) {
            throw new ValidationException("Numar tema invalid!");
        }
        if(entity.getDescription() == null || entity.getDescription().equals("")){
            throw new ValidationException("Descriere invalida!");
        }
        if(entity.getDeadline() < 1 || entity.getDeadline() > 14) {
            throw new ValidationException("Deadlineul trebuie sa fie intre 1-14.");
        }
        if(entity.getReceived() < 1 || entity.getReceived() > 14) {
            throw new ValidationException("Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }
}
