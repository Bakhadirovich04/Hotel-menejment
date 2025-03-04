package org.example;

import org.example.db.Datasource;
import org.example.service.AuthPackage.AuthService;

public class Main {
    public static void main(String[] args) {
        while (true){
            Datasource.refreshScanner();
            try{
                AuthService.service();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}