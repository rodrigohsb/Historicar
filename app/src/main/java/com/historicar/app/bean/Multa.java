package com.historicar.app.bean;

import java.io.Serializable;

/**
 * Created by Rodrigo on 15/04/15.
 */
public class Multa implements Serializable
{
    //6
    private String type;
    //7
    private String infracao;
    //8
    private String codDetran;
    //9
//    private String renainf;
    //10
//    private String nic;
    //11
    private String dataHoraInfracao;
    //12
    private String local;
    //13
    private String codInfracao;
    //14
    private String descricao;
    //15
    private String pontos;
    //16
    private String gravidade;
    //17
    private String velocidadeAferida;
    //17
    private String velocidadeMax;
    //18
//    private String equipamento;
    //19
//    private String aferidoCertificado;
    //20
    private String status;

    //21
//    private String autuacao;
    //22
    private String notificacaoAutuacao;
    //23
    private String dataAutuacao;
    //24
    private String postagemAutuacao;
    //25
//    private String publicDomRJAutuacao;
    //26
    private String numeroARAutuacao;
    //27
    private String situacaoARAutuacao;


    //28 - RECURSO (Defesa Prévia / Real Infrator)
    private String recurso;
    //29
    private String processoData;
    //30
    private String processoSituacao;


    //31:
//    private String penalidade;
    //32
    private String notificacaoPenalidade;
    //33
    private String dataPenalidade;
    //34
    private String postagemPenalidade;
    //35
//    private String publicDomRJPenalidade;
    //36
    private String numeroARPenalidade;
    //36
//    private String lote;
    //37
    private String situacaoARPenalidade;


    //38
//    private String dadosParaPagamento;
    //39
    private String vencimento;
    //40
    //TODO valorAPagar?
    private String valorAPagar;
    //41
    private String dataDoPagamento;
    //42
    private String valorPago;
    //43
    private String situacaoDoPagamento;

    private boolean hasRecurso;

    private boolean hasPenalidade;

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getInfracao ()
    {
        return infracao;
    }

    public void setInfracao (String auto)
    {
        this.infracao = auto;
    }

    public String getCodDetran ()
    {
        return codDetran;
    }

    public void setCodDetran (String codDetran)
    {
        this.codDetran = codDetran;
    }

    public String getDataHoraInfracao ()
    {
        return dataHoraInfracao;
    }

    public void setDataHoraInfracao (String dataHoraInfracao)
    {
        this.dataHoraInfracao = dataHoraInfracao;
    }

    public String getLocal ()
    {
        return local;
    }

    public void setLocal (String local)
    {
        this.local = local;
    }

    public String getCodInfracao ()
    {
        return codInfracao;
    }

    public void setCodInfracao (String codInfracao)
    {
        this.codInfracao = codInfracao;
    }

    public String getDescricao ()
    {
        return descricao;
    }

    public void setDescricao (String descricao)
    {
        this.descricao = descricao;
    }

    public String getPontos ()
    {
        return pontos;
    }

    public void setPontos (String pontos)
    {
        this.pontos = pontos;
    }

    public String getGravidade ()
    {
        return gravidade;
    }

    public void setGravidade (String gravidade)
    {
        this.gravidade = gravidade;
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

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getNotificacaoAutuacao ()
    {
        return notificacaoAutuacao;
    }

    public void setNotificacaoAutuacao (String notificacaoAutuacao)
    {
        this.notificacaoAutuacao = notificacaoAutuacao;
    }

    public String getDataAutuacao ()
    {
        return dataAutuacao;
    }

    public void setDataAutuacao (String dataAutuacao)
    {
        this.dataAutuacao = dataAutuacao;
    }

    public String getPostagemAutuacao ()
    {
        return postagemAutuacao;
    }

    public void setPostagemAutuacao (String postagemAutuacao)
    {
        this.postagemAutuacao = postagemAutuacao;
    }

    public String getNumeroARAutuacao ()
    {
        return numeroARAutuacao;
    }

    public void setNumeroARAutuacao (String numeroARAutuacao)
    {
        this.numeroARAutuacao = numeroARAutuacao;
    }

    public String getSituacaoARAutuacao ()
    {
        return situacaoARAutuacao;
    }

    public void setSituacaoARAutuacao (String situacaoARAutuacao)
    {
        this.situacaoARAutuacao = situacaoARAutuacao;
    }

    public String getRecurso ()
    {
        return recurso;
    }

    public void setRecurso (String recurso)
    {
        this.recurso = recurso;
    }

    public String getProcessoData ()
    {
        return processoData;
    }

    public void setProcessoData (String processoData)
    {
        this.processoData = processoData;
    }

    public String getProcessoSituacao ()
    {
        return processoSituacao;
    }

    public void setProcessoSituacao (String processoSituacao)
    {
        this.processoSituacao = processoSituacao;
    }

    public String getNotificacaoPenalidade ()
    {
        return notificacaoPenalidade;
    }

    public void setNotificacaoPenalidade (String notificacaoPenalidade)
    {
        this.notificacaoPenalidade = notificacaoPenalidade;
    }

    public String getDataPenalidade ()
    {
        return dataPenalidade;
    }

    public void setDataPenalidade (String dataPenalidade)
    {
        this.dataPenalidade = dataPenalidade;
    }

    public String getPostagemPenalidade ()
    {
        return postagemPenalidade;
    }

    public void setPostagemPenalidade (String postagemPenalidade)
    {
        this.postagemPenalidade = postagemPenalidade;
    }

    public String getNumeroARPenalidade ()
    {
        return numeroARPenalidade;
    }

    public void setNumeroARPenalidade (String numeroARPenalidade)
    {
        this.numeroARPenalidade = numeroARPenalidade;
    }

    public String getSituacaoARPenalidade ()
    {
        return situacaoARPenalidade;
    }

    public void setSituacaoARPenalidade (String situacaoARPenalidade)
    {
        this.situacaoARPenalidade = situacaoARPenalidade;
    }

    public String getVencimento ()
    {
        return vencimento;
    }

    public void setVencimento (String vencimento)
    {
        this.vencimento = vencimento;
    }

    public String getValorAPagar ()
    {
        return valorAPagar;
    }

    public void setValorAPagar (String valorAPagar)
    {
        this.valorAPagar = valorAPagar;
    }

    public String getDataDoPagamento ()
    {
        return dataDoPagamento;
    }

    public void setDataDoPagamento (String dataDoPagamento)
    {
        this.dataDoPagamento = dataDoPagamento;
    }

    public String getValorPago ()
    {
        return valorPago;
    }

    public void setValorPago (String valorPago)
    {
        this.valorPago = valorPago;
    }

    public String getSituacaoDoPagamento ()
    {
        return situacaoDoPagamento;
    }

    public void setSituacaoDoPagamento (String situacaoDoPagamento)
    {
        this.situacaoDoPagamento = situacaoDoPagamento;
    }

    public boolean isHasPenalidade ()
    {
        return hasPenalidade;
    }

    public void setHasPenalidade (boolean hasPenalidade)
    {
        this.hasPenalidade = hasPenalidade;
    }

    public boolean isHasRecurso ()
    {
        return hasRecurso;
    }

    public void setHasRecurso (boolean hasRecurso)
    {
        this.hasRecurso = hasRecurso;
    }

    @Override
    public String toString ()
    {
        return "Multa{" +
                "type='" + type + '\'' +
                ", infracao='" + infracao + '\'' +
                ", codDetran='" + codDetran + '\'' +
                ", dataHoraInfracao='" + dataHoraInfracao + '\'' +
                ", local='" + local + '\'' +
                ", codInfracao='" + codInfracao + '\'' +
                ", descricao='" + descricao + '\'' +
                ", pontos='" + pontos + '\'' +
                ", gravidade='" + gravidade + '\'' +
                ", velocidadeAferida='" + velocidadeAferida + '\'' +
                ", velocidadeMax='" + velocidadeMax + '\'' +
                ", status='" + status + '\'' +
                ", notificacaoAutuacao='" + notificacaoAutuacao + '\'' +
                ", dataAutuacao='" + dataAutuacao + '\'' +
                ", postagemAutuacao='" + postagemAutuacao + '\'' +
                ", numeroARAutuacao='" + numeroARAutuacao + '\'' +
                ", situacaoARAutuacao='" + situacaoARAutuacao + '\'' +
                ", recurso='" + recurso + '\'' +
                ", processoData='" + processoData + '\'' +
                ", processoSituacao='" + processoSituacao + '\'' +
                ", notificacaoPenalidade='" + notificacaoPenalidade + '\'' +
                ", dataPenalidade='" + dataPenalidade + '\'' +
                ", postagemPenalidade='" + postagemPenalidade + '\'' +
                ", numeroARPenalidade='" + numeroARPenalidade + '\'' +
                ", situacaoARPenalidade='" + situacaoARPenalidade + '\'' +
                ", vencimento='" + vencimento + '\'' +
                ", valorAPagar='" + valorAPagar + '\'' +
                ", dataDoPagamento='" + dataDoPagamento + '\'' +
                ", valorPago='" + valorPago + '\'' +
                ", situacaoDoPagamento='" + situacaoDoPagamento + '\'' +
                ", hasRecurso=" + hasRecurso +
                ", hasPenalidade=" + hasPenalidade +
                '}';
    }
}
