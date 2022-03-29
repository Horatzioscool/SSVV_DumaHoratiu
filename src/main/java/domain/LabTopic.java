package domain;


import repository.HasID;


public class LabTopic implements HasID<String> {
    private String labId;
    private String description;
    private int deadline;
    private int received;

    public LabTopic(String labId, String descriere, int deadline, int received) {
        this.labId = labId;
        this.description = descriere;
        this.deadline = deadline;
        this.received = received;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    @Override
    public String getID() {
        return this.labId;
    }

    @Override
    public void setID(String labId) {
        this.labId = labId;
    }

    @Override
    public String toString() {
        return labId + "," + description + "," + deadline + "," + received;
    }
}
