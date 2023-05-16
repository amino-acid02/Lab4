package com.mycompany.maven_lab4_maths;

import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.interval.ConfidenceInterval;

public class StatCalculations {
    private ArrayList<double[]> samples = new ArrayList<>();

    public double calcGeomMean(double[] sample) {
        return StatUtils.geometricMean(sample);
    }

    public double calcMean(double[] sample) {
        return StatUtils.mean(sample);
    }

    public double calcSD(double[] sample) {
        StandardDeviation sd = new StandardDeviation();
        return sd.evaluate(sample);
    }

    public double calcR(double[] sample) {
        return StatUtils.max(sample) - StatUtils.min(sample);
    }

    public double calcCov(double[] sample1, double[] sample2) {
            Covariance covariance = new Covariance();
            return covariance.covariance(sample1,sample2);
    }

    public int calcN(double[] sample) {
        return sample.length;
    }

    public double calcCoeffVar(double[] sample) {
        StandardDeviation sd = new StandardDeviation();
        double mean = StatUtils.mean(sample);
        return sd.evaluate(sample)/mean;
    }

    public static ConfidenceInterval calcConfInterval(double[] sample, double alpha) {
        StandardDeviation SD = new StandardDeviation();
        double mean = StatUtils.mean(sample);
        double sd = SD.evaluate(sample);
        NormalDistribution normalDistribution = new NormalDistribution();
        double quantile = normalDistribution.inverseCumulativeProbability(1.0 - alpha / 2.0);
        return new ConfidenceInterval(mean - quantile*sd/Math.sqrt(sample.length), mean + quantile*sd/Math.sqrt(sample.length), alpha);
    }

    public static double calcVar(double[] sample) {
        Variance variance = new Variance();
        return variance.evaluate(sample);
    }

    public static double calcMin(double[] sample) {
        return StatUtils.min(sample);
    }

    public static double calcMax(double[] sample) {
        return StatUtils.max(sample);
    }

    public ArrayList<Object[]> getCalculations(ArrayList<double[]> samples)
    {
        ArrayList<Object[]> results = new ArrayList<Object[]>(); //лист значений для каждой выборки
        for(int i=0; i<samples.size(); i++)
        {
            Object[] values = new Object[10]; //лист значений для одной выборки
            values[0] = (calcN(samples.get(i)));
            values[1] = (calcMax(samples.get(i)));
            values[2] = (calcMin(samples.get(i)));
            values[3] = (calcR(samples.get(i)));
            values[4] = (calcMean(samples.get(i)));
            values[5] = (calcGeomMean(samples.get(i)));
            values[6] = (calcSD(samples.get(i)));
            values[7] = (calcVar(samples.get(i)));
            values[8] = (calcCoeffVar(samples.get(i)));
            values[9] = (calcConfInterval(samples.get(i), 0.05));
            results.add(values);
        }
        return results;
    }

    public ArrayList<Object> getCovMatrix(ArrayList<double[]> samples)
    {
        ArrayList<Object> results = new ArrayList<Object>();
        results.add(calcCov(samples.get(0), samples.get(1)));
        results.add(calcCov(samples.get(0), samples.get(2)));
        results.add(calcCov(samples.get(1), samples.get(2)));
        return results;
    }
}
