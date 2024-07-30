package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request;


import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchCorporationsRequest {

    private String corporationId;

    private String corporationName;

    private String representative;

    private String contactNumber;

    private String businessNumber;

    private String grade;
}
