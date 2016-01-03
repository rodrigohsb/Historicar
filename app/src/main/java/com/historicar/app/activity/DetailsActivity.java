package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
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


    // COMMON_SECTION_XML
    @Bind(R.id.detailsSectionCommonTitle)
    protected TextView description;

    @Bind(R.id.detailsSectionCommonPoints)
    protected TextView points;

    @Bind(R.id.detailsSectionCommonGravidade)
    protected TextView gravidade;

    @Bind(R.id.detailsSectionCommonLocal)
    protected TextView local;

    @Bind(R.id.detailsSectionCommonInfracao)
    protected TextView infracao;

    @Bind(R.id.detailsSectionCommonCodigoDetran)
    protected TextView codDetran;

    @Bind(R.id.detailsSectionCommonCodInfracao)
    protected TextView codInfracao;

    @Bind(R.id.detailsSectionCommonType)
    protected TextView type;

    @Bind(R.id.detailsSectionCommonDateHour)
    protected TextView dateHour;

    @Bind(R.id.detailsSectionCommonStatus)
    protected TextView status;

    @Bind(R.id.detailsSectionCommonVelocidade)
    protected LinearLayout velocidadeLinearLayout;
    
    @Bind(R.id.detailsSectionCommonVelocidadeAferida)
    protected TextView velocAferica;

    @Bind(R.id.detailsSectionCommonVelocidadeMax)
    protected TextView velocMax;


    // AUTUACAO_SECTION_XML
    @Bind(R.id.detailsAutuacaoSectionNotificacao)
    protected TextView autuacaoNotificacao;

    @Bind(R.id.detailsAutuacaoSectionDate)
    protected TextView autuacaoDate;

    @Bind(R.id.detailsAutuacaoSectionPostagemLinearLayout)
    protected LinearLayout autuacaoPostagemLinearLayout;

    @Bind(R.id.detailsAutuacaoSectionPostagem)
    protected TextView autuacaoPostagem;

    @Bind(R.id.detailsAutuacaoSectionAR)
    protected TextView autuacaoAr;

    @Bind(R.id.detailsAutuacaoSectionSituacaoPostagem)
    protected TextView autuacaoSituacaoPostagem;


    // RECURSO_SECTION_XML
    @Bind(R.id.detailsRecursoSectionSubtitle)
    protected TextView recursoSubtitle;

    @Bind(R.id.detailsRecursoSectionProcessAndDate)
    protected TextView recursoProcessAndDate;

    @Bind(R.id.detailsRecursoSectionSituationLinearLayout)
    protected LinearLayout recursoSituationLinerLayout;

    @Bind(R.id.detailsRecursoSectionSituation)
    protected TextView recursoSituation;

    // PENALIDADE_SECTION_XML
    @Bind(R.id.detailsSectionPenalidadeNotificacao)
    protected TextView penalidadeNotificacao;

    @Bind(R.id.detailsSectionPenalidadeDate)
    protected TextView penalidadeDate;

    @Bind(R.id.detailsSectionPenalidadePostagemLinearLayout)
    protected LinearLayout penalidadePostgemLinearLayout;

    @Bind(R.id.detailsSectionPenalidadePostagem)
    protected TextView penalidadeDataPostagem;

    @Bind(R.id.detailsSectionPenalidadeAR)
    protected TextView penalidadeAr;

    @Bind(R.id.detailsSectionPenalidadeSituacaoPostagem)
    protected TextView penalidadeSituacaoPostagem;


    // PAYMENT_SECTION_XML
    @Bind(R.id.detailsSectionPaymentVencimentoLinearLayout)
    protected LinearLayout paymentVencimentoLinearLayout;

    @Bind(R.id.detailsSectionPaymentVencimento)
    protected TextView paymentVencimento;

    @Bind(R.id.detailsSectionPaymentValorJaPagoLinearLayout)
    protected LinearLayout paymentValorJaPagoLinearLayout;

    @Bind(R.id.detailsSectionPaymentValorJaPago)
    protected TextView paymentValorJaPago;

    @Bind(R.id.detailsSectionPaymentValorAPagarLinearLayout)
    protected LinearLayout paymentValorAPagarLinerLayout;

    @Bind(R.id.detailsSectionPaymentValorAPagar)
    protected TextView paymentValorAPagar;

    @Bind(R.id.detailsSectionPaymentSituation)
    protected TextView paymentSituacao;


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

        fillCommonUI(multa);
        fillAutuacaoUI(multa);
        
        if(multa.isHasRecurso())
        {
            fillRecursoUI(multa);
        }
        if(multa.isHasPenalidade())
        {
            fillPenalidadeUI(multa);
        }
        fillPaymentUI(multa);
    }
    
    private void fillCommonUI(Multa multa)
    {
        description.setText(multa.getDescricao());

        points.setText(multa.getPontos());
        gravidade.setText(multa.getGravidade());

        local.setText(multa.getLocal());

        infracao.setText(multa.getInfracao());
        codDetran.setText(multa.getCodDetran());

        codInfracao.setText(multa.getCodInfracao());
        type.setText(multa.getType());

        dateHour.setText(multa.getDataHoraInfracao());
        status.setText(multa.getStatus());

        if(multa.getVelocidadeAferida() != null && multa.getVelocidadeMax() != null)
        {
            velocidadeLinearLayout.setVisibility(View.VISIBLE);
            velocAferica.setText(multa.getVelocidadeAferida());
            velocMax.setText(multa.getVelocidadeMax());
        }
    }
    
    private void fillAutuacaoUI(Multa multa)
    {
        autuacaoNotificacao.setText(multa.getNotificacaoAutuacao());

        autuacaoDate.setText(multa.getDataAutuacao());

        if(multa.getPostagemAutuacao() != null)
        {
            autuacaoPostagemLinearLayout.setVisibility(View.VISIBLE);
            autuacaoPostagem.setText(multa.getPostagemAutuacao());
        }

        autuacaoAr.setText(multa.getNumeroARAutuacao());

        autuacaoSituacaoPostagem.setText(multa.getSituacaoARAutuacao());
    }

    private void fillRecursoUI(Multa multa)
    {
        recursoSubtitle.setText(multa.getRecurso());

        recursoProcessAndDate.setText(multa.getProcessoData());

        if (multa.getProcessoSituacao() != null)
        {
            recursoSituationLinerLayout.setVisibility(View.VISIBLE);
            recursoSituation.setText(multa.getProcessoSituacao());
        }
    }

    private void fillPenalidadeUI(Multa multa)
    {
        penalidadeNotificacao.setText(multa.getNotificacaoPenalidade());

        penalidadeDate.setText(multa.getDataPenalidade());

        if(multa.getPostagemPenalidade() != null)
        {
            penalidadePostgemLinearLayout.setVisibility(View.VISIBLE);
            penalidadeDataPostagem.setText(multa.getPostagemPenalidade());
        }

        penalidadeAr.setText(multa.getNumeroARPenalidade());

        penalidadeSituacaoPostagem.setText(multa.getSituacaoARPenalidade());
    }

    private void fillPaymentUI(Multa multa)
    {
        if(multa.getVencimento() != null)
        {
            paymentVencimentoLinearLayout.setVisibility(View.VISIBLE);
            paymentVencimento.setText(multa.getVencimento());
        }

        if(multa.getValorPago() != null)
        {
            paymentValorJaPagoLinearLayout.setVisibility(View.VISIBLE);
            paymentValorJaPago.setText(multa.getValorPago());
        }

        if ("PENALIDADE PAGA".equalsIgnoreCase(multa.getSituacaoDoPagamento()))
        {
            paymentSituacao.setText("Pagamento efetuado em " + multa.getDataDoPagamento());
        }
        else
        {
            if(multa.getValorAPagar() != null)
            {
                paymentValorAPagarLinerLayout.setVisibility(View.VISIBLE);
                paymentValorAPagar.setText(multa.getValorAPagar());
            }
            paymentSituacao.setText("Pagamento NÃO efetuado");
        }
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

                if(DetailsActivity.this.getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(DetailsActivity.this.getCurrentFocus().getWindowToken(), 0);
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
