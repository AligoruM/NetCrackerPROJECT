package catalogApp.shared.model;

public class User extends BaseObject {
    private String password;
    private String role;
    private boolean active;

    public User() {
    }

    public User(int id, String name, Type type, String password, String role, boolean active) {
        super(id, name, type);
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User(String password, String role) {
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                '}';
    }
}
