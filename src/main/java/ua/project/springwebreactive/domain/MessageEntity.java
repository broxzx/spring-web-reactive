package ua.project.springwebreactive.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    @Id
    private Long id;
    private String data;

    public MessageEntity(String data) {
        this.data = data;
    }
}
