package com.historicar.app.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.bean.Multa;
import com.historicar.app.contants.Constants;

/**
 * Created by Rodrigo on 19/04/15.
 */
public class DetailsActivity extends ActionBarActivity
{

    private int id = 1;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();
        Multa multa = (Multa) bundle.getSerializable(Constants.MULTA);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.detailsRelativeLayoutLayout);

        RelativeLayout.LayoutParams llp0 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        llp0.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llp0.setMargins(0, 0, 0, 10);

        /** INFRACAO **/
        TextView infracaoValueTextView = new TextView(this);
        infracaoValueTextView.setLayoutParams(llp0);
        infracaoValueTextView.setId(id);
        infracaoValueTextView.setText(multa.getInfracao());
        infracaoValueTextView.setTypeface(Typeface.DEFAULT_BOLD);
        infracaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Large);
        rl.addView(infracaoValueTextView);

        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
        llp.addRule(RelativeLayout.BELOW, infracaoValueTextView.getId());
        llp.setMargins(0, 10, 0, 10);

        View hrView = new View(this);
        hrView.setId(++id);
        hrView.setBackgroundColor(getResources().getColor(R.color.app_background));
        hrView.setLayoutParams(llp);

        rl.addView(hrView);

        int lastId = hrView.getId();

        /** TIPO **/
        if (multa.getType() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 10, 0, 10);

            TextView typeTextView = new TextView(this);
            typeTextView.setLayoutParams(llp1);
            typeTextView.setId(++id);
            typeTextView.setTextColor(getResources().getColor(R.color.app_background));
            typeTextView.setText("Tipo:");
            typeTextView.setTypeface(Typeface.DEFAULT_BOLD);
            typeTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(typeTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, typeTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView typeValueTextView = new TextView(this);
            typeValueTextView.setLayoutParams(llp2);
            typeValueTextView.setId(++id);
            typeValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            typeValueTextView.setText(multa.getType());

            rl.addView(typeValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, typeValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** AUTO DE INFRAÇÃO **/
        if (multa.getAutoInfracao() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView autoInfracaoTextView = new TextView(this);
            autoInfracaoTextView.setLayoutParams(llp1);
            autoInfracaoTextView.setId(++id);
            autoInfracaoTextView.setText("Auto de Infração:");
            autoInfracaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            autoInfracaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(autoInfracaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, autoInfracaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView autoInfracaoValueTextView = new TextView(this);
            autoInfracaoValueTextView.setLayoutParams(llp2);
            autoInfracaoValueTextView.setId(++id);
            autoInfracaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            autoInfracaoValueTextView.setText(multa.getAutoInfracao());

            rl.addView(autoInfracaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, autoInfracaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();

        }

        /** DATA E HORA DA INFRACAO **/
        if (multa.getDateInfracao() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView dataHoraInfracaoTextView = new TextView(this);
            dataHoraInfracaoTextView.setLayoutParams(llp1);
            dataHoraInfracaoTextView.setId(++id);
            dataHoraInfracaoTextView.setText("Data/Hora da Infração:");
            dataHoraInfracaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            dataHoraInfracaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(dataHoraInfracaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, dataHoraInfracaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView dataHoraInfracaoValueTextView = new TextView(this);
            dataHoraInfracaoValueTextView.setLayoutParams(llp2);
            dataHoraInfracaoValueTextView.setId(++id);
            dataHoraInfracaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            dataHoraInfracaoValueTextView.setText(multa.getDateInfracao());

            rl.addView(dataHoraInfracaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, dataHoraInfracaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** PONTO / GRAVIDADE **/
        if (multa.getPontosGravidade() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView pontoGravidadeTextView = new TextView(this);
            pontoGravidadeTextView.setLayoutParams(llp1);
            pontoGravidadeTextView.setId(++id);
            pontoGravidadeTextView.setText("Pontos-Gravidade:");
            pontoGravidadeTextView.setTypeface(Typeface.DEFAULT_BOLD);
            pontoGravidadeTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(pontoGravidadeTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, pontoGravidadeTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView pontoGravidadeValueTextView = new TextView(this);
            pontoGravidadeValueTextView.setLayoutParams(llp2);
            pontoGravidadeValueTextView.setId(++id);
            pontoGravidadeValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            pontoGravidadeValueTextView.setText(multa.getPontosGravidade());

            rl.addView(pontoGravidadeValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, pontoGravidadeValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();

        }
        /** LOCAL DA INFRACAO **/
        if (multa.getLocal() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView localInfracaoTextView = new TextView(this);
            localInfracaoTextView.setLayoutParams(llp1);
            localInfracaoTextView.setId(++id);
            localInfracaoTextView.setText("Local de Infração:");
            localInfracaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            localInfracaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(localInfracaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, localInfracaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView localInfracaoValueTextView = new TextView(this);
            localInfracaoValueTextView.setLayoutParams(llp2);
            localInfracaoValueTextView.setId(++id);
            localInfracaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            localInfracaoValueTextView.setText(multa.getLocal());

            rl.addView(localInfracaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, localInfracaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();

        }
        /** VELOCIDADE PERMITIDA **/
        if (multa.getVelocidadeMax() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView velocidadePermitidaTextView = new TextView(this);
            velocidadePermitidaTextView.setLayoutParams(llp1);
            velocidadePermitidaTextView.setId(++id);
            velocidadePermitidaTextView.setText("Velocidade Permitida:");
            velocidadePermitidaTextView.setTypeface(Typeface.DEFAULT_BOLD);
            velocidadePermitidaTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(velocidadePermitidaTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, velocidadePermitidaTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView velocidadePermitidaValueTextView = new TextView(this);
            velocidadePermitidaValueTextView.setLayoutParams(llp2);
            velocidadePermitidaValueTextView.setId(++id);
            velocidadePermitidaValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            velocidadePermitidaValueTextView.setText(multa.getVelocidadeMax() + " Km/h");

            rl.addView(velocidadePermitidaValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, velocidadePermitidaValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** VELOCIDADE AFERIDA **/
        if (multa.getVelocidadeAferida() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView velocidadeAferidaTextView = new TextView(this);
            velocidadeAferidaTextView.setLayoutParams(llp1);
            velocidadeAferidaTextView.setId(++id);
            velocidadeAferidaTextView.setText("Velocidade Aferida");
            velocidadeAferidaTextView.setTypeface(Typeface.DEFAULT_BOLD);
            velocidadeAferidaTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(velocidadeAferidaTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, velocidadeAferidaTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView velocidadeAferidaValueTextView = new TextView(this);
            velocidadeAferidaValueTextView.setLayoutParams(llp2);
            velocidadeAferidaValueTextView.setId(++id);
            velocidadeAferidaValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            velocidadeAferidaValueTextView.setText(multa.getVelocidadeAferida() + " Km/h");

            rl.addView(velocidadeAferidaValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, velocidadeAferidaValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();

        }
        /** SITUACAO INFRACAO **/
        if (multa.getSituacaoInfracao() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView situacaoInfracaoTextView = new TextView(this);
            situacaoInfracaoTextView.setLayoutParams(llp1);
            situacaoInfracaoTextView.setId(++id);
            situacaoInfracaoTextView.setText("Situação Infração:");
            situacaoInfracaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            situacaoInfracaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(situacaoInfracaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, situacaoInfracaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView situacaoInfracaoValueTextView = new TextView(this);
            situacaoInfracaoValueTextView.setLayoutParams(llp2);
            situacaoInfracaoValueTextView.setId(++id);
            situacaoInfracaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            situacaoInfracaoValueTextView.setText(multa.getSituacaoInfracao());

            rl.addView(situacaoInfracaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, situacaoInfracaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** NUMERO NOTIFICACAO AUTUACAO **/
        if (multa.getNumeroNotificacaoAutuacao() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView numeroNotificacaoAutuacaoTextView = new TextView(this);
            numeroNotificacaoAutuacaoTextView.setLayoutParams(llp1);
            numeroNotificacaoAutuacaoTextView.setId(++id);
            numeroNotificacaoAutuacaoTextView.setText("Número da Notificação Autuação:");
            numeroNotificacaoAutuacaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            numeroNotificacaoAutuacaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(numeroNotificacaoAutuacaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, numeroNotificacaoAutuacaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView numeroNotificacaoAutuacaoValueTextView = new TextView(this);
            numeroNotificacaoAutuacaoValueTextView.setLayoutParams(llp2);
            numeroNotificacaoAutuacaoValueTextView.setId(++id);
            numeroNotificacaoAutuacaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            numeroNotificacaoAutuacaoValueTextView.setText(multa.getNumeroNotificacaoAutuacao());

            rl.addView(numeroNotificacaoAutuacaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, numeroNotificacaoAutuacaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** NUMERO AR AUTUACAO **/
        if (multa.getNumeroArAutuacao() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView numeroArAutuacaoTextView = new TextView(this);
            numeroArAutuacaoTextView.setLayoutParams(llp1);
            numeroArAutuacaoTextView.setId(++id);
            numeroArAutuacaoTextView.setText("Número AR Autuação:");
            numeroArAutuacaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            numeroArAutuacaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(numeroArAutuacaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, numeroArAutuacaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView numeroArAutuacaoValueTextView = new TextView(this);
            numeroArAutuacaoValueTextView.setLayoutParams(llp2);
            numeroArAutuacaoValueTextView.setId(++id);
            numeroArAutuacaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            numeroArAutuacaoValueTextView.setText(multa.getNumeroArAutuacao());

            rl.addView(numeroArAutuacaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, numeroArAutuacaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** DATA POSTAGEM AUTUACAO **/
        if (multa.getDataPostagemAutuacao() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView dataPostagemAutuacaoTextView = new TextView(this);
            dataPostagemAutuacaoTextView.setLayoutParams(llp1);
            dataPostagemAutuacaoTextView.setId(++id);
            dataPostagemAutuacaoTextView.setText("Data de Postagem Autuacao:");
            dataPostagemAutuacaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            dataPostagemAutuacaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(dataPostagemAutuacaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, dataPostagemAutuacaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView dataPostagemAutuacaoValueTextView = new TextView(this);
            dataPostagemAutuacaoValueTextView.setLayoutParams(llp2);
            dataPostagemAutuacaoValueTextView.setId(++id);
            dataPostagemAutuacaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            dataPostagemAutuacaoValueTextView.setText(multa.getDataPostagemAutuacao());

            rl.addView(dataPostagemAutuacaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, dataPostagemAutuacaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** SITUACAO AR AUTUACAO **/
        if (multa.getSituacaoArAutuacao() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView situacaoArAutuacaoTextView = new TextView(this);
            situacaoArAutuacaoTextView.setLayoutParams(llp1);
            situacaoArAutuacaoTextView.setId(++id);
            situacaoArAutuacaoTextView.setText("Situação AR Autuação:");
            situacaoArAutuacaoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            situacaoArAutuacaoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(situacaoArAutuacaoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, situacaoArAutuacaoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView situacaoArAutuacaoValueTextView = new TextView(this);
            situacaoArAutuacaoValueTextView.setLayoutParams(llp2);
            situacaoArAutuacaoValueTextView.setId(++id);
            situacaoArAutuacaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            situacaoArAutuacaoValueTextView.setText(multa.getSituacaoArAutuacao());

            rl.addView(situacaoArAutuacaoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, situacaoArAutuacaoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** NUMERO NOTIFICACAO PENALIDADE **/
        if (multa.getNumeroNotificacaoPenalidade() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView numeroNotificacaoPenalidadeTextView = new TextView(this);
            numeroNotificacaoPenalidadeTextView.setLayoutParams(llp1);
            numeroNotificacaoPenalidadeTextView.setId(++id);
            numeroNotificacaoPenalidadeTextView.setText("Numero da Notificacao Penalidade:");
            numeroNotificacaoPenalidadeTextView.setTypeface(Typeface.DEFAULT_BOLD);
            numeroNotificacaoPenalidadeTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(numeroNotificacaoPenalidadeTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, numeroNotificacaoPenalidadeTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView numeroNotificacaoPenalidadeValueTextView = new TextView(this);
            numeroNotificacaoPenalidadeValueTextView.setLayoutParams(llp2);
            numeroNotificacaoPenalidadeValueTextView.setId(++id);
            numeroNotificacaoPenalidadeValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            numeroNotificacaoPenalidadeValueTextView.setText(multa.getNumeroNotificacaoPenalidade());

            rl.addView(numeroNotificacaoPenalidadeValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, numeroNotificacaoPenalidadeValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** NUMERO AR PENALIDADE **/
        if (multa.getNumeroArPenalidade() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView numeroArPenalidadeTextView = new TextView(this);
            numeroArPenalidadeTextView.setLayoutParams(llp1);
            numeroArPenalidadeTextView.setText("Número AR Penalidade:");
            numeroArPenalidadeTextView.setId(++id);
            numeroArPenalidadeTextView.setTypeface(Typeface.DEFAULT_BOLD);
            numeroArPenalidadeTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(numeroArPenalidadeTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, numeroArPenalidadeTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView numeroArPenalidadeValueTextView = new TextView(this);
            numeroArPenalidadeValueTextView.setLayoutParams(llp2);
            numeroArPenalidadeValueTextView.setId(++id);
            numeroArPenalidadeValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            numeroArPenalidadeValueTextView.setText(multa.getNumeroArPenalidade());

            rl.addView(numeroArPenalidadeValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, numeroArPenalidadeValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();

        }
        /** DATA POSTAGEM PENALIDADE **/
        if (multa.getDataPostagemPenalidade() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView dataPostagemPenalidadeTextView = new TextView(this);
            dataPostagemPenalidadeTextView.setLayoutParams(llp1);
            dataPostagemPenalidadeTextView.setId(++id);
            dataPostagemPenalidadeTextView.setText("Data de Postagem Penalidade:");
            dataPostagemPenalidadeTextView.setTypeface(Typeface.DEFAULT_BOLD);
            dataPostagemPenalidadeTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(dataPostagemPenalidadeTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, dataPostagemPenalidadeTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView dataPostagemPenalidadeValueTextView = new TextView(this);
            dataPostagemPenalidadeValueTextView.setLayoutParams(llp2);
            dataPostagemPenalidadeValueTextView.setId(++id);
            dataPostagemPenalidadeValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            dataPostagemPenalidadeValueTextView.setText(multa.getDataPostagemPenalidade());

            rl.addView(dataPostagemPenalidadeValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, dataPostagemPenalidadeValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** DATA VENCIMENTO **/
        if (multa.getVencimento() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView dataVencimentoTextView = new TextView(this);
            dataVencimentoTextView.setLayoutParams(llp1);
            dataVencimentoTextView.setId(++id);
            dataVencimentoTextView.setText("Data Vencimento:");
            dataVencimentoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            dataVencimentoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(dataVencimentoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, dataVencimentoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView dataVencimentoValueTextView = new TextView(this);
            dataVencimentoValueTextView.setLayoutParams(llp2);
            dataVencimentoValueTextView.setId(++id);
            dataVencimentoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            dataVencimentoValueTextView.setText(multa.getVencimento());

            rl.addView(dataVencimentoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, dataVencimentoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** VALOR PAGO **/
        if (multa.getValorPago() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView valorPagoTextView = new TextView(this);
            valorPagoTextView.setLayoutParams(llp1);
            valorPagoTextView.setId(++id);
            valorPagoTextView.setText("Valor Pago:");
            valorPagoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            valorPagoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(valorPagoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, valorPagoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView valorPagoValueTextView = new TextView(this);
            valorPagoValueTextView.setLayoutParams(llp2);
            valorPagoValueTextView.setId(++id);
            valorPagoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            valorPagoValueTextView.setText("R$ " + multa.getValorPago());

            rl.addView(valorPagoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, valorPagoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** DATA DO PAGAMENTO **/
        if (multa.getDataPagamento() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView dataDoPagamentoTextView = new TextView(this);
            dataDoPagamentoTextView.setLayoutParams(llp1);
            dataDoPagamentoTextView.setId(++id);
            dataDoPagamentoTextView.setText("Data do Pagamento:");
            dataDoPagamentoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            dataDoPagamentoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(dataDoPagamentoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, dataDoPagamentoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView dataDoPagamentoValueTextView = new TextView(this);
            dataDoPagamentoValueTextView.setLayoutParams(llp2);
            dataDoPagamentoValueTextView.setId(++id);
            dataDoPagamentoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            dataDoPagamentoValueTextView.setText(multa.getDataPagamento());

            rl.addView(dataDoPagamentoValueTextView);

            RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx(2));
            llp3.addRule(RelativeLayout.BELOW, dataDoPagamentoValueTextView.getId());
            llp3.setMargins(0, 10, 0, 10);

            View hrView3 = new View(this);
            hrView3.setId(++id);
            hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
            hrView3.setLayoutParams(llp3);

            rl.addView(hrView3);

            lastId = hrView3.getId();
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getSituacaoPagamento() != null)
        {

            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView situacaoPagamentoTextView = new TextView(this);
            situacaoPagamentoTextView.setLayoutParams(llp1);
            situacaoPagamentoTextView.setId(++id);
            situacaoPagamentoTextView.setText("Situação do Pagamento:");
            situacaoPagamentoTextView.setTypeface(Typeface.DEFAULT_BOLD);
            situacaoPagamentoTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(situacaoPagamentoTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, situacaoPagamentoTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView situacaoPagamentoValueTextView = new TextView(this);
            situacaoPagamentoValueTextView.setLayoutParams(llp2);
            situacaoPagamentoValueTextView.setId(++id);
            situacaoPagamentoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            situacaoPagamentoValueTextView.setText(multa.getSituacaoPagamento());

            rl.addView(situacaoPagamentoValueTextView);
        }
        if (multa.getValorAPagar() != null)
        {
            RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp1.addRule(RelativeLayout.BELOW, lastId);
            llp1.setMargins(0, 0, 0, 0);

            TextView valorAPagarTextView = new TextView(this);
            valorAPagarTextView.setLayoutParams(llp1);
            valorAPagarTextView.setId(++id);
            valorAPagarTextView.setText("Valor a Pagar:");
            valorAPagarTextView.setTypeface(Typeface.DEFAULT_BOLD);
            valorAPagarTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            rl.addView(valorAPagarTextView);

            RelativeLayout.LayoutParams llp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp2.addRule(RelativeLayout.BELOW, valorAPagarTextView.getId());
            llp2.setMargins(0, 10, 0, 10);

            TextView valorAPagarValueTextView = new TextView(this);
            valorAPagarValueTextView.setLayoutParams(llp2);
            valorAPagarValueTextView.setId(++id);
            valorAPagarValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Small);
            valorAPagarValueTextView.setText(multa.getValorAPagar().contains("R$") ? multa.getValorAPagar() : "R$ " + multa.getValorAPagar());

            rl.addView(valorAPagarValueTextView);
        }
    }

    private int getPx (int dimensionDp)
    {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dimensionDp * density + 0.5f);
    }

    @Override
    public void onBackPressed ()
    {
        finish();
        super.onBackPressed();
    }
}
