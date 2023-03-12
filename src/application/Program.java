package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {
	
	private static final Integer ANO = 2016;

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Sale> sale = new ArrayList<>();
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				int items = Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				
				sale.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}
				
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			
			List<String> newSale = sale.stream()
					.filter(x -> x.getYear().equals(ANO))
					.sorted(Comparator.reverseOrder())
					.map(x -> x.toString())
					.limit(5)
					.collect(Collectors.toList());
			newSale.forEach(System.out::println);
			
			System.out.println();			
			List<Double> priceSale1 = sale.stream()
					.filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() == 1)
					.map(x -> x.getTotal())
					.collect(Collectors.toList());
			
			List<Double> priceSale7 = sale.stream()
					.filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() == 7)
					.map(x -> x.getTotal())
					.collect(Collectors.toList());
			
			double sum1 = priceSale1.stream()
				.reduce(0.0, (x,y) -> x + y);
			
			double sum7 = priceSale7.stream()
				.reduce(0.0, (x,y) -> x + y);
			
			System.out.print("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sum1 + sum7));
			
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		finally {
			sc.close();
		}
	}
}
