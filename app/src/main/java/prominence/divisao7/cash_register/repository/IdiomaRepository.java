package prominence.divisao7.cash_register.repository;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.Optional;

import prominence.divisao7.cash_register.model.Idioma;

@Dao
public interface IdiomaRepository {

    @Query("SELECT * FROM idiomas")
    List<Idioma> findAll();


    @Query("SELECT * FROM idiomas WHERE id_idioma = :id_idioma")
    Idioma findById(int id_idioma);


    @Query("SELECT * FROM idiomas WHERE nomeISO_idioma = :nomeISO_idioma")
    Idioma findByName(String nomeISO_idioma);

    @Query("SELECT * FROM idiomas LIMIT 1")
    Optional<Idioma> findLanguage();


    @Insert
    void save(Idioma idioma);

    //Nesse contexto não é necessário WHERE
    @Query("UPDATE idiomas SET nomeISO_idioma = :nomeISO_idioma")
    void updateIdioma(String nomeISO_idioma);

    @Query("UPDATE idiomas SET primeira_alteracao = :primeira_alteracao")
    void updateStatusAlteracao(boolean primeira_alteracao);
}
