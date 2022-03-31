package br.com.ribeiro.apirest.repository;

import br.com.ribeiro.apirest.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findByNome(String nome);

}
