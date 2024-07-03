package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController // o Spring carrega nossa classe
@RequestMapping("/medicos") // mapear a URL /medicos
public class MedicoController {

    @Autowired //estou injentanso o repository como sendo um atributo
    private MedicoRepository repository;

    @PostMapping // é para voce chamar o metodo cadastrar da classe medicoController
    @Transactional // para ter trasação ativa com o banco de dados.
    public ResponseEntity cadastrar(@RequestBody  @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) { //@RequestBody é para puxar do corpo da requisição.
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity <Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page =  repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity  atualizar(@RequestBody  @Valid DadosAtualizacaoMedico dados ) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
//@Valid serve para o Spring se integrar com o Bean Validation
//subistiuiu void utilizaremos uma classe do Spring chamada ResponseEntity, sendo uma classe que conseguimos controlar
// a resposta devolvida pelo framework.