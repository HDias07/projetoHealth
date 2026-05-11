package com.healthconnect.healthconnect.DTO;

import com.healthconnect.healthconnect.entity.Paciente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PacienteDTO {

    private Long id;

    @NotBlank(message = "Nome obrigatorio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "Nome invalido")
    private String nome;

    @NotBlank(message = "CPF obrigatorio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 numeros")
    private String cpf;

    @NotBlank(message = "Email obrigatorio")
    @Email(message = "Email invalido")
    @Size(max = 100, message = "Email deve ter no maximo 100 caracteres")
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

    public static PacienteDTO fromEntity(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCpf());
        dto.setEmail(paciente.getEmail());
        dto.setTelefone(paciente.getTelefone());
        dto.setCep(paciente.getCep());
        dto.setRua(paciente.getRua());
        dto.setBairro(paciente.getBairro());
        dto.setCidade(paciente.getCidade());
        dto.setEstado(paciente.getEstado());
        return dto;
    }
}
