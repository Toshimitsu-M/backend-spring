package com.example.service;

import java.io.BufferedReader;
import java.io.FileReader;

public class NewClass {
	public static void Scan(String[] args) throws Exception {
		FileReader fis = new FileReader("sample.txt");
		BufferedReader reader = new BufferedReader(fis);
		try (reader) {
			reader.lines().forEach(System.out::println);
			}
		}
	}