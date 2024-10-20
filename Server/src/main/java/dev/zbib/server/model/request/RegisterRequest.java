package dev.zbib.server.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Valid


    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "Email is required.")

    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Username is required")
    @NotNull(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Matching password is required.")
    private String matchingPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordMatch() {
        return this.password != null && this.password.equals(this.matchingPassword);
    }
}
