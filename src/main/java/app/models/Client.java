package app.models;

import java.sql.Timestamp;

public class Client {

    public enum DocumentType {
        CPF,
        CNPJ
    }

    private Integer idClient;
    private String nameClient;
    private String contactClient;
    private String documentClient;
    private DocumentType documentTypeClient;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Client(
            Integer idClient,
            String nameClient,
            String contactClient,
            String documentClient,
            DocumentType documentTypeClient,
            Timestamp createdAt,
            Timestamp updatedAt

    ) {

        this.idClient = idClient;
        this.nameClient = nameClient;
        this.contactClient = contactClient;
        this.documentClient = documentClient;
        this.documentTypeClient = documentTypeClient;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public String getContactClient() {
        return contactClient;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getDocumentClient() {
        return documentClient;
    }

    public DocumentType getDocumentTypeClient() {
        return documentTypeClient;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {

        return "Client(idClient=" + this.idClient + ", nameClient=" + this.nameClient + ", documentClient="+ this.documentClient + ")";
    }

}
