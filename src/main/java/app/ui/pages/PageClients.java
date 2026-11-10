package app.ui.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.models.Client;
import app.services.BinaryTree;
import io.javelit.components.layout.TabsComponent.Tabs;
import io.javelit.core.Jt;

public class PageClients {

    private final BinaryTree binaryTree = new BinaryTree();

    public void loadPage() {

        Jt.title("Clients").use();
        pageClients();
    }

    public void pageClients() {
        try {

            var tabsClients = Jt.tabs(List.of("Create", "Update", "Delete", "List")).key("tabsClient").use();

            formCreateClient(tabsClients);
            formUpdateClient(tabsClients);
            formDeleteClient(tabsClients);
            tableClientList(tabsClients);

        } catch (Exception exception) {

            Jt.error("ERROR").key("tooltipPageclientsError").use();
            System.out.println(exception.getMessage());

        }

    }

    public void formCreateClient(Tabs tab) {

        var formCreateClient = Jt.form().key("formCreateClient").use(tab.tab("Create"));

        String nameClient = Jt.textInput("name").key("nameClientCreate")
                .use(formCreateClient);
        String contactClient = Jt.textInput("contact").key("contactClientCreate")
                .use(formCreateClient);
        String documentClient = Jt.textInput("document").key("documentClientCreate")
                .use(formCreateClient);
        String documentTypeClient = Jt.textInput("document - type").key("documentTypeClientCreate")
                .use(formCreateClient);

        boolean submittedCreate = Jt.formSubmitButton("create client")
                .key("formCreateButton").use(formCreateClient);

        if (submittedCreate) {
            try {
                Client newClient = new Client(nameClient, contactClient, documentClient, documentTypeClient);
                binaryTree.insert(newClient);
                Jt.success("SUCCESS - CREATE").key("tooltipSuccessCreate").use();

            } catch (Exception exception) {
                Jt.error("ERROR - CREATE").key("tooltipErrorCreate").use();
                System.out.println(exception.getMessage());
            }
        }
    }

    public void formUpdateClient(Tabs tab) {

        var formUpdateClient = Jt.form().key("formUpdateClient").use(tab.tab("Update"));

        String nameClient = Jt.textInput("name").key("nameClientUpdate")
                .use(formUpdateClient);
        String contactClient = Jt.textInput("contact").key("contactClientUpdate")
                .use(formUpdateClient);
        String documentClient = Jt.textInput("document").key("documentClientUpdate")
                .use(formUpdateClient);
        String documentTypeClient = Jt.textInput("document - type").key("documentTypeClientUpdate")
                .use(formUpdateClient);

        boolean submittedCreate = Jt.formSubmitButton("update client")
                .key("formUpdateButton").use(formUpdateClient);

        if (submittedCreate) {
            try {
                Client updateClient = new Client(nameClient, contactClient, documentClient, documentTypeClient);
                Client client = binaryTree.search(documentClient);

                if (client == null) {
                    throw new Exception("CLIENT NOT FOUND");
                }

                binaryTree.delete(client.getDocumentClient());
                binaryTree.insert(updateClient);

                Jt.success("SUCCESS - UPDATE").key("tooltipSuccessUpdate").use();

            } catch (Exception exception) {
                Jt.error("ERROR - UPDATE").key("tooltipErrorUpdate").use();
                System.out.println(exception.getMessage());
            }
        }

    }

    public void formDeleteClient(Tabs tab){

        var formDeleteClient = Jt.form().key("formDeleteClient").use(tab.tab("Delete"));

        String documentClient = Jt.textInput("document").key("documentClientDelete")
                .use(formDeleteClient);

        boolean submittedDelete = Jt.formSubmitButton("delete client")
                .key("formDeleteButton").use(formDeleteClient);

        if (submittedDelete) {
            try {

                binaryTree.delete(documentClient);
                Jt.success("SUCCESS - DELETE").key("tooltipSuccessDelete").use();

            } catch (Exception exception) {
                Jt.error("ERROR - UPDATE").key("tooltipErrorDelete").use();
                System.out.println(exception.getMessage());
            }
        }

    }

    public void tableClientList(Tabs tab) {

        Client[] clientList = binaryTree.getAll();

        List<Map<String, Object>> paramsList = new ArrayList<>();

        for (Client client : clientList) {

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("name", client.getNameClient());
            row.put("contact", client.getContactClient());
            row.put("document", client.getDocumentClient());
            row.put("document - type", client.getDocumentTypeClient());

            paramsList.add(row);
        }

        var tableClientList = Jt.container().key("tableClientList")
                .height(300)
                .use(tab.tab("List"));
        Jt.table(paramsList).use(tableClientList);

    }

}
