package prominence.divisao7.cash_register.ui.configuracoes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import prominence.divisao7.cash_register.R;

public class Termos_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termos);

    }


    private void irParaTermos() {
        findViewById(R.id.txt_termos).setOnClickListener((e) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(""));
            startActivity(intent);
        });
    }


}