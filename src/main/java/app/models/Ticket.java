package app.models;

import app.services.LinkedList;

public class Ticket {

    private final LinkedList<String> messagesTicket = new LinkedList<>();
    private String idTicket;
    private String documentClient;
    private String contentTicket;
    private String statusTicket = "ACTIVE";

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

    public void setStatus(String value){
        this.statusTicket = value;
    }

    public String[] getMessages() {

        String[] messageList = messagesTicket.toArray(new String[messagesTicket.size()]);
        return messageList;

    }

    public void addMessage(String value) {

        this.messagesTicket.insert(value);
    }

    @Override
    public String toString() {
        return "Ticket(id=" + this.getIdTicket() + ", document=" + this.getDocumentClient() + ", status="
                + this.getStatusTicket() + ")";
    }

}
