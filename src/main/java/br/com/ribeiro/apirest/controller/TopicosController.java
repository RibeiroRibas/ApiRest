package br.com.ribeiro.apirest.controller;

import br.com.ribeiro.apirest.controller.dto.TopicoDto;
import br.com.ribeiro.apirest.controller.form.AtualizaTopicoForm;
import br.com.ribeiro.apirest.controller.form.TopicoForm;
import br.com.ribeiro.apirest.model.Topico;
import br.com.ribeiro.apirest.repository.CursoRepository;
import br.com.ribeiro.apirest.controller.dto.DetalhesDoTopicoDto;
import br.com.ribeiro.apirest.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos") // url padrão
public class TopicosController {

//    // com duas url iguais temo ambiguidade, usamos nesse caso o metodo http ara diferenciar
//    @RequestMapping(value="/topicos",method = RequestMethod.POST)

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // com duas url iguais temo ambiguidade, usamos nesse caso o metodo http ara diferenciar

    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            //a interface TopicoRepository está herdando de uma interface que já possue os métodos padrões do crud
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        } else {
            // findByCursoNome() = padrão de nomenclatura pra gerar a query automáticamente,
            // tem que seguir o padrão =findBy(nome do atributo) - se for entidade dá pra concatenar
            // no caso de ambiquidade no nome do atributo onde o mesmo tenha um nome igual ao exemplo em baixo ficaria assim nome_curso
            // para usar outro no lugar do padrão tem que ensinar o spring data, no caso fazendo uma query na mão
            // nesse caso fica @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
            // (@Param("nomeCurso") String nomeCurso)
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }

    // com duas url iguais temo ambiguidade, usamos nesse caso o metodo http ara diferenciar
    //request body é pra pegar no corpo da requisição e não como parametro de url
    //@valid indica que o spring Para o Spring disparar as validações do Bean Validation e devolver um erro 400, caso alguma informação enviada pelo cliente esteja inválida
    //para interceptar as exception cliamos uma classe com a anotação @RestController advice
    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoFrom, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoFrom.converter(cursoRepository);
        topicoRepository.save(topico);
        // devolve o codigo 201 http, cria um recurso
        //devolve um cabeçalho http chamado location com a url
        // devolve uma represenação do recurso (body)
        // precisa passar um parametro Uri

        // caminho da uri = location
        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    //definindo um path com partes dinamicas @GetMapping("/{id}")
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id){//essa variavel id representa o id da url gerado na linha de cima, tem que srer o mesmo nome
        //@findById usado para tratar exceções caso não exista um id valido
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()){
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}") // sobreescrever um recurso já o PatchMapping cria uma pequena atualização apenas nos recursos que foram modificado
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,@RequestBody @Valid AtualizaTopicoForm topicoFrom){
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if(topicoOptional.isPresent()){
            Topico topico = topicoFrom.atualizar(id,topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if(topicoOptional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
