package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request;


import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class SearchCorporationsRequest {

    private String corporationId;

    private String corporationName;

    private String representative;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String contactNumber;

    private String businessNumber;

    private String grade;
}
