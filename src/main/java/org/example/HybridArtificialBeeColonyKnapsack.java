package org.example;

import org.example.model.Objeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.service.FileReader.getObjetos;
import static org.example.util.Values.LIMITE_MOCHILA;
import static org.example.util.Values.QUANTIDADE_ITENS;

public class HybridArtificialBeeColonyKnapsack {
    private int colonySize;
    private int maxIterations;
    private int maxTrials;
    private double abandonProbability;
    private int numItems;
    private int[] weights;
    private int[] values;
    private int maxWeight;
    private double deMutationFactor;
    private double deCrossoverRate;
    private Random random;
    private List<Bee> colony;

    public static int MAX = 0;

    public HybridArtificialBeeColonyKnapsack(int colonySize, int maxIterations, int maxTrials, double abandonProbability,
                                             int numItems, int[] weights, int[] values, int maxWeight,
                                             double deMutationFactor, double deCrossoverRate) {
        this.colonySize = colonySize;
        this.maxIterations = maxIterations;
        this.maxTrials = maxTrials;
        this.abandonProbability = abandonProbability;
        this.numItems = numItems;
        this.weights = weights;
        this.values = values;
        this.maxWeight = maxWeight;
        this.deMutationFactor = deMutationFactor;
        this.deCrossoverRate = deCrossoverRate;
        this.random = new Random();
        this.colony = new ArrayList<>();
    }

    public int[] optimize() {
        initializeColony();
        Bee bestBee = getBestBee();

        for (int i = 0; i < maxIterations; i++) {
            for (Bee bee : colony) {
                if (bee.getTrials() >= maxTrials) {
                    if (random.nextDouble() < abandonProbability) {
                        abandonBee(bee);
                    } else {
                        exploreBee(bee);
                    }
                } else {
                    exploreBee(bee);
                }
            }

            Bee currentBestBee = getBestBee();
            if (currentBestBee.getFitness() > bestBee.getFitness()) {
                bestBee = currentBestBee;
            }
        }

        return bestBee.getSolution();
    }

    private void initializeColony() {
        for (int i = 0; i < colonySize; i++) {
            int[] solution = generateRandomSolution();
            double fitness = evaluateSolution(solution);
            Bee bee = new Bee(solution, fitness);
            colony.add(bee);
        }
    }

    private int[] generateRandomSolution() {
        int[] solution = new int[numItems];
        for (int i = 0; i < numItems; i++) {
            solution[i] = random.nextInt(2);
        }
        return solution;
    }

    private double evaluateSolution(int[] solution) {
        int totalWeight = 0;
        int totalValue = 0;

        for (int i = 0; i < numItems; i++) {
            if (solution[i] == 1) {
                totalWeight += weights[i];
                totalValue += values[i];
            }
        }

        if (totalWeight > maxWeight) {
            totalValue = 0; // Penalização se exceder o peso máximo
        }
        System.out.println("TOTAL VALUE: " + totalValue);
        if(totalValue>MAX){
            MAX = totalValue;
        }
        return totalValue;
    }

    private void exploreBee(Bee bee) {
        int[] newSolution = generateDESolution(bee.getSolution());
        double newFitness = evaluateSolution(newSolution);

        if (newFitness > bee.getFitness()) {
            bee.updateSolution(newSolution, newFitness);
        } else {
            bee.incrementTrials();
        }
    }

    private int[] generateDESolution(int[] currentSolution) {
        int[] randomBeeSolution1 = getRandomBeeSolution();
        int[] randomBeeSolution2 = getRandomBeeSolution();
        int[] randomBeeSolution3 = getRandomBeeSolution();

        int[] newSolution = new int[numItems];
        for (int i = 0; i < numItems; i++) {
            if (random.nextDouble() < deCrossoverRate) {
                newSolution[i] = (int) (randomBeeSolution1[i] + (deMutationFactor * (randomBeeSolution2[i] - randomBeeSolution3[i])));
                newSolution[i] = Math.max(0, Math.min(1, newSolution[i])); // Limitar entre 0 e 1
            } else {
                newSolution[i] = currentSolution[i];
            }
        }
        return newSolution;
    }

    private int[] getRandomBeeSolution() {
        int randomBeeIndex = random.nextInt(colonySize);
        return colony.get(randomBeeIndex).getSolution();
    }

    private void abandonBee(Bee bee) {
        int[] randomSolution = generateRandomSolution();
        double randomFitness = evaluateSolution(randomSolution);
        bee.updateSolution(randomSolution, randomFitness);
        bee.resetTrials();
    }

    private Bee getBestBee() {
        Bee bestBee = colony.get(0);
        for (Bee bee : colony) {
            if (bee.getFitness() > bestBee.getFitness()) {
                bestBee = bee;
            }
        }
        return bestBee;
    }

    private class Bee {
        private int[] solution;
        private double fitness;
        private int trials;

        public Bee(int[] solution, double fitness) {
            this.solution = solution;
            this.fitness = fitness;
            this.trials = 0;
        }

        public int[] getSolution() {
            return solution;
        }

        public double getFitness() {
            return fitness;
        }

        public int getTrials() {
            return trials;
        }

        public void updateSolution(int[] newSolution, double newFitness) {
            this.solution = newSolution;
            this.fitness = newFitness;
            this.trials = 0;
        }

        public void incrementTrials() {
            this.trials++;
        }

        public void resetTrials() {
            this.trials = 0;
        }
    }

    public static void main(String[] args) {
        int colonySize = 50;
        int maxIterations = 100;
        int maxTrials = 10;
        double abandonProbability = 0.3; //0.3
        double deMutationFactor = 0.5; //0.5
        double deCrossoverRate = 0.7; //0.7

//        int numItems = 10;
//        int[] weights = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
//        int[] values = {10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
//        int maxWeight = 30;

        int numItems = QUANTIDADE_ITENS;
        int maxWeight = LIMITE_MOCHILA.intValue();
        int[] weights = new int[QUANTIDADE_ITENS];
        int[] values = new int[QUANTIDADE_ITENS];

        /***/ long init = System.currentTimeMillis();
        List<Objeto> objetos = getObjetos();
        for (int i = 0; i < objetos.size(); i++) {
            weights[i] = objetos.get(i).getPeso().intValue();
            values[i] = objetos.get(i).getValorTotal().intValue();
        }

        HybridArtificialBeeColonyKnapsack abcKnapsack = new HybridArtificialBeeColonyKnapsack(colonySize,
                maxIterations, maxTrials, abandonProbability, numItems, weights, values, maxWeight,
                deMutationFactor, deCrossoverRate);

        int[] bestSolution = abcKnapsack.optimize();
        /***/ long finish = System.currentTimeMillis();

        System.out.println("Best Solution:");
        int somaValues = 0;
        int somaPesos = 0;

        for (int i = 0; i < numItems; i++) {
            if (bestSolution[i] == 1) {
                System.out.println("Item " + i + ": Weight = " + weights[i] + ", Value = " + values[i]);
                somaValues += values[i];
                somaPesos += weights[i];
            }
        }
        System.out.println("Soma Total obtida \n--> Pesos: " + somaPesos + "\n --> Valores: " + somaValues);
        System.out.println("Tempo de processamento: " + (finish - init) + " milissegundos");

    }
}
