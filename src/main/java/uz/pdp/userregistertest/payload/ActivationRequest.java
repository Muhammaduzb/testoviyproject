package uz.pdp.userregistertest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * created by Muhammad
 * on 21.07.2020
 */

public class ActivationRequest {

    @Pattern(regexp = "[0-9]{5}", message = "Phone number must be 5 numbers!")
    private String activeCode;

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
}
