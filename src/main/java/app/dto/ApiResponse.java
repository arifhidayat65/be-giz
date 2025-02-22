package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private int code;

    public static <T> ApiResponse<T> success(String message, T data, HttpStatus status) {
        return new ApiResponse<>("success", message, data, LocalDateTime.now(), status.value());
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>("error", message, null, LocalDateTime.now(), status.value());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }
}
