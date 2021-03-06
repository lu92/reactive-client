package reactive;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Event {
    private final long id;
    private final LocalDateTime dateTime;
}
