package org.rvm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "rvm")
public class RvmConfigProperties {

    private final int bottleProcessSpeed;
    private final int canProcessSpeed;
    private final int defaultProcessSpeed;
    private final boolean ignoreProcessSpeed;

    public RvmConfigProperties(String bottleProcessSpeed, String canProcessSpeed, String defaultProcessSpeed, String ignoreProcessSpeed) {
        this.bottleProcessSpeed = Integer.valueOf(bottleProcessSpeed);
        this.canProcessSpeed = Integer.valueOf(canProcessSpeed);
        this.defaultProcessSpeed = Integer.valueOf(defaultProcessSpeed);
        this.ignoreProcessSpeed = Boolean.valueOf(ignoreProcessSpeed);
    }

    public int getBottleProcessSpeed() {
        return bottleProcessSpeed;
    }

    public int getCanProcessSpeed() {
        return canProcessSpeed;
    }

    public int getDefaultProcessSpeed() {
        return defaultProcessSpeed;
    }

    public boolean isIgnoreProcessSpeed() {
        return ignoreProcessSpeed;
    }
}
