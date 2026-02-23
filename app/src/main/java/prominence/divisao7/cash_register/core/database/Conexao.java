package prominence.divisao7.cash_register.core.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import prominence.divisao7.cash_register.domain.model.Idioma;
import prominence.divisao7.cash_register.domain.model.Produto;
import prominence.divisao7.cash_register.data.repository.IdiomaRepository;
import prominence.divisao7.cash_register.data.repository.ProdutoRepository;


@Database(entities = {Produto.class, Idioma.class}, version = 1)
public abstract class Conexao extends RoomDatabase {

    private static Conexao INSTANCIA;

    public abstract ProdutoRepository produtoRepository();

    public abstract IdiomaRepository idiomaRepository();


    public static Conexao getInstance(Context contexto) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(contexto.getApplicationContext(), Conexao.class, "db_cash_register")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCIA;
    }

}