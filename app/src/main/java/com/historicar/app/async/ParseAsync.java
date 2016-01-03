package com.historicar.app.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.historicar.app.R;
import com.historicar.app.activity.ErrorActivity;
import com.historicar.app.activity.NoMultaActivity;
import com.historicar.app.adapter.ResultAdapter;
import com.historicar.app.bean.Multa;
import com.historicar.app.connection.Connection;
import com.historicar.app.contants.Constants;
import com.historicar.app.util.EncodeUtils;

import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rodrigo on 19/04/15.
 */
public class ParseAsync extends AsyncTask<String, String, List<Multa>>
{

    private final Context ctx;

    private ProgressDialog dialog;

    private final String placa;

    public ParseAsync (Context ctx, String placa)
    {
        this.ctx = ctx;
        this.placa = placa;
    }

    private List<Multa> getMultas ()
    {
        Document doc = Connection.getContent(placa);

        if (doc != null)
        {
            Elements tables = doc.select(".Section1 .MsoNormalTable");

            if (!tables.isEmpty())
            {
                return convertTablesToMultas(tables);
            }
            return new ArrayList<>();
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

        //DADOS DO VEÍCULO E INFRAÇÃO
        multa.setType(item.get(3).text().contains("---") ? null : EncodeUtils.formatText(item.get(3).text().split(" ")[item.get(3).text().split(" ").length-1]));
        multa.setInfracao(item.get(7).text().contains("---") ? null : EncodeUtils.formatText(item.get(7).text().split(" ")[item.get(7).text().split(" ").length - 1]));
        multa.setCodDetran(item.get(8).text().contains("---") ? null : EncodeUtils.formatter(item.get(8).text().split(" ")[item.get(8).text().split(" ").length - 1]));
        multa.setDataHoraInfracao(item.get(11).text().contains("---") ? null : EncodeUtils.formatter(item.get(11).text()).replace("DATA - HORA ", ""));
        multa.setLocal(item.get(12).text().contains("---") ? null : EncodeUtils.formatter(item.get(12).text()).replace("LOCAL ", ""));
        multa.setCodInfracao(item.get(13).text().contains("---") ? null : EncodeUtils.formatter(item.get(13).text().split(" ")[item.get(13).text().split(" ").length - 1]));

        String descricao = item.get(14).text().contains("---") ? null : EncodeUtils.formatter(item.get(14).text()).replace("DESCRIÇÃO DA INFRAÇÃO", "");
        multa.setDescricao(WordUtils.capitalizeFully(descricao, new char[]{'.'}));

        multa.setPontos(item.get(15).text().contains("---") ? null : EncodeUtils.formatter(item.get(15).text().split(" ")[item.get(15).text().split(" ").length - 1]));
        multa.setGravidade(item.get(16).text().contains("---") ? null : EncodeUtils.formatter(item.get(16).text().split(" ")[item.get(16).text().split(" ").length - 1]));

        if (item.get(17).text().contains("km/h"))
        {
            String[] velocidade = EncodeUtils.formatter(item.get(17).text()).split(" ");
            multa.setVelocidadeMax(velocidade[1].concat(" ").concat(velocidade[2]));
            multa.setVelocidadeAferida(velocidade[4].concat(" ").concat(velocidade[5]));
        }

        multa.setStatus(item.get(20).text().contains("---") ? null : EncodeUtils.formatter(item.get(20).text().split("-")[item.get(20).text().split("-").length - 1]));

        //AUTUAÇÃO
        multa.setNotificacaoAutuacao(item.get(22).text().contains("---") ? null : EncodeUtils.formatter(item.get(22).text().split(" ")[item.get(22).text().split(" ").length - 1]));
        multa.setDataAutuacao(item.get(23).text().contains("---") ? null : EncodeUtils.formatter(item.get(23).text().split(" ")[item.get(23).text().split(" ").length - 1]));
        multa.setPostagemAutuacao(item.get(24).text().contains("---") ? null : EncodeUtils.formatter(item.get(24).text().split(" ")[item.get(24).text().split(" ").length - 1]));
        multa.setNumeroARAutuacao(item.get(26).text().contains("---") ? null : EncodeUtils.formatter(item.get(26).text().split(" ")[item.get(26).text().split(" ").length - 1]));
        multa.setSituacaoARAutuacao(item.get(27).text().contains("---") ? null : EncodeUtils.formatter(item.get(27).text().replace("SITUAÇÃO DO AR ","")));

        if(item.get(28).text() != null && item.get(28).text().contains("RECURSO"))
        {
            multa.setHasRecurso(true);
            //RECURSO
            multa.setRecurso(EncodeUtils.formatter(item.get(28).text().replace("RECURSO ","")));
            multa.setProcessoData(item.get(29).text().contains("---") ? null : EncodeUtils.formatter(item.get(29).text().replace("PROCESSO (REAL INFRATOR) - DATA ", "")));
            multa.setProcessoSituacao(item.get(30).text().contains("---") ? null : EncodeUtils.formatter(item.get(30).text().replace("PROCESSO (REAL INFRATOR) - SITUAÇÃO ", "")));

            if (multa.getType().equalsIgnoreCase("PENALIDADE"))
            {

                multa.setHasPenalidade(true);
                //PENALIDADE
                multa.setNotificacaoPenalidade(item.get(32).text().contains("---") ? null : EncodeUtils.formatter(item.get(32).text().split(" ")[item.get(32).text().split(" ").length - 1]));
                multa.setDataPenalidade(item.get(33).text().contains("---") ? null : item.get(33).text().contains("---") ? null : EncodeUtils.formatter(item.get(33).text().split(" ")[item.get(33).text().split(" ").length - 1]));
                multa.setPostagemPenalidade(item.get(34).text().contains("---") ? null : EncodeUtils.formatter(item.get(34).text().split(" ")[item.get(34).text().split(" ").length - 1]));
                multa.setNumeroARPenalidade(item.get(36).text().contains("---") ? null : EncodeUtils.formatter(item.get(36).text().split(" ")[item.get(36).text().split(" ").length - 1]));
                multa.setSituacaoARPenalidade(item.get(37).text().contains("---") ? null : EncodeUtils.formatter(item.get(37).text().replace("SITUAÇÃO DA POSTAGEM ", "")));


                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(39).text().contains("---") ? null : EncodeUtils.formatter(item.get(39).text().split(" ")[item.get(39).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(40).text().contains("---") ? null : EncodeUtils.formatter(item.get(40).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(41).text().contains("---") ? null : EncodeUtils.formatter(item.get(41).text().split(" ")[item.get(41).text().split(" ").length-1]));
                multa.setValorPago(item.get(42).text().contains("---") ? null : EncodeUtils.formatter(item.get(42).text().replace("VALOR PAGO ", "")));
                multa.setSituacaoDoPagamento(item.get(43).text().contains("---") ? null : EncodeUtils.formatter(item.get(43).text().replace("SITUAÇÃO DO PAGAMENTO ", "")));
            }
            else
            {
                multa.setHasPenalidade(false);
                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(32).text().contains("---") ? null : EncodeUtils.formatter(item.get(32).text().split(" ")[item.get(32).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(33).text().contains("---") ? null : EncodeUtils.formatter(item.get(33).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(34).text().contains("---") ? null : EncodeUtils.formatter(item.get(34).text().split(" ")[item.get(34).text().split(" ").length-1]));
                multa.setValorPago(item.get(35).text().contains("---") ? null : EncodeUtils.formatter(item.get(35).text()).replace("VALOR PAGO ", ""));
                multa.setSituacaoDoPagamento(item.get(36).text().contains("---") ? null : EncodeUtils.formatter(item.get(36).text().replace("SITUAÇÃO DO PAGAMENTO ", "")));
            }
        }
        else
        {
            multa.setHasRecurso(false);

            if (multa.getType().equalsIgnoreCase("PENALIDADE"))
            {
                //PENALIDADE
                multa.setNotificacaoPenalidade(item.get(29).text().contains("---") ? null : EncodeUtils.formatter(item.get(29).text().split(" ")[item.get(29).text().split(" ").length - 1]));
                multa.setDataPenalidade(item.get(30).text().contains("---") ? null : EncodeUtils.formatter(item.get(30).text().split(" ")[item.get(30).text().split(" ").length - 1]));
                multa.setPostagemPenalidade(item.get(31).text().contains("---") ? null : EncodeUtils.formatter(item.get(31).text().split(" ")[item.get(31).text().split(" ").length - 1]));

                if(!item.get(33).text().contains("LOTE"))
                {
                    multa.setNumeroARPenalidade(item.get(33).text().contains("---") ? null : EncodeUtils.formatter(item.get(33).text().split(" ")[item.get(33).text().split(" ").length - 1]));
                }
                multa.setSituacaoARPenalidade(item.get(34).text().contains("---") ? null : EncodeUtils.formatter(item.get(34).text()).replace("SITUAÇÃO DA POSTAGEM ", ""));


                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(36).text().contains("---") ? null : EncodeUtils.formatter(item.get(36).text().split(" ")[item.get(36).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(37).text().contains("---") ? null : EncodeUtils.formatter(item.get(37).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(38).text().contains("---") ? null : EncodeUtils.formatter(item.get(38).text().split(" ")[item.get(38).text().split(" ").length - 1]));
                multa.setValorPago(item.get(39).text().contains("---") ? null : EncodeUtils.formatter(item.get(39).text().replace("VALOR PAGO ", "")));
                multa.setSituacaoDoPagamento(item.get(40).text().contains("---") ? null : EncodeUtils.formatter(item.get(40).text()).replace("SITUAÇÃO DO PAGAMENTO ",""));
            }
            else
            {
                //DADOS PARA PAGAMENTO
                multa.setVencimento(item.get(29).text().contains("---") ? null : EncodeUtils.formatter(item.get(29).text().split(" ")[item.get(29).text().split(" ").length - 1]));
                multa.setValorAPagar(item.get(30).text().contains("---") ? null : EncodeUtils.formatter(item.get(30).text().replace("VALOR ", "")));
                multa.setDataDoPagamento(item.get(31).text().contains("---") ? null : EncodeUtils.formatter(item.get(31).text().split(" ")[item.get(31).text().split(" ").length - 1]));
                multa.setValorPago(item.get(32).text().contains("---") ? null : EncodeUtils.formatter(item.get(32).text().replace("VALOR PAGO ", "")));
                multa.setSituacaoDoPagamento(item.get(33).text().contains("---") ? null : EncodeUtils.formatter(item.get(33).text()).replace("SITUAÇÃO DO PAGAMENTO ",""));
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
        List<Multa> multaList;

        try
        {
            multaList = getMultas();

            if(multaList == null)
            {
                multaList = recoverFromCache();
            }
            else
            {
                saveInCache(multaList);
            }
        }
        catch (Exception e)
        {
           multaList = recoverFromCache();
        }

        return multaList;
    }

    @Override
    protected void onPostExecute (final List<Multa> multaList)
    {
        super.onPostExecute(multaList);

        if (multaList != null)
        {
            if (!multaList.isEmpty())
            {
                RecyclerView mRecyclerView = (RecyclerView) ((Activity) ctx).findViewById(R.id.recyclerView);

                mRecyclerView.setHasFixedSize(true);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
                mRecyclerView.setLayoutManager(mLayoutManager);

                RecyclerView.Adapter mAdapter = new ResultAdapter(multaList, ctx);
                mRecyclerView.setAdapter(mAdapter);

            }
            else
            {
                Intent intent = new Intent(ctx, NoMultaActivity.class);
                intent.putExtra(Constants.PLACA_KEY, placa);
                ctx.startActivity(intent);
                ((Activity) ctx).finish();
            }
        }
        else
        {
            Intent intent = new Intent(ctx, ErrorActivity.class);
            intent.putExtra(Constants.PLACA_KEY, placa);
            ctx.startActivity(intent);
            ((Activity) ctx).finish();
        }

        dialog.dismiss();
    }

    private List<Multa> recoverFromCache()
    {
        String json = PreferenceManager.getDefaultSharedPreferences(ctx).getString(placa, null);
        if(json != null)
        {
            return Arrays.asList(new Gson().fromJson(json, Multa[].class));
        }
        return new ArrayList<>();
    }

    private void saveInCache(List<Multa> multaList)
    {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(placa,new Gson().toJson(multaList)).commit();
    }
}