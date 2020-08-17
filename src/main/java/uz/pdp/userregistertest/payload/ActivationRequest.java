package uz.pdp.userregistertest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * created by Muhammad
 * on 21.07.2020
 */

public class ActivationRequest {
    @NotEmpty(message = "Iltimos sizga yuborilgan kodni kiriting!")
    @Pattern(regexp = "[0-9]{4}", message = "Aktiv kod 4 xonali son!")
    private String activeCode;

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
}
