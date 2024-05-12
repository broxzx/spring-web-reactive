package ua.project.springwebreactive.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", schema = "public")
public class UserEntity {
    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private UserRole role;
}
