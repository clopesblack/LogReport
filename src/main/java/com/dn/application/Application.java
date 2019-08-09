package com.dn.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader(args[0]);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        String[] arrs = null;
        int num = 0;

        while ((line = bufferedReader.readLine()) != null) {
            arrs = line.split(" ");
            num++;

        }
        bufferedReader.close();
        fileReader.close();
    }
}