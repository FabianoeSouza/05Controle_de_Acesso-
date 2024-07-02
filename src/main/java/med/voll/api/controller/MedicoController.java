package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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


}
//@ Valid serve para o Spring se integrar com o Bean Validation