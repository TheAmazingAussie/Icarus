package org.alexdev.icarus.http.template.binders;

public class TemplateSiteBinder {

    private String url;
    private String name;
    private boolean popupClient;

    public TemplateSiteBinder() {
        this.popupClient = true;
        this.url = "http://localhost";
        this.name = "Icarus";
    }
}