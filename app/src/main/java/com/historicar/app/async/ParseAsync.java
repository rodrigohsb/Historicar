package com.historicar.app.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.historicar.app.R;
import com.historicar.app.activity.CaptchaActivity;
import com.historicar.app.activity.ErrorActivity;
import com.historicar.app.activity.NoMultaActivity;
import com.historicar.app.adapter.ResultAdapter;
import com.historicar.app.bean.Multa;
import com.historicar.app.connection.Connection;
import com.historicar.app.contants.Constants;
import com.historicar.app.util.EncodeUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 19/04/15.
 */
public class ParseAsync extends AsyncTask<String, String, List<Multa>>
{

    private static final String TAG = ParseAsync.class.getName();

    private final Context ctx;

    private ProgressDialog dialog;

    private final String placa;

    private final String captcha;

    private boolean isInvalidCode = false;

    private boolean hasError = false;

    public ParseAsync (Context ctx, String placa, String captcha)
    {
        this.ctx = ctx;
        this.placa = placa;
        this.captcha = captcha;
    }

    private List<Multa> getMultas ()
    {
        Document doc;
        try
        {
            doc = Connection.getContent(placa, captcha);

            if (doc != null)
            {

                Elements tables = doc.select(".Section1 .MsoNormalTable");

                if (!tables.isEmpty())
                {
                    Log.d(TAG,"A busca para a placa ["+ placa + "] e o captcha [" + captcha + "] retornou [" + tables.size() + "] multas");
                    return convertTablesToMultas(tables);
                }

                Elements elements = doc.select("div:contains(Favor refazer a consulta)");

                if(elements != null && elements.size() > 0)
                {
                    Log.d(TAG,"A busca para a placa ["+ placa + "] e o captcha [" + captcha + "] retornou captcha inválido");
                    isInvalidCode = true;
                    return null;
                }

                Log.d(TAG,"A busca para a placa ["+ placa + "] e o captcha [" + captcha + "] nao retornou nenhuma multa");
                return new ArrayList<>();
            }
        }
        catch (SocketTimeoutException stex)
        {
            Log.d(TAG,"A busca para a placa ["+ placa + "] e o captcha [" + captcha + "] retornou [" + stex.getMessage() + "]");

            hasError = true;
            stex.printStackTrace();
        }
        catch (Exception ex)
        {
            Log.d(TAG,"A busca para a placa ["+ placa + "] e o captcha [" + captcha + "] retornou [" + ex.getMessage() + "]");
            ex.printStackTrace();
        }
        return null;
    }


    /**
     *
     *
     *
     * @param tables
     * @return
     */
    private List<Multa> convertTablesToMultas (Elements tables)
    {

        List<Multa> multaList = new ArrayList<>();

        for (Element table : tables)
        {
            try
            {
                multaList.add(convert(table.select("tbody")));
            }
            catch (Exception e)
            {
                //continue
                e.printStackTrace();
            }
        }
        return multaList;
    }


    /**
     *
     *
     *
     * @param body
     * @return
     */
    private Multa convert (Elements body)
    {
        Elements item = body.select("tr").select("td");

        Multa multa = new Multa();

        String infracao = item.get(9).text().contains("---") ? null : EncodeUtils.formatText(item.get(9).text().split(" ")[item.get(9).text().split(" ").length - 1]);

        //DADOS DO VEÍCULO E INFRAÇÃO
        multa.setType(item.get(5).text().contains("---") ? null : EncodeUtils.formatText(item.get(5).text().split(" ")[item.get(5).text().split(" ").length - 1]));
        multa.setInfracao(infracao);
        multa.setCodDetran(item.get(10).text().contains("---") ? null : EncodeUtils.formatter(item.get(10).text().split(" ")[item.get(10).text().split(" ").length - 1]));
        multa.setDataHoraInfracao(item.get(13).text().contains("---") ? null : EncodeUtils.formatter(item.get(13).text()).replace("DATA - HORA ", ""));
        multa.setLocal(item.get(14).text().contains("---") ? null : EncodeUtils.formatter(item.get(14).text()).replace("LOCAL ", ""));
        multa.setCodInfracao(item.get(15).text().contains("---") ? null : EncodeUtils.formatter(item.get(15).text().split(" ")[item.get(15).text().split(" ").length - 1]));

        String descricao = item.get(16).text().contains("---") ? null : EncodeUtils.formatter(item.get(16).text()).replace("DESCRIÇÃO DA INFRAÇÃO", "");
        if(descricao != null)
        {
            descricao = EncodeUtils.replaceAll(descricao).toLowerCase().trim();
            multa.setDescricao(Character.toUpperCase(descricao.charAt(0)) + descricao.substring(1));
        }
        else
        {
            multa.setDescricao(null);
        }

        multa.setPontos(item.get(17).text().contains("---") ? null : EncodeUtils.formatter(item.get(17).text().split(" ")[item.get(17).text().split(" ").length - 1]));
        multa.setGravidade(item.get(18).text().contains("---") ? null : EncodeUtils.formatter(item.get(18).text().split(" ")[item.get(18).text().split(" ").length - 1]));

        if (!item.get(19).text().contains("---"))
        {
            String[] velocidade = EncodeUtils.formatter(item.get(19).text()).split(" ");
            multa.setVelocidadeMax(velocidade[1].concat(" ").concat(velocidade[2]));
            multa.setVelocidadeAferida(velocidade[4].concat(" ").concat(velocidade[5]));
        }

        multa.setStatus(item.get(22).text().contains("---") ? null : EncodeUtils.formatter(item.get(22).text().split("-")[item.get(22).text().split("-").length - 1]));

        //AUTUAÇÃO
        multa.setNotificacaoAutuacao(item.get(24).text().contains("---") ? null : EncodeUtils.formatter(item.get(24).text().split(" ")[item.get(24).text().split(" ").length - 1]));
        multa.setDataAutuacao(item.get(25).text().contains("---") ? null : EncodeUtils.formatter(item.get(25).text().split(" ")[item.get(25).text().split(" ").length - 1]));
        multa.setPostagemAutuacao(item.get(26).text().contains("---") ? null : EncodeUtils.formatter(item.get(26).text().split(" ")[item.get(26).text().split(" ").length - 1]));
        multa.setNumeroARAutuacao(item.get(28).text().contains("---") ? null : EncodeUtils.formatter(item.get(28).text().split(" ")[item.get(28).text().split(" ").length - 1]));
        multa.setSituacaoARAutuacao(item.get(29).text().contains("---") ? null : EncodeUtils.formatter(item.get(29).text().replace("SITUAÇÃO DO AR ", "")));

        if(item.get(30).text() != null && item.get(30).text().contains("RECURSO"))
        {
            multa.setHasRecurso(true);
            //RECURSO
            multa.setRecurso(EncodeUtils.formatter(item.get(30).text().replace("RECURSO ","")));
            multa.setProcessoData(item.get(31).text().contains("---") ? null : EncodeUtils.formatter(item.get(31).text().replace("PROCESSO (REAL INFRATOR) - DATA ", "")));
            multa.setProcessoSituacao(item.get(32).text().contains("---") ? null : EncodeUtils.formatter(item.get(32).text().replace("PROCESSO (REAL INFRATOR) - SITUAÇÃO ", "")));

            if (multa.getType().equalsIgnoreCase("PENALIDADE"))
            {

                multa.setHasPenalidade(true);
                //PENALIDADE
                multa.setNotificacaoPenalidade(item.get(34).text().contains("---") ? null : EncodeUtils.formatter(item.get(34).text().split(" ")[item.get(34).text().split(" ").length - 1]));
                multa.setDataPenalidade(item.get(35).text().contains("---") ? null : item.get(35).text().contains("---") ? null : EncodeUtils.formatter(item.get(35).text().split(" ")[item.get(35).text().split(" ").length - 1]));
                multa.setPostagemPenalidade(item.get(36).text().contains("---") ? null : EncodeUtils.formatter(item.get(36).text().split(" ")[item.get(36).text().split(" ").length - 1]));
                multa.setNumeroARPenalidade(item.get(38).text().contains("---") ? null : EncodeUtils.formatter(item.get(38).text().split(" ")[item.get(38).text().split(" ").length - 1]));
                multa.setSituacaoARPenalidade(item.get(39).text().contains("---") ? null : EncodeUtils.formatter(item.get(39).text().replace("SITUAÇÃO DA POSTAGEM ", "")));


                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(41).text().contains("---") ? null : EncodeUtils.formatter(item.get(41).text().split(" ")[item.get(41).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(42).text().contains("---") ? null : EncodeUtils.formatter(item.get(42).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(43).text().contains("---") ? null : EncodeUtils.formatter(item.get(43).text().split(" ")[item.get(43).text().split(" ").length-1]));
                multa.setValorPago(item.get(44).text().contains("---") ? null : EncodeUtils.formatter(item.get(44).text().replace("VALOR PAGO ", "")));
                multa.setSituacaoDoPagamento(item.get(45).text().contains("---") ? null : EncodeUtils.formatter(item.get(45).text().replace("SITUAÇÃO DO PAGAMENTO ", "")));
            }
            else
            {
                multa.setHasPenalidade(false);
                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(34).text().contains("---") ? null : EncodeUtils.formatter(item.get(34).text().split(" ")[item.get(34).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(35).text().contains("---") ? null : EncodeUtils.formatter(item.get(35).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(36).text().contains("---") ? null : EncodeUtils.formatter(item.get(36).text().split(" ")[item.get(36).text().split(" ").length-1]));
                multa.setValorPago(item.get(37).text().contains("---") ? null : EncodeUtils.formatter(item.get(37).text()).replace("VALOR PAGO ", ""));
                multa.setSituacaoDoPagamento(item.get(38).text().contains("---") ? null : EncodeUtils.formatter(item.get(38).text().replace("SITUAÇÃO DO PAGAMENTO ", "")));
            }
        }
        else
        {
            multa.setHasRecurso(false);

            if (multa.getType().equalsIgnoreCase("PENALIDADE"))
            {
                //PENALIDADE
                multa.setNotificacaoPenalidade(item.get(31).text().contains("---") ? null : EncodeUtils.formatter(item.get(31).text().split(" ")[item.get(31).text().split(" ").length - 1]));
                multa.setDataPenalidade(item.get(32).text().contains("---") ? null : EncodeUtils.formatter(item.get(32).text().split(" ")[item.get(32).text().split(" ").length - 1]));
                multa.setPostagemPenalidade(item.get(33).text().contains("---") ? null : EncodeUtils.formatter(item.get(33).text().split(" ")[item.get(33).text().split(" ").length - 1]));

                if(!item.get(35).text().contains("LOTE"))
                {
                    multa.setNumeroARPenalidade(item.get(35).text().contains("---") ? null : EncodeUtils.formatter(item.get(35).text().split(" ")[item.get(35).text().split(" ").length - 1]));
                }
                multa.setSituacaoARPenalidade(item.get(36).text().contains("---") ? null : EncodeUtils.formatter(item.get(36).text()).replace("SITUAÇÃO DA POSTAGEM ", ""));


                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(38).text().contains("---") ? null : EncodeUtils.formatter(item.get(38).text().split(" ")[item.get(38).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(39).text().contains("---") ? null : EncodeUtils.formatter(item.get(39).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(40).text().contains("---") ? null : EncodeUtils.formatter(item.get(40).text().split(" ")[item.get(40).text().split(" ").length - 1]));
                multa.setValorPago(item.get(41).text().contains("---") ? null : EncodeUtils.formatter(item.get(41).text().replace("VALOR PAGO ", "")));
                multa.setSituacaoDoPagamento(item.get(42).text().contains("---") ? null : EncodeUtils.formatter(item.get(42).text()).replace("SITUAÇÃO DO PAGAMENTO ",""));
            }
            else
            {
                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(31).text().contains("---") ? null : EncodeUtils.formatter(item.get(31).text().split(" ")[item.get(31).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(32).text().contains("---") ? null : EncodeUtils.formatter(item.get(32).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(33).text().contains("---") ? null : EncodeUtils.formatter(item.get(33).text().split(" ")[item.get(33).text().split(" ").length - 1]));
                multa.setValorPago(item.get(34).text().contains("---") ? null : EncodeUtils.formatter(item.get(34).text().replace("VALOR PAGO ", "")));
                multa.setSituacaoDoPagamento(item.get(35).text().contains("---") ? null : EncodeUtils.formatter(item.get(35).text()).replace("SITUAÇÃO DO PAGAMENTO ",""));
            }
        }

        return multa;
    }

    @Override
    protected void onPreExecute ()
    {
        super.onPreExecute();
        dialog = new ProgressDialog(ctx);
        dialog.setMessage("Buscando informações...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected List<Multa> doInBackground (String... params)
    {
        try
        {
            Log.d(TAG, "Buscando o conteudo o site com a placa [" + placa + "] e o captcha [" + captcha + "]");
            return getMultas();
        }
        catch (Exception e)
        {
            Log.d(TAG,"Problemas ao buscar o conteudo o site com a placa ["+ placa + "] e o captcha [" + captcha + "]. Message [" + e.getMessage() + "]");
            return null;
        }
    }

    @Override
    protected void onPostExecute (final List<Multa> multaList)
    {
        super.onPostExecute(multaList);

        dialog.dismiss();

        Class<?> cls;

        if (multaList != null)
        {
            if (!multaList.isEmpty())
            {
                drawList(multaList);
                return;
            }
            cls = NoMultaActivity.class;
        }
        else if(hasError)
        {
            cls = ErrorActivity.class;
        }
        else if(isInvalidCode)
        {
            Toast.makeText(ctx, "Código inválido!", Toast.LENGTH_SHORT).show();
            cls = CaptchaActivity.class;
        }
        else
        {
            Toast.makeText(ctx, "Problemas ao efetuar a busca. Por favor, tente novamente!", Toast.LENGTH_SHORT).show();
            cls = CaptchaActivity.class;
        }

        Intent intent = new Intent(ctx, cls);
        intent.putExtra(Constants.PLACA_KEY, placa);
        ctx.startActivity(intent);
        ((Activity) ctx).finish();
    }

    /**
     *
     * @param multaList
     */
    private void drawList(List<Multa> multaList)
    {
        RecyclerView mRecyclerView = (RecyclerView) ((Activity) ctx).findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new ResultAdapter(multaList, ctx);
        mRecyclerView.setAdapter(mAdapter);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)((Activity) ctx).findViewById(R.id.snackbarlocation);

        Snackbar snackbar;

        if(multaList.size() == 1)
        {
            snackbar = Snackbar.make(coordinatorLayout, ctx.getString(R.string.snackbar_singular, multaList.size()), Snackbar.LENGTH_LONG);
        }
        else
        {
            snackbar = Snackbar.make(coordinatorLayout, ctx.getString(R.string.snackbar_plural, multaList.size()), Snackbar.LENGTH_LONG);
        }

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ctx.getResources().getColor(android.R.color.white));

        snackbar.show();
    }
}