package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // o Spring carrega nossa classe
@RequestMapping("/medicos") // mapear a URL /medicos
public class MedicoController {

    @Autowired //estou injentanso o repository como sendo um atributo
    private MedicoRepository repository;

    @PostMapping // é para voce chamar o metodo cadastrar da classe medicoController
    @Transactional // para ter trasação ativa com o banco de dados.
    public void cadastrar(@RequestBody  @Valid DadosCadastroMedico dados ) { //@RequestBody é para puxar do corpo da requisição.
        repository.save(new Medico(dados));

    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }


}
//@ Valid serve para o Spring se integrar com o Bean Validation