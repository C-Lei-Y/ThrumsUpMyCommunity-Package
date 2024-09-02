package org.goodneigbor.postitserver.dto.global;

import java.util.List;

import org.goodneigbor.postitserver.dto.IdEntityDto;

public class UserDto extends IdEntityDto {

    private String username;

    private String password;

    private Boolean enabled;

    private List<String> roleList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserDto))
            return false;
        return super.equals(other);
    }

}
