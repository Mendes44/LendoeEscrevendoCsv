package manipulacaoDados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class ManipulandoArquivosPastas {

	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner (System.in);
		
		/*
 * Fazer um programa para ler o caminho de um arquivo .csv
contendo os dados de itens vendidos. Cada item possui um
nome, preço unitário e quantidade, separados por vírgula. Você
deve gerar um novo arquivo chamado "summary.csv", localizado
em uma subpasta chamada "out" a partir da pasta original do
arquivo de origem, contendo apenas o nome e o valor total para
aquele item (preço unitário multiplicado pela quantidade),
conforme exemplo.
		 */
		
		List<Product> list = new ArrayList<>();
		
		System.out.print("Entre com o path: ");
		String sourceFileStr = sc.nextLine();
		
		File sourceFile = new File(sourceFileStr);
		String sourceFolderStr = sourceFile.getParent();
		
		// Criando a pasta mais o arquivo de saida
		boolean success = new File (sourceFolderStr + "//out").mkdir();
		
		String targetFileStr = sourceFileStr + "//out/summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))){
			
			String intemCsv = br.readLine();
			while (intemCsv != null) {
				
				String[] campos = intemCsv.split(","); // Split serve para quebrar os valores que estiverem com ','
				String name = campos[0];
				Double price = Double.parseDouble(campos[1]);
				Integer quantity = Integer.parseInt(campos[2]);
				
				list.add(new Product(name, price, quantity));
				
				intemCsv = br.readLine();
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){
				
				for (Product item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.total()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr + " CRIADO COM SUCESSO!!! ");
				
			}catch (IOException e) {
				System.out.println("ERRO AO GRAVAR ARQUIVO!" + e.getMessage());
			}
			
			
		}catch (IOException e) {
			System.out.println("ERRO AO LER ARQUIVO: " + e.getMessage());
		}
			
		sc. close();
	}

}
