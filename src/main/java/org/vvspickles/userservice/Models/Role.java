package org.vvspickles.userservice.Models;

import jakarta.persistence.Entity;

@Entity
public class Role extends BaseModel{
    private String name;

    public String getName() {
        return name;
    }
}

