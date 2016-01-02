package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.historicar.app.R;
import com.historicar.app.bean.Multa;
import com.historicar.app.contants.Constants;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 19/04/15.
 */
public class DetailsActivity extends AppCompatActivity
{

    private Context ctx;

    @Bind(R.id.detailsRelativeLayoutLayout)
    protected RelativeLayout rl;

    private int id = 1;

    private int lastId;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(true);

        ctx = this;


        Bundle bundle = getIntent().getExtras();
        Multa multa = (Multa) bundle.getSerializable(Constants.MULTA);

        RelativeLayout.LayoutParams llp0 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        llp0.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llp0.setMargins(0, 0, 0, 10);

        /** DADOS DO VEÍCULO E INFRAÇÃO **/

        /** INFRACAO **/
        TextView infracaoValueTextView = new TextView(this);
        infracaoValueTextView.setLayoutParams(llp0);
        infracaoValueTextView.setId(id);
        infracaoValueTextView.setText(multa.getDescricao());
        infracaoValueTextView.setTypeface(Typeface.DEFAULT_BOLD);
        infracaoValueTextView.setTextAppearance(this, android.R.style.TextAppearance_Large);
        rl.addView(infracaoValueTextView);

        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getPx());
        llp.addRule(RelativeLayout.BELOW, infracaoValueTextView.getId());
        llp.setMargins(0, 10, 0, 10);

        View hrView = new View(this);
        hrView.setId(++id);
        hrView.setBackgroundColor(getResources().getColor(R.color.actionbar_background));
        hrView.setLayoutParams(llp);

        rl.addView(hrView);

        lastId = hrView.getId();

        /** TIPO **/
        if (multa.getType() != null && !multa.getType().contains("---"))
        {
            createRow("Tipo:", multa.getType());
        }

        /** AUTO DE INFRAÇÃO **/
        if (multa.getAuto() != null && !multa.getAuto().contains("---"))
        {
            createRow("Auto de Infração:", multa.getAuto());
        }

        /** CÓDIGO DETRAN **/
        if (multa.getCodDetran() != null && !multa.getCodDetran().contains("---"))
        {
            createRow("Código Detran:", multa.getCodDetran());
        }

        /** DATA E HORA DA INFRACAO **/
        if (multa.getDataAutuacao() != null && !multa.getDataAutuacao().contains("---"))
        {
            createRow("Data/Hora da Infração:", multa.getDataHoraInfracao());
        }

        /** LOCAL DA INFRACAO **/
        if (multa.getLocal() != null && !multa.getLocal().contains("---"))
        {
            createRow("Local de Infração:", multa.getLocal());
        }

        /** COD INFRACAO **/
        if (multa.getCodInfracao() != null && !multa.getCodInfracao().contains("---"))
        {
            createRow("Código da Infração:", multa.getCodInfracao());
        }

        /** PONTOS **/
        if (multa.getPontos() != null && !multa.getPontos().contains("---"))
        {
            createRow("Pontos:", multa.getPontos());
        }

        /** GRAVIDADE **/
        if (multa.getGravidade() != null && !multa.getGravidade().contains("---"))
        {
            createRow("Gravidade:", multa.getGravidade());
        }

        /** VELOCIDADE AFERIDA **/
        if (multa.getVelocidadeAferida() != null && !multa.getVelocidadeAferida().contains("---"))
        {
            createRow("Velocidade Aferida:", multa.getVelocidadeAferida());
        }

        /** VELOCIDADE PERMITIDA **/
        if (multa.getVelocidadeMax() != null && !multa.getVelocidadeMax().contains("---"))
        {
            createRow("Velocidade Permitida:", multa.getVelocidadeMax());
        }

        /** EQUIPAMENTO **/
        if (multa.getEquipamento() != null && !multa.getEquipamento().contains("---"))
        {
            createRow("Equipamento:", multa.getEquipamento());
        }

        /** AFERIDO CERTIFICADO **/
        if (multa.getAferidoCertificado() != null && !multa.getAferidoCertificado().contains("---"))
        {
            createRow("Aferido/Certificado:", multa.getAferidoCertificado());
        }

        /** STATUS **/
        if (multa.getStatus() != null && !multa.getStatus().contains("---"))
        {
            createRow("Status:", multa.getStatus());
        }

        /** AUTUAÇÃO **/
        if (multa.getAutuacao() != null && !multa.getAutuacao().contains("---"))
        {
            createRow("Autuação:", multa.getAutuacao());
        }

        /** NUMERO NOTIFICACAO AUTUACAO **/
        if (multa.getNotificacaoAutuacao() != null && !multa.getNotificacaoAutuacao().contains("---"))
        {
            createRow("Notificação:", multa.getNotificacaoAutuacao());
        }

        /** DATA AUTUACAO **/
        if (multa.getDataAutuacao() != null && !multa.getDataAutuacao().contains("---"))
        {
            createRow("Data:", multa.getDataAutuacao());
        }

        /** DATA POSTAGEM AUTUACAO **/
        if (multa.getPostagemAutuacao() != null && !multa.getPostagemAutuacao().contains("---"))
        {
            createRow("Postagem:", multa.getPostagemAutuacao());
        }

        /** SITUACAO AR AUTUACAO **/
        if (multa.getPublicDomRJAutuacao() != null && !multa.getPublicDomRJAutuacao().contains("---"))
        {
            createRow("Public.dom RJ:", multa.getPublicDomRJAutuacao());
        }

        /** NUMERO NOTIFICACAO PENALIDADE **/
        if (multa.getNumeroARAutuacao() != null && !multa.getNumeroARAutuacao().contains("---"))
        {
            createRow("Número do AR:", multa.getNumeroARAutuacao());
        }

        /** NUMERO AR PENALIDADE **/
        if (multa.getSituacaoARAutuacao() != null && !multa.getSituacaoARAutuacao().contains("---"))
        {
            createRow("Situação do AR:", multa.getSituacaoARAutuacao());
        }

        if(multa.isHasRecurso())
        {
            /** NUMERO AR PENALIDADE **/
            if (multa.getRecurso() != null && !multa.getRecurso().contains("---"))
            {
                createRow("Recurso:", multa.getRecurso());
            }

            /** NUMERO AR PENALIDADE **/
            if (multa.getProcessoData() != null && !multa.getProcessoData().contains("---"))
            {
                createRow("Processo (Real infrator) - Data:", multa.getProcessoData());
            }

            /** NUMERO AR PENALIDADE **/
            if (multa.getProcessoSituacao() != null && !multa.getProcessoSituacao().contains("---"))
            {
                createRow("Processo (Real infrator) - Situação:", multa.getProcessoSituacao());
            }
        }

        /** DATA POSTAGEM PENALIDADE **/
        if (multa.getPenalidade() != null && !multa.getPenalidade().contains("---"))
        {
            createRow("Penalidade:", multa.getPenalidade());
        }

        /** DATA VENCIMENTO **/
        if (multa.getNotificacaoPenalidade() != null && !multa.getNotificacaoPenalidade().contains("---"))
        {
            createRow("Notificação:", multa.getNotificacaoPenalidade());
        }

        /** VALOR PAGO **/
        if (multa.getDataPenalidade() != null && !multa.getDataPenalidade().contains("---"))
        {
            createRow("Data:", multa.getDataPenalidade());
        }

        /** DATA VENCIMENTO **/
        if (multa.getPostagemPenalidade() != null && !multa.getPostagemPenalidade().contains("---"))
        {
            createRow("Postagem:", multa.getPostagemPenalidade());
        }

        /** DATA DO PAGAMENTO **/
        if (multa.getPublicDomRJPenalidade() != null && !multa.getPublicDomRJPenalidade().contains("---"))
        {
            createRow("Public.dom RJ:", multa.getPublicDomRJPenalidade());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getNumeroARPenalidade() != null && !multa.getNumeroARPenalidade().contains("---"))
        {
            createRow("AR:", multa.getNumeroARPenalidade());
        }

        /** LOTE **/
        if (multa.getLote() != null && !multa.getLote().contains("---"))
        {
            createRow("IDOBJ./LOTE:", multa.getLote());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getSituacaoARPenalidade() != null && !multa.getSituacaoARPenalidade().contains("---"))
        {
            createRow("Situação da Postagem:", multa.getSituacaoARPenalidade());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getDadosParaPagamento() != null && !multa.getDadosParaPagamento().contains("---"))
        {
            createRow("Dados para pagamento:", multa.getDadosParaPagamento());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getVencimento() != null && !multa.getVencimento().contains("---"))
        {
            createRow("Vencimento:", multa.getVencimento());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getValorAPagar() != null && !multa.getValorAPagar().contains("---"))
        {
            createRow("Valor até o vencimento:", multa.getValorAPagar());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getDataDoPagamento() != null && !multa.getDataDoPagamento().contains("---"))
        {
            createRow("Pagamento:", multa.getDataDoPagamento());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getValorPago() != null && !multa.getValorPago().contains("---"))
        {
            createRow("Valor Pago:", multa.getValorPago());
        }

        /** SITUACAO PAGAMENTO **/
        if (multa.getSituacaoDoPagamento() != null && !multa.getSituacaoDoPagamento().contains("---"))
        {
            createRow("Situação do Pagamento:", multa.getSituacaoDoPagamento());
        }

    }

    private void createRow(String typeText, String typeTextValue)
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
        hrView3.setBackgroundColor(getResources().getColor(R.color.actionbar_background));
        hrView3.setLayoutParams(llp3);

        rl.addView(hrView3);

        lastId = hrView3.getId();
    }

    private int getPx ()
    {
        float density = getResources().getDisplayMetrics().density;
        return (int) (2 * density + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_example));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit (String s)
            {

                if (!ValidateUtils.isOnline(ctx))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    AlertDialog alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_connection), button);
                    alertDialog.show();
                    return false;
                }
                else if (!ValidateUtils.validatePlate(s))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    AlertDialog alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_plate), button);
                    alertDialog.show();
                    return false;
                }

                Intent myIntent = new Intent(ctx, ResultActivity.class);
                myIntent.putExtra(Constants.PLACA_KEY, s);
                startActivity(myIntent);
                finish();
                return true;

            }

            @Override
            public boolean onQueryTextChange (String s)
            {
                if (s.length() > 7)
                {
                    searchView.setQuery(s.substring(0, 7), false);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_insert_or_edit:
                startActivity(new Intent(ctx, InsertOrEditActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }
}
