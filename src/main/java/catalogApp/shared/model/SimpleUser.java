package catalogApp.shared.model;

import catalogApp.server.dao.constants.Types;

import java.util.Set;

public class SimpleUser extends BaseObject{

    private Set<String> roles;
    private String description;

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

    public void updateFields(SimpleUser user){
        setId(user.getId());
        setName(user.getName());
        setRoles(user.getRoles());
        setArchived(user.isArchived());
        setImagePath(user.getImagePath());
        setDescription(user.getDescription());
    }


    @Override
    public String toString() {
        return "SimpleUser{" +
                "roles=" + roles +
                ", description='" + description + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", archived=" + isArchived() +
                ", comment='" + getComment() + '\'' +
                ", imagePath='" + getImagePath() + '\'' +
                '}';
    }
}
