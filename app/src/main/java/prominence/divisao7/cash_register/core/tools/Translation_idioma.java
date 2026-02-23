package prominence.divisao7.cash_register.core.tools;

import android.app.LocaleManager;
import android.content.Context;
import android.os.Build;
import android.os.LocaleList;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Optional;

import prominence.divisao7.cash_register.core.database.Conexao;
import prominence.divisao7.cash_register.core.tools.exceptions.NotFoundException;
import prominence.divisao7.cash_register.domain.model.Idioma;

public class Translation_idioma {


    public static void setLocale(String idioma, Context contexto) {

        Conexao conexao_db = Conexao.getInstance(contexto);

        //Se um idioma for escolhido no spinner, o valor dele será salvo na base de dados
        if (idioma != null) {
            conexao_db.idiomaRepository().updateIdioma(idioma);
            conexao_db.idiomaRepository().updateStatusAlteracao(false);
        }


        //Validando se foi encontrado idioma salvo na base de dados
        Optional<Idioma> idiomaSalvo = conexao_db.idiomaRepository().findLanguage();
        if (idiomaSalvo.isEmpty()) {
            throw new NotFoundException("Não foi encontrado nenhum idioma salvo");
        }


        //Alterando idioma
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            contexto.getSystemService(LocaleManager.class)
                    .setApplicationLocales(LocaleList
                            .forLanguageTags(idiomaSalvo.get().getNomeISO_idioma()));

            return;
        }


        AppCompatDelegate
                .setApplicationLocales(LocaleListCompat
                        .forLanguageTags(idiomaSalvo.get().getNomeISO_idioma()));


    }

}
