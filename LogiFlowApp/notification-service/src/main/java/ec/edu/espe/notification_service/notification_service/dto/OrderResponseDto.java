package ec.edu.espe.notification_service.notification_service.dto;

import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private String customerName;
    private String status;
}
