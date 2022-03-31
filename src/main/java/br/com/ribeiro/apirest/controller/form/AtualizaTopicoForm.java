package br.com.ribeiro.apirest.controller.form;

import br.com.ribeiro.apirest.model.Topico;
import br.com.ribeiro.apirest.repository.TopicoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AtualizaTopicoForm {
    @NotNull
    @NotEmpty
    private String titulo;
    @NotNull @NotEmpty
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    //o update já esta sendo feito automaticamente pela jpa, não precisa fazer mais nada
    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getById(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
