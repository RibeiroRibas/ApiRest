package br.com.ribeiro.apirest.controller.dto;

import br.com.ribeiro.apirest.model.StatusTopico;
import br.com.ribeiro.apirest.model.Topico;

import java.util.List;
import java.util.stream.Collectors;

public class DetalhesDoTopicoDto extends TopicoDto{

    private  String nomeAutor;
    private StatusTopico status;
    private List<RespostaDto> respostas;

    public DetalhesDoTopicoDto(Topico topico) {
        super(topico);
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus();
        this.respostas = topico.getRespostas().stream()
                .map(RespostaDto::new).collect(Collectors.toList());
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDto> getRespostas() {
        return respostas;
    }
}
