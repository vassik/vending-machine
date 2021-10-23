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
        BOTTLE("bottle", Bottle.class),
        CAN("can", Can.class),
        UNKNOWN("unknown", Container.class);

        private final String stringType;
        private final Class<? extends Container> klass;

        Type(String stringType, Class<? extends Container> klass) {
            this.stringType = stringType;
            this.klass = klass;
        }

        @Override
        public String toString() {
            return stringType;
        }

        public Class<? extends Container> getContainerClass() {
            return klass;
        }

        public static Type typeByClass(Class<? extends Container> klass) {
            if (Bottle.class.equals(klass)) {
                return Type.BOTTLE;
            } else if (Can.class.equals(klass)) {
                return Type.CAN;
            } else {
                return Type.UNKNOWN;
            }
        }
    }
}
