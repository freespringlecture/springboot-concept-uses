package me.freelife;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.Duration;

@Component
@ConfigurationProperties("freelife")
@Validated
public class FreelifeProperties {

    @NotEmpty
    private String name;

    @Min(19)
    private int age;

    private String fullName;

    //    @DurationUnit(ChronoUnit.SECONDS) // 값을 초로 받음 들어오지 않으면 기본 값은 30초
    private Duration sessionTimeout = Duration.ofSeconds(30);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Duration getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Duration sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
