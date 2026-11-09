package app.models;

import app.services.LinkedList;;;

public class Ticket {

    private final LinkedList linkedList = new LinkedList<>();
    private String idTicket;
    private String documentClient;
    private String contentTicket;
    private String statusTicket = "ACTIVE";
    private LinkedList<String> messagesTicket;

    public Ticket(

            String idTicket,
            String documentClient,
            String contentTicket

    ) {

        this.idTicket = idTicket;
        this.documentClient = documentClient;
        this.contentTicket = contentTicket;

    }

    public String getDocumentClient() {
        return documentClient;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public String getContentTicket() {
        return contentTicket;
    }

    public String getStatusTicket() {
        return statusTicket;
    }

    public void addMessage(String value) {

        this.messagesTicket.insert(value);
    }

}
