package com.historicar.app.bean;

import java.io.Serializable;

/**
 * Created by Rodrigo on 15/04/15.
 */
public class Multa implements Serializable
{

    private Carro carro;

    private String type;

    private String autoInfracao;
    private String dateInfracao;

    private String infracao;
    private String pontosGravidade;

    private String local;

    private String velocidadeAferida;
    private String velocidadeMax;

    private String situacaoInfracao;

    private String numeroNotificacaoAutuacao;
    private String numeroArAutuacao;

    private String dataPostagemAutuacao;
    private String situacaoArAutuacao;

    private String numeroNotificacaoPenalidade;
    private String numeroArPenalidade;

    private String dataPostagemPenalidade;
    private String situacaoArPenalidade;

    private String vencimento;

    private String valorPago;

    private String valorAPagar;

    private String dataPagamento;

    private String situacaoPagamento;

    public Carro getCarro ()
    {
        return carro;
    }

    public void setCarro (Carro carro)
    {
        this.carro = carro;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getAutoInfracao ()
    {
        return autoInfracao;
    }

    public void setAutoInfracao (String autoInfracao)
    {
        this.autoInfracao = autoInfracao;
    }

    public String getDateInfracao ()
    {
        return dateInfracao;
    }

    public void setDateInfracao (String dateInfracao)
    {
        this.dateInfracao = dateInfracao;
    }

    public String getInfracao ()
    {
        return infracao;
    }

    public void setInfracao (String infracao)
    {
        this.infracao = infracao;
    }

    public String getPontosGravidade ()
    {
        return pontosGravidade;
    }

    public void setPontosGravidade (String pontosGravidade)
    {
        this.pontosGravidade = pontosGravidade;
    }

    public String getLocal ()
    {
        return local;
    }

    public void setLocal (String local)
    {
        this.local = local;
    }

    public String getVelocidadeAferida ()
    {
        return velocidadeAferida;
    }

    public void setVelocidadeAferida (String velocidadeAferida)
    {
        this.velocidadeAferida = velocidadeAferida;
    }

    public String getVelocidadeMax ()
    {
        return velocidadeMax;
    }

    public void setVelocidadeMax (String velocidadeMax)
    {
        this.velocidadeMax = velocidadeMax;
    }

    public String getSituacaoInfracao ()
    {
        return situacaoInfracao;
    }

    public void setSituacaoInfracao (String situacaoInfracao)
    {
        this.situacaoInfracao = situacaoInfracao;
    }

    public String getNumeroNotificacaoAutuacao ()
    {
        return numeroNotificacaoAutuacao;
    }

    public void setNumeroNotificacaoAutuacao (String numeroNotificacaoAutuacao)
    {
        this.numeroNotificacaoAutuacao = numeroNotificacaoAutuacao;
    }

    public String getNumeroArAutuacao ()
    {
        return numeroArAutuacao;
    }

    public void setNumeroArAutuacao (String numeroArAutuacao)
    {
        this.numeroArAutuacao = numeroArAutuacao;
    }

    public String getDataPostagemAutuacao ()
    {
        return dataPostagemAutuacao;
    }

    public void setDataPostagemAutuacao (String dataPostagemAutuacao)
    {
        this.dataPostagemAutuacao = dataPostagemAutuacao;
    }

    public String getSituacaoArAutuacao ()
    {
        return situacaoArAutuacao;
    }

    public void setSituacaoArAutuacao (String situacaoArAutuacao)
    {
        this.situacaoArAutuacao = situacaoArAutuacao;
    }

    public String getNumeroNotificacaoPenalidade ()
    {
        return numeroNotificacaoPenalidade;
    }

    public void setNumeroNotificacaoPenalidade (String numeroNotificacaoPenalidade)
    {
        this.numeroNotificacaoPenalidade = numeroNotificacaoPenalidade;
    }

    public String getNumeroArPenalidade ()
    {
        return numeroArPenalidade;
    }

    public void setNumeroArPenalidade (String numeroArPenalidade)
    {
        this.numeroArPenalidade = numeroArPenalidade;
    }

    public String getDataPostagemPenalidade ()
    {
        return dataPostagemPenalidade;
    }

    public void setDataPostagemPenalidade (String dataPostagemPenalidade)
    {
        this.dataPostagemPenalidade = dataPostagemPenalidade;
    }

    public String getSituacaoArPenalidade ()
    {
        return situacaoArPenalidade;
    }

    public void setSituacaoArPenalidade (String situacaoArPenalidade)
    {
        this.situacaoArPenalidade = situacaoArPenalidade;
    }

    public String getVencimento ()
    {
        return vencimento;
    }

    public void setVencimento (String vencimento)
    {
        this.vencimento = vencimento;
    }

    public String getValorPago ()
    {
        return valorPago;
    }

    public void setValorPago (String valorPago)
    {
        this.valorPago = valorPago;
    }

    public String getValorAPagar ()
    {
        return valorAPagar;
    }

    public void setValorAPagar (String valorAPagar)
    {
        this.valorAPagar = valorAPagar;
    }

    public String getDataPagamento ()
    {
        return dataPagamento;
    }

    public void setDataPagamento (String dataPagamento)
    {
        this.dataPagamento = dataPagamento;
    }

    public String getSituacaoPagamento ()
    {
        return situacaoPagamento;
    }

    public void setSituacaoPagamento (String situacaoPagamento)
    {
        this.situacaoPagamento = situacaoPagamento;
    }

    @Override
    public String toString ()
    {
        return "Multa{" +
                "carro=" + carro +
                ", type='" + type + '\'' +
                ", autoInfracao='" + autoInfracao + '\'' +
                ", dateInfracao=" + dateInfracao +
                ", infracao='" + infracao + '\'' +
                ", pontosGravidade='" + pontosGravidade + '\'' +
                ", local='" + local + '\'' +
                ", velocidadeAferida='" + velocidadeAferida + '\'' +
                ", velocidadeMax='" + velocidadeMax + '\'' +
                ", situacaoInfracao='" + situacaoInfracao + '\'' +
                ", numeroNotificacaoAutuacao='" + numeroNotificacaoAutuacao + '\'' +
                ", numeroArAutuacao='" + numeroArAutuacao + '\'' +
                ", dataPostagemAutuacao=" + dataPostagemAutuacao +
                ", situacaoArAutuacao='" + situacaoArAutuacao + '\'' +
                ", numeroNotificacaoPenalidade='" + numeroNotificacaoPenalidade + '\'' +
                ", numeroArPenalidade='" + numeroArPenalidade + '\'' +
                ", dataPostagemPenalidade=" + dataPostagemPenalidade +
                ", situacaoArPenalidade='" + situacaoArPenalidade + '\'' +
                ", vencimento=" + vencimento +
                ", valorPago='" + valorPago + '\'' +
                ", dataPagamento=" + dataPagamento +
                ", situacaoPagamento='" + situacaoPagamento + '\'' +
                '}';
    }

}
