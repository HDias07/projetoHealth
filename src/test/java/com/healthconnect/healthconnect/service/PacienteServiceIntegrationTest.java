package com.healthconnect.healthconnect.service;

import com.healthconnect.healthconnect.DTO.ContatoDTO;
import com.healthconnect.healthconnect.DTO.PacienteDTO;
import com.healthconnect.healthconnect.exception.CpfJaCadastradoException;
import com.healthconnect.healthconnect.exception.PacienteNaoEncontradoException;
import com.healthconnect.healthconnect.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class PacienteServiceIntegrationTest {

    @Autowired
    private PacienteService service;

    @Autowired
    private PacienteRepository repository;

    @BeforeEach
    void limparBase() {
        repository.deleteAll();
    }

    @Test
    void deveImpedirCpfDuplicado() {
        PacienteDTO primeiro = criarPaciente("Ana Silva", "12345678901");
        PacienteDTO segundo = criarPaciente("Bruno Souza", "12345678901");

        service.salvar(primeiro);

        assertThrows(CpfJaCadastradoException.class, () -> service.salvar(segundo));
    }

    @Test
    void deveAtualizarContatoDoPacienteExistente() {
        PacienteDTO paciente = criarPaciente("Carla Lima", "98765432100");
        service.salvar(paciente);

        Long id = repository.findAll().getFirst().getId();

        ContatoDTO contato = new ContatoDTO();
        contato.setEmail("carla.novo@email.com");
        contato.setTelefone("11999998888");
        contato.setCep("01001000");
        contato.setRua("Praca da Se");
        contato.setBairro("Se");
        contato.setCidade("Sao Paulo");
        contato.setEstado("sp");

        service.atualizarContato(id, contato);

        var salvo = repository.findById(id).orElseThrow();
        assertEquals("carla.novo@email.com", salvo.getEmail());
        assertEquals("11999998888", salvo.getTelefone());
        assertEquals("01001000", salvo.getCep());
        assertEquals("SP", salvo.getEstado());
    }

    @Test
    void deveLancarErroQuandoPacienteNaoExiste() {
        assertThrows(PacienteNaoEncontradoException.class, () -> service.buscarPorId(999L));
    }

    private PacienteDTO criarPaciente(String nome, String cpf) {
        PacienteDTO dto = new PacienteDTO();
        dto.setNome(nome);
        dto.setCpf(cpf);
        dto.setEmail(nome.toLowerCase().replace(" ", ".") + "@email.com");
        dto.setTelefone("11999999999");
        dto.setCep("01001000");
        dto.setRua("Rua A");
        dto.setBairro("Centro");
        dto.setCidade("Sao Paulo");
        dto.setEstado("SP");
        return dto;
    }
}
