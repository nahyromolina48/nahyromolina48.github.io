/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc365;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public Double total(LinkedHashMap<String, Integer> dict, File filer) throws FileNotFoundException {
        LinkedHashMap<String, Double> record = new LinkedHashMap<>();
        Double total = 0.0;
        File[] fileNames = filer.listFiles();
        for (File file : fileNames) {
            File maker = new File(file.getPath());
            Scanner sc = new Scanner(maker);
            while (sc.hasNext()) {
                String temp = sc.next();
                temp = temp.toLowerCase();
                if (dict.containsKey(temp) && record.get(temp) == null) {
                    record.put(temp, 1.0);
                } else if (dict.containsKey(temp) && record.get(temp) != null) {
                    Double occur = record.get(temp) + 1.0;
                    record.put(temp, occur);
                }
            }
        }
        for (Map.Entry target : record.entrySet()) {
            Double freq = (double)target.getValue();
            total = total + freq;
        }
        return total;
    }
    
    public LinkedHashMap<String, Double> vocabulary(LinkedHashMap<String, Integer> dict, File filer) throws FileNotFoundException {
        LinkedHashMap<String, Double> record = new LinkedHashMap<>();
        Double total = 0.0;
        File[] fileNames = filer.listFiles();
        for (File file : fileNames) {
            File maker = new File(file.getPath());
            Scanner sc = new Scanner(maker);
            while (sc.hasNext()) {
                String temp = sc.next();
                temp = temp.toLowerCase();
                if (dict.containsKey(temp) && record.get(temp) == null) {
                    record.put(temp, 1.0);
                } else if (dict.containsKey(temp) && record.get(temp) != null) {
                    Double occur = record.get(temp) + 1.0;
                    record.put(temp, occur);
                }
            }
        }
        return record;
    }

    public LinkedHashMap<String, Double> training(ArrayList<LinkedHashMap> data, LinkedHashMap<String, Integer> dict, Double words, Double total) {
        LinkedHashMap<String, Double> trained = new LinkedHashMap<>();
        for (LinkedHashMap<String, ArrayList> entry : data) {
            entry.entrySet().stream().forEach((Map.Entry<String, ArrayList> target) -> {
                String key = target.getKey();
                ArrayList temp = target.getValue();
                Double frequency = (Double) temp.get(0);
                Double points = ((frequency / (words + total)));
                trained.put(key, points);//happy is key points .48
            });
        }
        return trained;
    }

    public void prediction(LinkedHashMap<String, Double> pos, LinkedHashMap<String, Double> neg,Double p, Double n, LinkedHashMap<String, Integer> dict, File data) throws FileNotFoundException, IOException {
        Double positive = 0.0;
        Double negative = 0.0;
        Double count = 0.0;
        File[] fileNames = data.listFiles();
        FileWriter writer = new FileWriter("summary.txt");
        for (File file : fileNames) {
            count++;
            LinkedHashMap<String, Double> record = new LinkedHashMap<>();
            File maker = new File(file.getPath());
            Scanner sc = new Scanner(maker);
            while (sc.hasNext()) {
                String temp = sc.next();
                temp = temp.toLowerCase();
                if (dict.containsKey(temp) && record.get(temp) == null) {
                    record.put(temp, 1.0);
                } else if (dict.containsKey(temp) && record.get(temp) != null) {
                    Double occur = record.get(temp) + 1.0;
                    record.put(temp, occur);
                }
            }
            ArrayList<Double> plus = new ArrayList<>();
            ArrayList<Double> minus = new ArrayList<>();
            record.entrySet().stream().forEach((Map.Entry<String, Double> target) -> {
                String key = target.getKey();
                Double value = target.getValue();
                if (pos.containsKey(key)) {
                    Double points = pos.get(key);
                    Double score = points * value * p;
                    plus.add(score);
                }
                if (neg.containsKey(key)) {
                    Double points = neg.get(key);
                    Double score = points * value * n;
                    minus.add(score);
                }
            });
            Double p_result = sum(plus);
            Double n_result = sum(minus);
            if (p_result.compareTo(n_result) > 0) {
                positive++;
            } else if (p_result.compareTo(n_result) < 0) {
                negative++;
            } else {
                negative++;
            }
        }
        Double poss = (positive / count) * 100;
        Double negate = (negative / count) * 100;
        
        writer.write("Percentage of positive reviews: " + poss +
                "\nPercentage of negative reviews: " + negate);
        writer.write("\n-------------------------------------\n");
        writer.write("          positive  negative\n");
        writer.write("positive:  " + (count/2) + "       " + ((count*(poss/100))-(count/2)) + "\n");
        writer.write("negative:  " + ((count/2)-(count*(negate/100))) + "       " + (count/2) + "\n");
        writer.close();
        System.out.println("Percentage of positive reviews: " + poss +
                "\nPercentage of negative reviews: " + negate);
        System.out.println("\n-------------------------------------\n");
        System.out.println("          positive  negative");
        System.out.println("positive:  " + (count*(negate/100)) + "       " + ((count*(poss/100))-(count/2))*2);
        System.out.println("negative:  " + ((count/2)-(count*(negate/100))) + "       " + ((count/2)-((count*(poss/100))-(count/2))*2));
    }

    public static Double sum(ArrayList<Double> list) {
        Double sum = 0.0;
        for (Double i : list) {
            sum += i;
        }
        return sum;
    }

    public String removeSpecialChars(String s) {
        if(s.startsWith("'") || s.startsWith(".") || s.startsWith(",") || s.startsWith("\"")){
            s = s.substring(1);
        }
        if(s.endsWith("'") || s.endsWith(".") || s.endsWith(",") || s.endsWith("\"")){
            s = s.substring(0,s.length() - 1);
        }
        return s;
    }

    public ArrayList<LinkedHashMap> processor(LinkedHashMap<String, Integer> dict, File filer) throws FileNotFoundException, IOException {
        ArrayList<LinkedHashMap> documents = new ArrayList<>();
        ArrayList<LinkedHashMap> docs = new ArrayList<>();
        File[] fileNames = filer.listFiles();
        for (File file : fileNames) {
            LinkedHashMap<String, Double> record = new LinkedHashMap<>();
            LinkedHashMap<String, ArrayList> track = new LinkedHashMap<>();
            File maker = new File(file.getPath());
            Scanner sc = new Scanner(maker);
            int count;
            while (sc.hasNext()) {
                String temp = sc.next();
                temp = temp.toLowerCase();
                temp = removeSpecialChars(temp);
                if (dict.containsKey(temp) && record.get(temp) == null) {
                    record.put(temp, 1.0);
                } else if (dict.containsKey(temp) && record.get(temp) != null) {
                    Double occur = record.get(temp) + 1.0;
                    record.put(temp, occur);
                }
            }
            count = record.size();
            record.entrySet().stream().forEach((entry) -> {
                ArrayList values = new ArrayList();
                Double occurs = entry.getValue();
                String key = entry.getKey();
                double tf = (occurs / count);
                values.add(0, occurs);
                values.add(1, count);
                values.add(2, tf);
                track.put(key, values);
            });
            documents.add(track);
        }
        for (LinkedHashMap<String, ArrayList> entry : documents) {
            entry.entrySet().stream().forEach((Map.Entry<String, ArrayList> target) -> {
                int count = 0;
                String key = target.getKey();
                ArrayList temp = target.getValue();
                count = documents.stream().filter((lister) -> (lister.containsKey(key))).map((item) -> 1).reduce(count, Integer::sum);
                double idf = Math.log(documents.size() / count);
                double tfidf = (double) temp.get(2) * idf;
                temp.add(3, idf);
                temp.add(4, tfidf);
                entry.put(key, temp);
            });
            docs.add(entry);
        }
        return docs;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        File plus = new File("csctest\\positive");
        File minus = new File("csctest\\negative");
        File dataset = new File("dataset");
        File predict = new File("predict");
        File file = new File("words.txt");
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        Scanner kb = new Scanner(file);
        String line;
        while (kb.hasNextLine()) {
            line = kb.nextLine();
            String temp[] = line.split("\\s+");
            if (!temp[0].trim().matches("") && !temp[1].matches("[a-zA-Z]*$")) {
                map.put(temp[0].toLowerCase(), Integer.parseInt(temp[1]));
            }
        }
        Main WordGetter = new Main();
        Double pos = WordGetter.total(map, plus);
        Double neg = WordGetter.total(map, minus);
        Double neg_size = WordGetter.vocabulary(map, dataset).size() / 1.0;
        Double pos_size = WordGetter.vocabulary(map, dataset).size() / 1.0;
        ArrayList<LinkedHashMap> positive = WordGetter.processor(map, plus);
        ArrayList<LinkedHashMap> negative = WordGetter.processor(map, minus);
        LinkedHashMap<String, Double> pos_set = WordGetter.training(negative, map, neg, neg_size);
        LinkedHashMap<String, Double> neg_set = WordGetter.training(positive, map, pos, pos_size);
        WordGetter.prediction(pos_set, neg_set, pos_size/pos, neg_size/neg, map, predict);
    }
    

}
