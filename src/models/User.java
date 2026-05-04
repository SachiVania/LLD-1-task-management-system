package models;

import java.util.UUID;

public class User {
    // Keeping as final as these will not be modified once created
    private final String id;
    private final String name;
    private final String email;

    public User(String name, String email) {
        // Keeping UUID instead of simple generation to simulate real world scenario
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
