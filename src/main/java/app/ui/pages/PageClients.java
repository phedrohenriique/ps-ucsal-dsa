package app.ui.pages;

import app.services.Database;
import io.javelit.core.Jt;

public class PageClients {

    private final Database database;

    public PageClients(Database database) {
        this.database = database;
    }

    public void loadPage() {

        Jt.title("Clients").use();
        pageClients();
    }

    public void pageClients() {
        
    }

}
