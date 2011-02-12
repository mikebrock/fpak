package org.jboss.fpak.model;

/**
 * @author Mike Brock .
 */
public class Parm {
    private Class type;
    private String name;
    private String description;

    public Parm(Class type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public Parm() {
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
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
