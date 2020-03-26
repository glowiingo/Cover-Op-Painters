package ca.bcit.coveropspainters;

import java.util.List;

public class User {
    private String name;
    private String email;
    private List<String> records;
    private List<String> current;

    User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRecords() {
        return this.records;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }

    public List<String> getCurrent() {
        return this.current;
    }

    public void setCurrent(List<String> current) {
        this.current = current;
    }
}
