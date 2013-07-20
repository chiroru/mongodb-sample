package jp.ddo.chiroru.sample;

import java.io.Serializable;

public class Bookshelf
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _id;

    private String name;

    private String description;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
