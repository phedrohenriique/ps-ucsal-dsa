package app.ui;

import app.ui.pages.PageClients;
import io.javelit.core.Jt;

public class UI {

        private final PageClients pageClients = new PageClients();;
        
        public void navigation() {

                var page = Jt.navigation(
                        Jt.page("/clients", () -> pageClients.loadPage())
                        .title("Clients")
                        .icon(":person:"),
                        Jt.page("/tickets", () -> pageClients.loadPage())
                        .title("Tickets")
                        .icon(":receipt:"),
                        Jt.page("/messages", () -> pageClients.loadPage())
                        .title("Messages")
                        .icon(":chat:"))
                        .use();

                page.run();
        }

}
