package com.healthconnect.healthconnect.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContatoDTO {

    @NotBlank(message = "Email obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Telefone obrigatorio")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone invalido")
    private String telefone;

    @NotBlank(message = "CEP obrigatorio")
    @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 numeros")
    private String cep;

    @Size(max = 120, message = "Rua deve ter no maximo 120 caracteres")
    private String rua;

    @Size(max = 80, message = "Bairro deve ter no maximo 80 caracteres")
    private String bairro;

    @Size(max = 80, message = "Cidade deve ter no maximo 80 caracteres")
    private String cidade;

    @Size(max = 2, message = "Estado deve ter 2 caracteres")
    private String estado;
}
