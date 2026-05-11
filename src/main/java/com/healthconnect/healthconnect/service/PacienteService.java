package com.healthconnect.healthconnect.service;

import com.healthconnect.healthconnect.DTO.ContatoDTO;
import com.healthconnect.healthconnect.DTO.PacienteDTO;
import com.healthconnect.healthconnect.entity.Paciente;
import com.healthconnect.healthconnect.exception.CpfJaCadastradoException;
import com.healthconnect.healthconnect.exception.PacienteNaoEncontradoException;
import com.healthconnect.healthconnect.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<PacienteDTO> listarTodos() {
        return repository.findAll().stream().map(PacienteDTO::fromEntity).toList();
    }

    public PacienteDTO buscarPorId(Long id) {
        return PacienteDTO.fromEntity(buscarEntidadePorId(id));
    }

    public void salvar(PacienteDTO dto) {
        validarCpfUnico(dto);

        Paciente paciente = dto.getId() == null
                ? new Paciente()
                : buscarEntidadePorId(dto.getId());

        aplicarDadosCadastrais(dto, paciente);
        repository.save(paciente);
    }

    public void excluir(Long id) {
        repository.delete(buscarEntidadePorId(id));
    }

    public ContatoDTO buscarContato(Long id) {
        Paciente paciente = buscarEntidadePorId(id);
        ContatoDTO dto = new ContatoDTO();
        dto.setEmail(paciente.getEmail());
        dto.setTelefone(paciente.getTelefone());
        dto.setCep(paciente.getCep());
        dto.setRua(paciente.getRua());
        dto.setBairro(paciente.getBairro());
        dto.setCidade(paciente.getCidade());
        dto.setEstado(paciente.getEstado());
        return dto;
    }

    public void atualizarContato(Long id, ContatoDTO dto) {
        Paciente paciente = buscarEntidadePorId(id);
        paciente.setEmail(normalizarTexto(dto.getEmail()));
        paciente.setTelefone(normalizarDigitos(dto.getTelefone()));
        paciente.setCep(normalizarDigitos(dto.getCep()));
        paciente.setRua(normalizarTexto(dto.getRua()));
        paciente.setBairro(normalizarTexto(dto.getBairro()));
        paciente.setCidade(normalizarTexto(dto.getCidade()));
        paciente.setEstado(normalizarEstado(dto.getEstado()));
        repository.save(paciente);
    }

    private Paciente buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new PacienteNaoEncontradoException(id));
    }

    private void validarCpfUnico(PacienteDTO dto) {
        String cpf = normalizarDigitos(dto.getCpf());
        boolean cpfEmUso = dto.getId() == null
                ? repository.existsByCpf(cpf)
                : repository.existsByCpfAndIdNot(cpf, dto.getId());

        if (cpfEmUso) {
            throw new CpfJaCadastradoException("Ja existe um paciente cadastrado com este CPF.");
        }
    }

    private void aplicarDadosCadastrais(PacienteDTO dto, Paciente paciente) {
        paciente.setNome(normalizarTexto(dto.getNome()));
        paciente.setCpf(normalizarDigitos(dto.getCpf()));
        paciente.setEmail(normalizarTexto(dto.getEmail()));
        paciente.setTelefone(normalizarDigitos(dto.getTelefone()));
        paciente.setCep(normalizarDigitos(dto.getCep()));
        paciente.setRua(normalizarTexto(dto.getRua()));
        paciente.setBairro(normalizarTexto(dto.getBairro()));
        paciente.setCidade(normalizarTexto(dto.getCidade()));
        paciente.setEstado(normalizarEstado(dto.getEstado()));
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim();
    }

    private String normalizarDigitos(String valor) {
        return valor == null ? null : valor.replaceAll("\\D", "");
    }

    private String normalizarEstado(String valor) {
        String estado = normalizarTexto(valor);
        return estado == null ? null : estado.toUpperCase();
    }
}
