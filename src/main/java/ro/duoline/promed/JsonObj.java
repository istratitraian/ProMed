package ro.duoline.promed;

import java.io.Serializable;

public class JsonObj implements Serializable{
    public static final long serialVersionUid = 1L;
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }
}
