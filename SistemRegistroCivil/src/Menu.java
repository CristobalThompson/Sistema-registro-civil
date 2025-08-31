/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author franc
 */
import java.io.*;
public class Menu {
    
    void consolaP()throws IOException{
        String nume;
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        do{
            mostrarMenuPrincipal();
            nume = lector.readLine();
            switch(nume){
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    consolaT();
                    break;
                case "4":
                    consolaC();
                    break;
                case "5":
                    break;
                case "6":
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }while(!"6".equals(nume));
    }
    
    void consolaT()throws IOException{
        String nume;
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        do{
            mostrarTramites();
            nume = lector.readLine();
            switch(nume){
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }while(!"5".equals(nume));
    }
    
    void consolaC()throws IOException{
        String nume;
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        do{
            mostrarCertificados();
            nume = lector.readLine();
            switch(nume){
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }while(!"4".equals(nume));
    }
    
    void mostrarMenuPrincipal(){
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("         Menú Principal");
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("Seleccione la opción que desee");
        System.out.println("1) Cargar datos csv. (solo una vez)");
        System.out.println("2) Mostrar sucursales.");
        System.out.println("3) Trámites.");
        System.out.println("4) Obtener certificado.");
        System.out.println("5) Actualizar datos del registro.");
        System.out.println("6) Salir");
        
    }
    void mostrarTramites(){
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("       Menú de Trámites");
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("Seleccione el tipo de trámite que desea realizar");
        System.out.println("1) Nacimiento.");
        System.out.println("2) Defunción");
        System.out.println("3) Matrimonio");
        System.out.println("4) Divorcio");
        System.out.println("5) Atrás.");
    }
    
    void mostrarCertificados(){
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("    Menú de Certificados");
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("Seleccione el certificado que desee.");
        System.out.println("1) Certificado de Matriminio");
        System.out.println("2) Certificado de Nacimiento");
        System.out.println("3) Certificado de Defución");
        System.out.println("4) Volver.");
    }
}
