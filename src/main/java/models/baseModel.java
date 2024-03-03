package models;

import java.util.UUID;

public class baseModel {
	private String id;

    public baseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
