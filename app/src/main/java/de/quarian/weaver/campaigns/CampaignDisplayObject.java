package de.quarian.weaver.campaigns;

public class CampaignDisplayObject {

    private final int id;
    private final String name;

    public CampaignDisplayObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
