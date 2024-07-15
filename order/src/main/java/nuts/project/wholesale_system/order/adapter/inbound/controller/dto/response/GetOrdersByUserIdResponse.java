package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetOrdersByUserIdResponse {

    @Size(min = 1)
    private int count;

    @NotEmpty
    private List<GetOrderByOrderIdResponse> orderResponses;

    public static GetOrdersByUserIdResponse fromOrderProcessDtoList(List<OrderProcessDto> orderProcessDtoList) {

        List<GetOrderByOrderIdResponse> orderByOrderIdResponses = orderProcessDtoList.stream().map(GetOrderByOrderIdResponse::fromOrderProcessDto).toList();

        return new GetOrdersByUserIdResponse(orderByOrderIdResponses.size(), orderByOrderIdResponses);
    }
}
