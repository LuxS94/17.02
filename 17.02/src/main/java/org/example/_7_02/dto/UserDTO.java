package org.example._7_02.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Username obbligatorio!") @Size(min = 2, max = 30, message = " l'username deve essere tra i 2 e i 30 caratteri") String username,
        @Email(message = "Email inserita non valida!")
        String email, @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$", message = "La password deve contenere almeno una maiuscola,un numero,un carattere speciale,e deve essere lunga almeno 8 caratteri!") String password) {
}
