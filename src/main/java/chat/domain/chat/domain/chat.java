package chat.domain.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("chat")
public class chat {

    @Id
    private Long id;

    private String name;

}
