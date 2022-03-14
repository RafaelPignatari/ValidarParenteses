/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package br.edu.cefsa.desafioparenteses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author rafae
 */
public class Principal {

    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) {
        if (args.length == 0)
            System.out.println("Por favor, adicione um argumento!");
        else {            
            String[] arquivo = LerArquivo(args[0]);
            var dados = new HashMap<String, Boolean>();
            
            dados = AlimentaHash(arquivo);
            EscreverArquivo(dados);
        }
    }
    
    public static String[] LerArquivo(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))){
            String line = br.readLine();
            List<String> lista = new LinkedList();

            while (line != null) {
                String aux = line.trim();
                lista.add(VasculhaString(aux));
                line = br.readLine();
            }
            
            String[] array = new String[lista.size()];
            return lista.toArray(array);
        } catch (Exception erro) {            
            System.out.println("Erro: " +erro.toString());
            return null;
        }
    }
    
    public static void EscreverArquivo(HashMap<String, Boolean> dados) {
        try (FileWriter myWriter = new FileWriter("saida.txt")) {
            for (HashMap.Entry<String, Boolean> mapa : dados.entrySet()) {
                myWriter.write(mapa.getKey() +" - " +(mapa.getValue() ? "Ok" : "Inválido") +"\n\n");
            }
        } catch (Exception erro) {                  
            System.out.println("Erro: " +erro.toString());
        }
    }
    
    public static boolean ChecaParenteses(Character[] parenteses){
        Stack pilha = new Stack();
        
        for (Character aux : parenteses) {
            if (aux == '(' || aux == '[' || aux == '{') {
                pilha.add(aux);
                continue;
            }
            if (!pilha.empty() &&
                ((aux == ')' && (Character)pilha.peek() == '(') ||
                (aux == ']' && (Character)pilha.peek() == '[') ||
                (aux == '}' && (Character)pilha.peek() == '{'))) {
                pilha.pop();
                continue;
            }
            else
                return false;
        }
        if (!pilha.empty())
            return false;
        return true;
    }
    
    public static HashMap<String, Boolean> AlimentaHash(String[] arquivo) {
        HashMap<String, Boolean> dados = new HashMap<String, Boolean>();
        for (String linha : arquivo) {
            Character[] caracteresLinha = new Character[linha.length()];;
            for (int i = 0; i < linha.length(); i++) {
                caracteresLinha[i] = linha.charAt(i);
            }
            dados.put(linha, ChecaParenteses(caracteresLinha));
        }
        return dados;
    }
    
    public static String VasculhaString(String linha) {
        String retorno = "";
        for (int i = 0; i < linha.length(); i++) {
            Character aux = linha.charAt(i);
            
            if (aux == '(' || aux == '[' || aux == '{' ||
                aux == ')' || aux == ']' || aux == '}')
                retorno += aux.toString();
        }
        return retorno;
    }
}
