package models.core;

import javax.persistence.*;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    public String fullname;

    public boolean isAdmin;
}
