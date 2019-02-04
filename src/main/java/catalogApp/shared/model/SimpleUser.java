package catalogApp.shared.model;

import catalogApp.server.dao.constants.Types;

import java.util.Set;

public class SimpleUser extends BaseObject{

    private Set<String> roles;
    private String description;
    private String avatarUrl;

    public SimpleUser() {
    }

    public SimpleUser(int id, String username) {
        super(id, username, new Type(Types.USER, "User"));
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    @Override
    public String toString() {
        return "SimpleUser{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", roles=" + roles +
                ", description='" + description + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
