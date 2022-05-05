
package dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Boolean complete;
    private long id;
    private long petId;
    private long quantity;
    private String shipDate;
    private String status;
}
