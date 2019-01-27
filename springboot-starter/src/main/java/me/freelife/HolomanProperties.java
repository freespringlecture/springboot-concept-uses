package me.freelife;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("holoman")
public class HolomanProperties {

    private String name;
    private int howLong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }
}
