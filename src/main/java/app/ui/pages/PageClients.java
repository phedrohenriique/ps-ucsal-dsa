package app.ui.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.configuration.AppConstants;
import app.models.Client;
import app.models.ClientTree;
import app.services.BinaryTree;
import app.services.Database;
import io.javelit.components.layout.TabsComponent.Tabs;
import io.javelit.core.Jt;

public class PageClients {

    private final BinaryTree binaryTree = new BinaryTree();

    public PageClients() {
        // this.database = database;
    }

    public void loadPage() {

        Jt.title("Clients").use();
        pageClients();
    }

    public void pageClients() {
        try {

            // List<Client> clientList = database.getClientList();

            var tabsClients = Jt.tabs(List.of("Create", "Update", "Delete", "List")).key("tabsClient").use();

            formCreateClient(tabsClients);
            formUpdateClient(tabsClients);
            formDeleteClient(tabsClients);
            tableClientList(tabsClients);

            // if (clientList == null || clientList.isEmpty()) {
            // Jt.error("ERROR LOADING PLANTS LIST").use();
            // return;
            // }

            // tablePlantList(plantsList);

            // Jt.text("Choose Plant - ID to get avaiable Devices").use();

            // var plantInfoForm = Jt.form().key("plantInfoForm").use();
            // String plantInfoId = Jt.textInput("id -
            // plant").key("plantInfoId").use(plantInfoForm);
            // boolean submmitedPlantInfoForm = Jt.formSubmitButton("Plant -
            // Info").key("submittedPlantInfo")
            // .use(plantInfoForm);

            // if (submmitedPlantInfoForm) {

            // try {

            // PlantInfo plantInfo =
            // database.getPlantInfoById(Integer.valueOf(plantInfoId));
            // tablePlantInfo(plantInfo);

            // List<Device> deviceList =
            // database.getDeviceListbyPlantId(Integer.valueOf(plantInfoId));
            // tableDeviceListByPlantId(deviceList);

            // } catch (Exception exception) {
            // Jt.error("ERROR").key(AppConstants.KEY_DEVICE_FORM_DELETE + "tooltip").use();
            // System.out.println(exception.getMessage());
            // }

            // }

        } catch (Exception exception) {

            Jt.error("ERROR").key(AppConstants.KEY_DEVICE_FORM_DELETE + "tooltip").use();
            System.out.println(exception.getMessage());

        }

    }

    // public void tablePlantList(List<Plant> plantList) {

    // List<Map<String, Object>> paramsList = new ArrayList<>();

    // for (Plant plant : plantList) {

    // Map<String, Object> row = new LinkedHashMap<>();
    // row.put("Id - Plant", String.valueOf(plant.getIdPlant()));
    // row.put("Name", plant.getNamePlant());
    // row.put("Company", plant.getCompanyPlant());
    // row.put("State", plant.getLocationStatePlant());
    // row.put("Location", plant.getLocationPlant());

    // paramsList.add(row);
    // }

    // var tablePlantList = Jt.container().key("tablePlantList")
    // .height(300)
    // .use();
    // Jt.table(paramsList).use(tablePlantList);

    // }

    // public void tablePlantInfo(PlantInfo plantInfo) {

    // if (plantInfo == null) {

    // plantInfo = new PlantInfo(0, 0, 0f, 0f, 0f);
    // }

    // Map<String, Object> row = new LinkedHashMap<>();
    // row.put("Devices - Total", String.valueOf(plantInfo.getTotalDevices()));
    // row.put("Temperature °C - MAX", String.format(Locale.US, "%.2f",
    // plantInfo.getMaxWindSpeed()));
    // row.put("Wind Speed km/h - MAX", String.format(Locale.US, "%.2f",
    // plantInfo.getMaxWindSpeed()));
    // row.put("Energy MW - Total", String.format(Locale.US, "%.2f",
    // plantInfo.getTotalEnergy()));

    // List<Map<String, Object>> paramsList = new ArrayList<>();
    // paramsList.add(row);
    // Jt.table(paramsList).use();
    // }

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
                ClientTree newClient = new ClientTree(nameClient, contactClient, documentClient, documentTypeClient);
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
                ClientTree updateClient = new ClientTree(nameClient, contactClient, documentClient, documentTypeClient);
                ClientTree client = binaryTree.search(documentClient);

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

        ClientTree[] clientList = binaryTree.getAll();

        List<Map<String, Object>> paramsList = new ArrayList<>();

        for (ClientTree client : clientList) {

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
