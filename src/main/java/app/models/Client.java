package app.models;

public class Client {

    public enum DocumentType {
        CPF,
        CNPJ
    }

    private String nameClient;
    private String contactClient;
    private String documentClient;
    private String documentTypeClient;

    public Client(

            String nameClient,
            String contactClient,
            String documentClient,
            String documentTypeClient

    ) {
        
        this.nameClient = nameClient;
        this.contactClient = contactClient;
        this.documentClient = documentClient;
        this.documentTypeClient = documentTypeClient;

    }

    public String getContactClient() {
        return contactClient;
    }

    public String getDocumentClient() {
        return documentClient;
    }

    public String getDocumentTypeClient() {
        return documentTypeClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    @Override 
    public String toString(){
        return "Client(name="+this.getNameClient()+", document="+this.getDocumentClient()+", documentType="+this.getDocumentTypeClient()+")";
    }

}
