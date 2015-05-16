package com.historicar.app.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.historicar.app.R;
import com.historicar.app.activity.DetailsActivity;
import com.historicar.app.activity.ErrorActivity;
import com.historicar.app.activity.NoMultaActivity;
import com.historicar.app.adapter.ListAdapter;
import com.historicar.app.bean.Carro;
import com.historicar.app.bean.Multa;
import com.historicar.app.connection.Connection;
import com.historicar.app.contants.Constants;
import com.historicar.app.util.EncodeUtils;

import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 19/04/15.
 */
public class ParseAsync extends AsyncTask<String, String, List<Multa>>
{

    private final Context ctx;

    private ProgressDialog dialog;

    private ListView list;
    private ListAdapter adapter;

    private final String placa;

    public ParseAsync (Context ctx, ListView list, ListAdapter adapter, String placa)
    {
        this.ctx = ctx;
        this.list = list;
        this.adapter = adapter;
        this.placa = placa;
    }

    private List<Multa> getMultas (String placa)
    {

        long starTime = System.currentTimeMillis();

        List<Multa> multas = new ArrayList<>();

        try
        {

            Document doc = Connection.getContent(placa);

            if (doc != null)
            {
                Elements allTables = doc.select("form table[border=1]");

                if (!allTables.isEmpty())
                    multas = convertTablesToMultas(allTables);
            }
            else
            {
                multas = null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("getMultas [" + (System.currentTimeMillis() - starTime) + "]ms");
        return multas;
    }

    private List<Multa> convertTablesToMultas (Elements elements)
    {

        long starTime = System.currentTimeMillis();

        List<Multa> multaList = new ArrayList<>();

        for (Element element : elements)
        {
            Elements tBodys = element.select("tbody");

            for (Element tBody : tBodys)
            {
                try
                {
                    multaList.add(convert(tBody));

                } catch (Exception e)
                {
                }
            }
        }
        System.out.println("convertTablesToMultas [" + (System.currentTimeMillis() - starTime) + "]ms");
        return multaList;
    }

    private Multa convert (Element tBody) throws ParseException
    {

        Elements trs = tBody.select("tr");

        Multa multa = new Multa();

        String placa = EncodeUtils.formatText(trs.select("td").get(1).text().replace("-", ""));
        String modelo = EncodeUtils.formatText(trs.select("td").get(3).text());
        Carro carro = new Carro(placa, modelo);
        multa.setCarro(carro);

        String type = EncodeUtils.formatText(trs.select("td").get(5).text());
        multa.setType(type);


        multa.setAutoInfracao(EncodeUtils.formatText(trs.select("td").get(9).text()));


        String dataInfracao = EncodeUtils.formatter(trs.select("td").get(11).text());
        if (dataInfracao != "")
            multa.setDateInfracao(dataInfracao);


        String infracao = EncodeUtils.formatText(trs.select("td").get(13).text().split("-")[trs.select("td").get(13).text().split("-").length - 1]);
        infracao = infracao.replaceAll("MAXIMA", "MÁXIMA").replaceAll("ELETRONICA", "ELETRÔNICA").replace("OBSERVANCIA", "OBSERVÂNCIA").replace("SEGURANCA", "SEGURANÇA").replaceAll("HORARIO", "HORÁRIO").replaceAll("CRIANCA", "CRIANÇA").replaceAll(" ATE ", " ATÉ ").replaceAll("CAO", "ÇÃO");
        multa.setInfracao(WordUtils.capitalizeFully(infracao, new char[]{'.'}));


        multa.setPontosGravidade(EncodeUtils.formatText(trs.select("td").get(15).text()));


        multa.setLocal(EncodeUtils.formatText(trs.select("td").get(17).text()));


        String velocidade = EncodeUtils.formatText(trs.select("td").get(19).text());

        if (!"000/000".equalsIgnoreCase(velocidade))
        {
            String velocidadeMaxima = velocidade.split("/")[0];
            String velocidadeAferida = velocidade.split("/")[1];

            multa.setVelocidadeMax(velocidadeMaxima.startsWith("0") ? velocidadeMaxima.substring(1, 3) : velocidadeMaxima);
            multa.setVelocidadeAferida(velocidadeAferida.startsWith("0") ? velocidadeAferida.substring(1, 3) : velocidadeAferida);
        }


        multa.setSituacaoInfracao(EncodeUtils.formatText(trs.select("td").get(21).text().split("-")[trs.select("td").get(21).text().split("-").length - 1]));


        multa.setNumeroNotificacaoAutuacao(EncodeUtils.formatText(trs.select("td").get(25).text()));


        multa.setNumeroArAutuacao(EncodeUtils.formatText(trs.select("td").get(27).text()));


        String postagemAutuacao = EncodeUtils.formatText(trs.select("td").get(29).text());
        if (postagemAutuacao != "")
            multa.setDataPostagemAutuacao(postagemAutuacao);


        multa.setSituacaoArAutuacao(EncodeUtils.formatText(trs.select("td").get(31).text()));


        if ("AUTUACAO".equalsIgnoreCase(type))
            multa = convertAutuacao(tBody, multa);
        else
            multa = convertPenalidade(tBody, multa);

        return multa;
    }

    private Multa convertAutuacao (Element tBody, Multa multa)
    {
        Elements trs = tBody.select("tr");


        String valorAPagarOriginal = EncodeUtils.formatText(trs.select("td").get(37).text().replaceAll("R$ ", ""));
        multa.setValorAPagar(valorAPagarOriginal.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", " "));


        String dataVencimento = EncodeUtils.formatText(trs.select("td").get(39).text());
        if (dataVencimento != "")
        {
            if (!dataVencimento.contains("/"))
            {
                String vencimento = dataVencimento.substring(6, 8).concat("/").concat(dataVencimento.substring(4, 6).concat("/").concat(dataVencimento.substring(0, 4)));
                multa.setVencimento(vencimento);
            }
            else
            {
                String vencimento = dataVencimento.substring(6, 8).concat("/").concat(dataVencimento.substring(4, 6).concat("/").concat(dataVencimento.substring(0, 4)));
                multa.setVencimento(dataVencimento);
            }
        }


        String valorPagoOriginal = EncodeUtils.formatText(trs.select("td").get(41).text().replaceAll("R$", ""));
        String valorPago = null;
        if (valorPagoOriginal != "" || !"00000000,00".equalsIgnoreCase(valorPagoOriginal))
        {

            for (int i = 0 ; i < valorPagoOriginal.length() ; i++)
            {
                if (valorPagoOriginal.charAt(i) != '0' && valorPagoOriginal.charAt(i) != ',')
                {
                    valorPago = valorPagoOriginal.substring(i, valorPagoOriginal.length());
                    break;
                }
            }

            multa.setValorPago(valorPago);
        }


        String dataPagamento = EncodeUtils.formatText(trs.select("td").get(43).text());
        if (dataPagamento != "" && !"0".equalsIgnoreCase(dataPagamento))
        {
            String pagamento = dataPagamento.substring(6, 8).concat("/").concat(dataPagamento.substring(4, 6).concat("/").concat(dataPagamento.substring(0, 4)));
            multa.setDataPagamento(EncodeUtils.formatText(trs.select("td").get(43).text()));
        }


        String situacaoDoPagamento = EncodeUtils.formatText(trs.select("td").get(45).text());
        multa.setSituacaoPagamento("".equals(situacaoDoPagamento) ? null : situacaoDoPagamento);

        return multa;
    }

    private Multa convertPenalidade (Element tBody, Multa multa)
    {
        Elements trs = tBody.select("tr");


        multa.setNumeroNotificacaoPenalidade(EncodeUtils.formatText(trs.select("td").get(33).text()));


        multa.setNumeroArPenalidade(EncodeUtils.formatText(trs.select("td").get(35).text()));


        String postagemPenalidade = EncodeUtils.formatText(trs.select("td").get(37).text());
        if (postagemPenalidade != "")
            multa.setDataPostagemPenalidade(postagemPenalidade);


        multa.setSituacaoArPenalidade(EncodeUtils.formatText(trs.select("td").get(39).text()));


        String dataVencimento = EncodeUtils.formatText(trs.select("td").get(49).text());
        if (dataVencimento != "")
        {
            if (!dataVencimento.contains("/"))
            {
                String vencimento = dataVencimento.substring(6, 8).concat("/").concat(dataVencimento.substring(4, 6).concat("/").concat(dataVencimento.substring(0, 4)));
                multa.setVencimento(vencimento);
            }
            else
            {
                multa.setVencimento(dataVencimento);
            }
        }


        String valorPagoOriginal = EncodeUtils.formatText(trs.select("td").get(51).text().replaceAll("R$", ""));
        String valorPago = null;
        if (valorPagoOriginal != "" || !"00000000,00".equalsIgnoreCase(valorPagoOriginal))
        {
            for (int i = 0 ; i < valorPagoOriginal.length() ; i++)
            {
                if (valorPagoOriginal.charAt(i) != '0' && valorPagoOriginal.charAt(i) != ',')
                {
                    valorPago = valorPagoOriginal.substring(i, valorPagoOriginal.length());
                    break;
                }
            }

            multa.setValorPago(valorPago);
        }


        String dataPagamento = EncodeUtils.formatText(trs.select("td").get(53).text());
        if (dataPagamento != "" && !"0".equalsIgnoreCase(dataPagamento))
        {
            String pagamento = dataPagamento.substring(6, 8).concat("/").concat(dataPagamento.substring(4, 6).concat("/").concat(dataPagamento.substring(0, 4)));
            multa.setDataPagamento(pagamento);
        }


        String situacaoDoPagamento = EncodeUtils.formatText(trs.select("td").get(55).text());
        multa.setSituacaoPagamento("".equals(situacaoDoPagamento) ? null : situacaoDoPagamento);

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
        List<Multa> multaList = new ArrayList<>();

        try
        {
            multaList = getMultas(placa);

        } catch (Exception e)
        {
            System.out.println(e.toString());
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
                adapter = new ListAdapter(ctx, multaList);

                list = (ListView) ((Activity) ctx).findViewById(R.id.list);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id)
                    {

                        Multa multa = multaList.get(position);

                        Intent intent = new Intent(ctx, DetailsActivity.class);
                        intent.putExtra(Constants.MULTA, multa);
                        ctx.startActivity(intent);
                    }
                });
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
}