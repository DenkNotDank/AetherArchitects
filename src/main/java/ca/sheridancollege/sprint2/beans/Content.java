package ca.sheridancollege.sprint2.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Content {
    private long contentId;
    private String contentBody;
}
