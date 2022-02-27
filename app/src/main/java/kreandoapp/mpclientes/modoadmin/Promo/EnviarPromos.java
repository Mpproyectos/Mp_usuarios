package kreandoapp.mpclientes.modoadmin.Promo;

import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import kreandoapp.mpclientes.R;

public class EnviarPromos extends AppCompatActivity {
    private WebView browser;
    private ProgressBar progressBar;
    Button btn_salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_promos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Enviar promociones");
        // Definimos el webView
        browser=(WebView)findViewById(R.id.webView);

        //Habilitamos JavaScript
        browser.getSettings().setJavaScriptEnabled(true);

        //Habilitamos los botones de Zoom
        btn_salir = findViewById(R.id.btn_salir);

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        browser.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // Cargamos la web
        browser.loadUrl("http://www.kreandoapp.com/andreaf/index2.html");

        //Sincronizamos la barra de progreso de la web
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        browser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                EnviarPromos.this.setProgress(progress * 1000);

                progressBar.incrementProgressBy(progress);

                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        if (browser.canGoBack())
        {
            browser.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}