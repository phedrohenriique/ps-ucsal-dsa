package app.ui;

import app.ui.pages.PageClients;
import app.ui.pages.PageTickets;
import io.javelit.core.Jt;

public class UI {

        private final PageClients pageClients = new PageClients();
        private final PageTickets pageTickets = new PageTickets();
        
        public void navigation() {

                var page = Jt.navigation(
                        Jt.page("/clients", () -> pageClients.loadPage())
                        .title("Clients")
                        .icon(":person:"),
                        Jt.page("/tickets", () -> pageTickets.loadPage())
                        .title("Tickets")
                        .icon(":receipt:"))
                        .use();

                page.run();
        }

}
