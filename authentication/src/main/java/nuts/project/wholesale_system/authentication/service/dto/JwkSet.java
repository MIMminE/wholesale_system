package nuts.project.wholesale_system.authentication.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class JwkSet {

    private List<Key> keys;

    @Getter
    @ToString
    public static class Key {
        private String kid;
        private String kty;
        private String alg;
        private String use;
        private String n;
        private String e;
        private List<String> x5c;
        private String x5t;
        @JsonProperty("x5t#S256")
        private String x5ts256;
    }
}

