package chat.domain.device.domain;

import org.springframework.data.annotation.Id;

public class device {

    @Id
    private Long id;

    private String user_fk;

    private String device_id;
}
