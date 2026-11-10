package app.ui.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.models.Ticket;
import app.services.LinkedList;
import app.services.Queue;
import io.javelit.components.layout.TabsComponent.Tabs;
import io.javelit.core.Jt;

public class PageTickets {

    private final Queue<Ticket> queue = new Queue<>();

    public void loadPage() {

        Jt.title("Tickets").use();
        pageTickets();
    }

    public void pageTickets() {

        // GetTicket will show internal messages from linked list
        // List will set show the queues in line waiting for response and resolution
        // Tickets will be created in the Queue

        var tabsTickets = Jt.tabs(List.of("Create", "Update", "GetTicket", "List")).key("tabsTickets").use();

        formCreateTicket(tabsTickets);
        formUpdateTicket(tabsTickets);
        tableTicketById(tabsTickets);
        tableTicketList(tabsTickets);

    }

    public void formCreateTicket(Tabs tab) {

        var formCreateTicket = Jt.form().key("formCreateTicket").use(tab.tab("Create"));

        String idTicket = Jt.textInput("id - ticket").key("idTicketCreate")
                .use(formCreateTicket);
        String documentClient = Jt.textInput("document").key("documentClientTicketCreate")
                .use(formCreateTicket);
        String contentTicket = Jt.textInput("content").key("contentTicketCreate")
                .use(formCreateTicket);

        boolean submittedCreate = Jt.formSubmitButton("create ticket")
                .key("formCreateButton").use(formCreateTicket);

        if (submittedCreate) {
            try {

                Ticket newTicket = new Ticket(idTicket, documentClient, contentTicket);
                queue.addItem(newTicket);
                Jt.success("SUCCESS - CREATE").key("tooltipSuccessCreate").use();

            } catch (Exception exception) {
                Jt.error("ERROR - CREATE").key("tooltipErrorCreate").use();
                System.out.println(exception.getMessage());
            }
        }
    }

    public void formUpdateTicket(Tabs tab) {

        var formUpdateTicket = Jt.form().key("formUpdateTicket").use(tab.tab("Update"));

        String idTicket = Jt.textInput("id - ticket").key("idTicketUpdate")
                .use(formUpdateTicket);
        String messageTicket = Jt.textInput("message").key("documentClientTicketUpdate")
                .use(formUpdateTicket);
        String statusTicket = Jt.textInput("status").key("statusTicketUpdate")
                .use(formUpdateTicket);

        boolean submittedUpdate = Jt.formSubmitButton("update ticket")
                .key("formUpdateButton").use(formUpdateTicket);

        if (submittedUpdate) {
            try {

                LinkedList<Ticket> queueTicketList = queue.getAll();
                Ticket[] ticketList = queueTicketList.toArray(new Ticket[queueTicketList.size()]);

                for (Ticket ticket : ticketList) {
                    if (ticket.getIdTicket().equals(idTicket)) {
                        ticket.addMessage(messageTicket);
                        if(!statusTicket.equals("") || statusTicket.equals("ACTIVE")){
                            ticket.setStatus(statusTicket);
                        }
                        Jt.success("SUCCESS - UPDATE ").key("tooltipSuccessUpdate").use();
                    }
                }

            } catch (Exception exception) {
                Jt.error("ERROR - UPDATE").key("tooltipErrorUpdate").use();
                System.out.println(exception.getMessage());
            }
        }
    }

    public void tableTicketById(Tabs tab) {

        var formGetTicket = Jt.form().key("formGetTicket").use(tab.tab("GetTicket"));

        String idTicket = Jt.textInput("id - ticket").key("idTicketGetById")
                .use(formGetTicket);

        boolean submittedGetTicket = Jt.formSubmitButton("get ticket")
                .key("formTicketGetByIdButton").use(formGetTicket);

        if (submittedGetTicket) {

            try {

                LinkedList<Ticket> queueTicketList = queue.getAll();
                Ticket[] ticketList = queueTicketList.toArray(new Ticket[queueTicketList.size()]);

                for (Ticket ticket : ticketList) {

                    if (ticket.getIdTicket().equals(idTicket)) {

                        Jt.success("SUCCESS - GET TICKET").key("tooltipSuccessGetTicket").use();

                        List<Map<String, String>> paramsList = new ArrayList<>();
                        String[] messageList = ticket.getMessages();
                        int n = 0;

                        for (String message : messageList) {

                            Map<String, String> row = new LinkedHashMap<>();

                            row.put("step", String.valueOf(n));
                            row.put("action", message);
                            paramsList.add(row);

                            n++;
                        }

                        var tableGetTicketById = Jt.container().key("tableGetTicketById")
                                .height(300)
                                .use(tab.tab("GetTicket"));
                        Jt.table(paramsList).use(tableGetTicketById);

                    }
                }

            } catch (Exception exception) {
                Jt.error("ERROR - UPDATE").key("tooltipErrorDelete").use();
                System.out.println(exception.getMessage());
            }
        }

    }

    public void tableTicketList(Tabs tab) {

        LinkedList<Ticket> linkedListTicket = queue.getAll();

        Ticket[] ticketList = linkedListTicket.toArray(new Ticket[linkedListTicket.size()]);

        List<Map<String, Object>> paramsList = new ArrayList<>();

        for (Ticket ticket : ticketList) {

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", ticket.getIdTicket());
            row.put("document - client", ticket.getDocumentClient());
            row.put("status", ticket.getStatusTicket());

            paramsList.add(row);
        }

        var tableTicketList = Jt.container().key("tableClientList")
                .height(300)
                .use(tab.tab("List"));
        Jt.table(paramsList).use(tableTicketList);

    }

}
