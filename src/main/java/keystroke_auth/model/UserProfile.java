package keystroke_auth.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @ElementCollection
    private List<Double> baselineTimings;

    public UserProfile() {}

    public UserProfile(String username, String password, List<Double> baselineTimings) {
        this.username = username;
        this.password = password;
        this.baselineTimings = baselineTimings;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Double> getBaselineTimings() { return baselineTimings; }
    public void setBaselineTimings(List<Double> baselineTimings) { this.baselineTimings = baselineTimings; }
}