package ru.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AirportsSearch {

	final static String PATH = "C:/airports.csv"; // Путь до файла

	public static void main(String[] args) throws ParseException, IOException {

		if (args.length <= 0) {
			System.out.println("Укажите номер столбца");
			System.exit(-1);
		}

		String s = checkquit("Введите !quit что бы выйти из программы или нажмите Enter что бы начать поиск");

		while (!"!quit".equals(s)) {

			int col = Integer.parseInt(args[0]) - 1; // Вводим номер колонки пользователя через параметр запуска

			String word = checkquit("Введите выражение для поиска");// Ввод с клавиатуры

			HashMap<Long, String> map = new HashMap<Long, String>();
			try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
				String line = null;

				long number = 0;

				while ((line = br.readLine()) != null) {
					String str[] = line.split(",");
					map.put(number, str[col].replaceAll("\"", ""));
					number++;
				}

			}

			final long startMS = System.currentTimeMillis();

			matchStrins(word, map, col);

			System.out.println("Time: " + (System.currentTimeMillis() - startMS) + " ms");

			s = checkquit("Введите !quit что бы выйти из программы или нажмите Enter что бы продолжить");

		}
		System.exit(0);
	}

	private static String checkquit(String prompt) throws IOException {

		System.out.println(prompt);
		InputStream inputStream = System.in;
		Reader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String word = bufferedReader.readLine();

		return word;
	}

	private static void matchStrins(String subStr, HashMap<Long, String> map, int col) throws IOException {

		final List<Long> results = new ArrayList<Long>();
		for (Long key : map.keySet()) {
			final String value = map.get(key);
			String newsubStr = subStr.substring(0, 1).toUpperCase() + subStr.substring(1).toLowerCase(); // проверка на
																											// регистр

			if (value.startsWith(newsubStr)) {
				results.add(key);
			}
		}
		;
		matchedPrint(results, col);

	}

	private static void matchedPrint(List<Long> matchesLines, int col) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
			String line = null;

			long number = 0;
			int count = 0;

			while ((line = br.readLine()) != null) {
				if (matchesLines.contains(number)) {

					String str[] = line.split(",");

					System.out.println(str[col] + "[" + line + "]");

					count++;
				}
				number++;

			}
			System.out.println("Число найденных строк=" + count);

		}
	}

}
