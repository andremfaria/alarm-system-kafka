package org.aflabs.kafka.stream.transformer.beans;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AggInfo  {


    private double sumCpu;
    private double sumMem;
    private double avgMem;
    private double avgCpu;
    private int count;

    public AggInfo()
    {

    }

    public AggInfo(AggInfo aggInfo) {
        this.sumCpu = aggInfo.getSumCpu();
        this.sumMem = aggInfo.getSumMem();
        this.avgMem = aggInfo.getAvgMem();
        this.avgCpu = aggInfo.getAvgCpu();
        this.count = aggInfo.getCount();
    }

    public Double getSumCpu() {
        return sumCpu;
    }

    public void setSumCpu(double sumCpu) {
        this.sumCpu = sumCpu;
    }

    public void setSumCpu(Double sumCpu) {
        this.sumCpu = sumCpu;
    }

    public Double getSumMem() {
        return sumMem;
    }

    public void setSumMem(Double sumMem) {
        this.sumMem = sumMem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSumMem(double sumMem) {
        this.sumMem = sumMem;
    }

    public double getAvgMem() {
        return avgMem;
    }

    public void setAvgMem(double avgMem) {
        this.avgMem = avgMem;
    }

    public double getAvgCpu() {
        return avgCpu;
    }

    public void setAvgCpu(double avgCpu) {
        this.avgCpu = avgCpu;
    }

    public AggInfo updateNode(JsonNode newKpi)
    {
        double paramCpu = newKpi.get("cpu").asDouble();
        double paramMem =  newKpi.get("mem").asDouble();

        this.sumCpu+=roundDouble(paramCpu,2);
        this.sumMem+=roundDouble(paramMem,2);
        this.count++;

        this.avgCpu = roundDouble(this.sumCpu/this.count,2);
        this.avgMem = roundDouble(this.sumMem/this.count,2);

        return new AggInfo(this);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{ sumCpu: ");
        builder.append(this.sumCpu);
        builder.append(" , sumMem: ");
        builder.append(this.sumMem);
        builder.append(" , avgMem: ");
        builder.append(this.avgMem);
        builder.append(", avgCpu: ");
        builder.append(this.avgCpu);


        return builder.toString();
    }

    private double roundDouble(double d, int places)
    {
        BigDecimal res = BigDecimal.valueOf(d);
        res = res.setScale(places, RoundingMode.HALF_UP);
        return res.doubleValue();
    }
}
