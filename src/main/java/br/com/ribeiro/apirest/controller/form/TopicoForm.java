package br.com.ribeiro.apirest.controller.form;

import br.com.ribeiro.apirest.repository.CursoRepository;
import br.com.ribeiro.apirest.model.Curso;
import br.com.ribeiro.apirest.model.Topico;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicoForm {

    //anotações da biblioteca bean validatios reponsavel por validar os campos

    @NotNull @NotEmpty @Length(min = 2)
    private String titulo;
    @NotNull @NotEmpty
    private String mensagem;
    @NotNull @NotEmpty
    private String nomeCurso;

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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository repository) {
        Curso curso = repository.findByNome(nomeCurso);
        return new Topico(titulo,mensagem,curso);
    }
}
