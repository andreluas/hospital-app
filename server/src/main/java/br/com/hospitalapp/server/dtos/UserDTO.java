package br.com.hospitalapp.server.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import br.com.hospitalapp.server.models.RoleModel;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID userId;
    
    @NotBlank(message = "Campo obrigatório")
    private String username;
    private String password;
    private List<RoleModel> roles;

    public UserDTO() {
    }

    public UserDTO(UUID userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

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

    public List<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }
}
