package org.rvm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.lang.NonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bottle.class, name = "bottle"),
        @JsonSubTypes.Type(value = Can.class, name = "can")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Container {

    @NonNull
    protected Type type;

    public abstract Integer getValue();

    public enum Type {
        BOTTLE("bottle"),
        CAN("can");

        private final String stringType;

        Type(String stringType) {
            this.stringType = stringType;
        }

        @Override
        public String toString() {
            return stringType;
        }
    }
}
