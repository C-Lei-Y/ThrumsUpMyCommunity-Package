package org.goodneigbor.postitserver.entity.global;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "global_parameter")
public class Parameter {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "value")
    @NotNull
    private String value;

    @Column(name = "client_view")
    @NotNull
    private Boolean clientView;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getClientView() {
        return clientView;
    }

    public void setClientView(Boolean clientView) {
        this.clientView = clientView;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Parameter))
            return false;
        Parameter other = (Parameter) obj;
        return Objects.equals(getName(), other.getName());
    }

}
