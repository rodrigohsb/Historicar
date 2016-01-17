package com.historicar.app.activity;

import android.app.Activity;
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

    // COMMON_SECTION_XML
    @Bind(R.id.detailsSectionCommonTitle)
    protected TextView description;

    @Bind(R.id.detailsSectionCommonPoints)
    protected TextView points;

    @Bind(R.id.detailsSectionCommonGravidade)
    protected TextView gravidade;

    @Bind(R.id.detailsSectionCommonLocal)
    protected TextView local;


    @Bind(R.id.detailsSectionCommonInfracaoLinearLayout)
    protected LinearLayout infracaoLinearLayout;
    @Bind(R.id.detailsSectionCommonInfracao)
    protected TextView infracao;


    @Bind(R.id.detailsSectionCommonCodigoDetranLinearLayout)
    protected LinearLayout CodigoDetranLinearLayout;
    @Bind(R.id.detailsSectionCommonCodigoDetran)
    protected TextView codDetran;


    @Bind(R.id.detailsSectionCommonCodInfracaoLinearLayout)
    protected LinearLayout codInfracaoLinearLayout;
    @Bind(R.id.detailsSectionCommonCodInfracao)
    protected TextView codInfracao;


    @Bind(R.id.detailsSectionCommonTypeLinearLayout)
    protected LinearLayout typeLinearLayout;
    @Bind(R.id.detailsSectionCommonType)
    protected TextView type;


    @Bind(R.id.detailsSectionCommonDateHourLinearLayout)
    protected LinearLayout dateHourLinearLayout;
    @Bind(R.id.detailsSectionCommonDateHour)
    protected TextView dateHour;


    @Bind(R.id.detailsSectionCommonStatusLinearLayout)
    protected LinearLayout statusLinearLayout;
    @Bind(R.id.detailsSectionCommonStatus)
    protected TextView status;

    @Bind(R.id.detailsSectionCommonVelocidade)
    protected LinearLayout velocidadeLinearLayout;
    
    @Bind(R.id.detailsSectionCommonVelocidadeAferida)
    protected TextView velocAferica;

    @Bind(R.id.detailsSectionCommonVelocidadeMax)
    protected TextView velocMax;


    // AUTUACAO_SECTION_XML
    @Bind(R.id.detailsAutuacaoSectionNotificacaoLinearLayout)
    protected LinearLayout autuacaoNotificacaoLinearLayout;
    @Bind(R.id.detailsAutuacaoSectionNotificacao)
    protected TextView autuacaoNotificacao;


    @Bind(R.id.detailsAutuacaoSectionDateLinearLayout)
    protected LinearLayout autuacaoDateLinearLayout;
    @Bind(R.id.detailsAutuacaoSectionDate)
    protected TextView autuacaoDate;


    @Bind(R.id.detailsAutuacaoSectionPostagemLinearLayout)
    protected LinearLayout autuacaoPostagemLinearLayout;
    @Bind(R.id.detailsAutuacaoSectionPostagem)
    protected TextView autuacaoPostagem;


    @Bind(R.id.detailsAutuacaoSectionARLinearLayout)
    protected LinearLayout autuacaoArLinearLayout;
    @Bind(R.id.detailsAutuacaoSectionAR)
    protected TextView autuacaoAr;


    @Bind(R.id.detailsAutuacaoSectionSituacaoPostagemLinearLayout)
    protected LinearLayout autuacaoSituacaoPostagemLinearLayout;
    @Bind(R.id.detailsAutuacaoSectionSituacaoPostagem)
    protected TextView autuacaoSituacaoPostagem;


    
    // RECURSO_SECTION_XML
    @Bind(R.id.details_recurso_section)
    RelativeLayout recursoSection;

    @Bind(R.id.detailsRecursoSectionSubtitle)
    protected TextView recursoSubtitle;


    @Bind(R.id.detailsRecursoSectionProcessAndDateLinearLayout)
    protected LinearLayout recursoProcessAndDateLinearLayout;
    @Bind(R.id.detailsRecursoSectionProcessAndDate)
    protected TextView recursoProcessAndDate;


    @Bind(R.id.detailsRecursoSectionSituationLinearLayout)
    protected LinearLayout recursoSituationLinerLayout;
    @Bind(R.id.detailsRecursoSectionSituation)
    protected TextView recursoSituation;



    // PENALIDADE_SECTION_XML
    @Bind(R.id.details_penalidade_section)
    RelativeLayout penalidadeSection;


    @Bind(R.id.detailsSectionPenalidadeNotificacaoLinearLayout)
    protected LinearLayout penalidadeNotificacaoLinearLayout;
    @Bind(R.id.detailsSectionPenalidadeNotificacao)
    protected TextView penalidadeNotificacao;


    @Bind(R.id.detailsSectionPenalidadeDateLinearLayout)
    protected LinearLayout penalidadeDateLinearLayout;
    @Bind(R.id.detailsSectionPenalidadeDate)
    protected TextView penalidadeDate;


    @Bind(R.id.detailsSectionPenalidadePostagemLinearLayout)
    protected LinearLayout penalidadePostagemLinearLayout;
    @Bind(R.id.detailsSectionPenalidadePostagem)
    protected TextView penalidadeDataPostagem;


    @Bind(R.id.detailsSectionPenalidadeARLinearLayout)
    protected LinearLayout penalidadeArLinearLayout;
    @Bind(R.id.detailsSectionPenalidadeAR)
    protected TextView penalidadeAr;


    @Bind(R.id.detailsSectionPenalidadeSituacaoPostagemLinearLayout)
    protected LinearLayout penalidadeSituacaoPostagemLinearLayout;
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


    public static void start(Activity activity, Multa multa)
    {
        Intent intent = new Intent(activity.getApplicationContext(), DetailsActivity.class);
        intent.putExtra(Constants.MULTA, multa);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

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

        if(multa.getInfracao() != null)
        {
            infracaoLinearLayout.setVisibility(View.VISIBLE);
            infracao.setText(multa.getInfracao());
        }

        if(multa.getCodDetran() != null)
        {
            CodigoDetranLinearLayout.setVisibility(View.VISIBLE);
            codDetran.setText(multa.getCodDetran());
        }

        if(multa.getCodInfracao() != null)
        {
            codInfracaoLinearLayout.setVisibility(View.VISIBLE);
            codInfracao.setText(multa.getCodInfracao());
        }

        if(multa.getType() != null)
        {
            typeLinearLayout.setVisibility(View.VISIBLE);
            type.setText(multa.getType());
        }

        if(multa.getDataHoraInfracao() != null)
        {
            dateHourLinearLayout.setVisibility(View.VISIBLE);
            dateHour.setText(multa.getDataHoraInfracao());
        }

        if(multa.getStatus() != null)
        {
            statusLinearLayout.setVisibility(View.VISIBLE);
            status.setText(multa.getStatus());
        }

        if(multa.getVelocidadeAferida() != null && multa.getVelocidadeMax() != null)
        {
            velocidadeLinearLayout.setVisibility(View.VISIBLE);
            velocAferica.setText(multa.getVelocidadeAferida());
            velocMax.setText(multa.getVelocidadeMax());
        }
    }
    
    private void fillAutuacaoUI(Multa multa)
    {
        if(multa.getNotificacaoAutuacao() != null)
        {
            autuacaoNotificacaoLinearLayout.setVisibility(View.VISIBLE);
            autuacaoNotificacao.setText(multa.getNotificacaoAutuacao());
        }

        if(multa.getDataAutuacao() != null)
        {
            autuacaoDateLinearLayout.setVisibility(View.VISIBLE);
            autuacaoDate.setText(multa.getDataAutuacao());
        }

        if(multa.getPostagemAutuacao() != null)
        {
            autuacaoPostagemLinearLayout.setVisibility(View.VISIBLE);
            autuacaoPostagem.setText(multa.getPostagemAutuacao());
        }

        if(multa.getNumeroARAutuacao() != null)
        {
            autuacaoArLinearLayout.setVisibility(View.VISIBLE);
            autuacaoAr.setText(multa.getNumeroARAutuacao());
        }

        if(multa.getSituacaoARAutuacao() != null)
        {
            autuacaoSituacaoPostagemLinearLayout.setVisibility(View.VISIBLE);
            autuacaoSituacaoPostagem.setText(multa.getSituacaoARAutuacao());
        }
    }

    private void fillRecursoUI(Multa multa)
    {

        recursoSection.setVisibility(View.VISIBLE);

        if(multa.getRecurso() != null)
        {
            recursoSubtitle.setVisibility(View.VISIBLE);
            recursoSubtitle.setText(multa.getRecurso());
        }

        if(multa.getProcessoData() != null)
        {
            recursoProcessAndDateLinearLayout.setVisibility(View.VISIBLE);
            recursoProcessAndDate.setText(multa.getProcessoData());
        }

        if (multa.getProcessoSituacao() != null)
        {
            recursoSituationLinerLayout.setVisibility(View.VISIBLE);
            recursoSituation.setText(multa.getProcessoSituacao());
        }
    }

    private void fillPenalidadeUI(Multa multa)
    {

        penalidadeSection.setVisibility(View.VISIBLE);

        if(multa.getNotificacaoPenalidade() != null)
        {
            penalidadeNotificacaoLinearLayout.setVisibility(View.VISIBLE);
            penalidadeNotificacao.setText(multa.getNotificacaoPenalidade());
        }

        if(multa.getDataPenalidade() != null)
        {
            penalidadeDateLinearLayout.setVisibility(View.VISIBLE);
            penalidadeDate.setText(multa.getDataPenalidade());
        }

        if(multa.getPostagemPenalidade() != null)
        {
            penalidadePostagemLinearLayout.setVisibility(View.VISIBLE);
            penalidadeDataPostagem.setText(multa.getPostagemPenalidade());
        }

        if(multa.getNumeroARPenalidade() != null)
        {
            penalidadeArLinearLayout.setVisibility(View.VISIBLE);
            penalidadeAr.setText(multa.getNumeroARPenalidade());
        }

        if(multa.getSituacaoARPenalidade() != null)
        {
            penalidadeSituacaoPostagemLinearLayout.setVisibility(View.VISIBLE);
            penalidadeSituacaoPostagem.setText(multa.getSituacaoARPenalidade());
        }
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

                if (!ValidateUtils.validatePlate(s))
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

                CaptchaActivity.start(DetailsActivity.this, s);
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
    protected void onResume ()
    {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }
}
