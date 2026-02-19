package prominence.divisao7.cash_register.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "idiomas")
public class Idioma {

    @ColumnInfo(name = "id_idioma")
    @PrimaryKey(autoGenerate = true)
    private int id_idioma;

    @ColumnInfo(name = "nomeISO_idioma")
    private String nomeISO_idioma;

    @ColumnInfo(name = "primeira_alteracao")
    private boolean primeira_alteracao;



    //construtores
    public Idioma(String nomeISO_idioma, boolean primeira_alteracao) {
        this.nomeISO_idioma = nomeISO_idioma;
        this.primeira_alteracao = primeira_alteracao;
    }


    //Gets e sets
    public int getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(int id_idioma) {
        this.id_idioma = id_idioma;
    }

    public String getNomeISO_idioma() {
        return nomeISO_idioma;
    }

    public void setNomeISO_idioma(String nomeISO_idioma) {
        this.nomeISO_idioma = nomeISO_idioma;
    }

    public boolean isPrimeira_alteracao() {
        return primeira_alteracao;
    }

    public void setPrimeira_alteracao(boolean primeira_alteracao) {
        this.primeira_alteracao = primeira_alteracao;
    }
}
