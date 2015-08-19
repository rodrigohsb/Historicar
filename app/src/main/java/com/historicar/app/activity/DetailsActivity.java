package com.historicar.app.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.bean.Multa;
import com.historicar.app.contants.Constants;

/**
 * Created by Rodrigo on 19/04/15.
 */
public class DetailsActivity extends AppCompatActivity
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

        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx());
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
            lastId = createRow(rl, "Tipo:", multa.getType(), lastId);
        }

        /** AUTO DE INFRAÇÃO **/
        if (multa.getAutoInfracao() != null)
        {
            lastId = createRow(rl, "Auto de Infração:", multa.getAutoInfracao(), lastId);
        }

        /** DATA E HORA DA INFRACAO **/
        if (multa.getDateInfracao() != null)
        {
            lastId = createRow(rl, "Data/Hora da Infração:", multa.getDateInfracao(), lastId);
        }

        /** PONTO / GRAVIDADE **/
        if (multa.getPontosGravidade() != null)
        {
            lastId = createRow(rl, "Pontos-Gravidade:", multa.getPontosGravidade(), lastId);
        }
        /** LOCAL DA INFRACAO **/
        if (multa.getLocal() != null)
        {
            lastId = createRow(rl, "Local de Infração:", multa.getLocal(), lastId);
        }
        /** VELOCIDADE PERMITIDA **/
        if (multa.getVelocidadeMax() != null)
        {
            lastId = createRow(rl, "Velocidade Permitida:", multa.getVelocidadeMax() + " Km/h", lastId);
        }

        /** VELOCIDADE AFERIDA **/
        if (multa.getVelocidadeAferida() != null)
        {
            lastId = createRow(rl, "Velocidade Aferida:", multa.getVelocidadeAferida() + " Km/h", lastId);
        }
        /** SITUACAO INFRACAO **/
        if (multa.getSituacaoInfracao() != null)
        {
            lastId = createRow(rl, "Situação Infração:", multa.getSituacaoInfracao(), lastId);
        }

        /** NUMERO NOTIFICACAO AUTUACAO **/
        if (multa.getNumeroNotificacaoAutuacao() != null)
        {
            lastId = createRow(rl, "Número da Notificação Autuação:", multa.getNumeroNotificacaoAutuacao(), lastId);
        }

        /** NUMERO AR AUTUACAO **/
        if (multa.getNumeroArAutuacao() != null)
        {
            lastId = createRow(rl, "Número AR Autuação:", multa.getNumeroArAutuacao(), lastId);
        }

        /** DATA POSTAGEM AUTUACAO **/
        if (multa.getDataPostagemAutuacao() != null)
        {
            lastId = createRow(rl, "Data de Postagem Autuacao:", multa.getDataPostagemAutuacao(), lastId);
        }

        /** SITUACAO AR AUTUACAO **/
        if (multa.getSituacaoArAutuacao() != null)
        {
            lastId = createRow(rl, "Situação AR Autuação:", multa.getSituacaoArAutuacao(), lastId);
        }

        /** NUMERO NOTIFICACAO PENALIDADE **/
        if (multa.getNumeroNotificacaoPenalidade() != null)
        {
            lastId = createRow(rl, "Numero da Notificacao Penalidade:", multa.getNumeroNotificacaoPenalidade(), lastId);
        }

        /** NUMERO AR PENALIDADE **/
        if (multa.getNumeroArPenalidade() != null)
        {
            lastId = createRow(rl, "Número AR Penalidade:", multa.getNumeroArPenalidade(), lastId);
        }
        /** DATA POSTAGEM PENALIDADE **/
        if (multa.getDataPostagemPenalidade() != null)
        {
            lastId = createRow(rl, "Data de Postagem Penalidade:", multa.getDataPostagemPenalidade(), lastId);
        }

        /** DATA VENCIMENTO **/
        if (multa.getVencimento() != null)
        {
            lastId = createRow(rl, "Data Vencimento:", multa.getVencimento(), lastId);
        }

        /** VALOR PAGO **/
        if (multa.getValorPago() != null)
        {
            lastId = createRow(rl, "Valor Pago:", multa.getValorPago(), lastId);
        }

        /** DATA DO PAGAMENTO **/
        if (multa.getDataPagamento() != null)
        {
            lastId = createRow(rl, "Data do Pagamento:", multa.getDataPagamento(), lastId);
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getSituacaoPagamento() != null)
        {
            createRow(rl, "Situação do Pagamento:", multa.getSituacaoPagamento(), lastId);
        }
    }

    private int createRow(RelativeLayout rl, String typeText, String typeTextValue, int lastId)
    {
        RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        llp1.addRule(RelativeLayout.BELOW, lastId);
        llp1.setMargins(0, 10, 0, 10);

        TextView typeTextView = new TextView(this);
        typeTextView.setLayoutParams(llp1);
        typeTextView.setId(++id);
        typeTextView.setText(typeText);
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
        typeValueTextView.setText(typeTextValue);

        rl.addView(typeValueTextView);

        RelativeLayout.LayoutParams llp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx());
        llp3.addRule(RelativeLayout.BELOW, typeValueTextView.getId());
        llp3.setMargins(0, 10, 0, 10);

        View hrView3 = new View(this);
        hrView3.setId(++id);
        hrView3.setBackgroundColor(getResources().getColor(R.color.app_background));
        hrView3.setLayoutParams(llp3);

        rl.addView(hrView3);

        lastId = hrView3.getId();

        return lastId;
    }

    private int getPx ()
    {
        float density = getResources().getDisplayMetrics().density;
        return (int) (2 * density + 0.5f);
    }

    @Override
    public void onBackPressed ()
    {
        finish();
        super.onBackPressed();
    }
}
