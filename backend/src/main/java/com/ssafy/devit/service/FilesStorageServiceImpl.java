package com.ssafy.devit.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	@Value("${spring.file.location}")
	private String filePath;

	@Override
	public void init() {
		try {
			Files.createDirectory(Paths.get(filePath));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public String save(MultipartFile file) {
		String result = null;
		try {
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);
			result = UUID.randomUUID() + format;
			Files.copy(file.getInputStream(),
					Paths.get(filePath).resolve(result));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return result;
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = Paths.get(filePath).resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(filePath).toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(Paths.get(filePath), 1).filter(path -> !path.equals(Paths.get(filePath)))
					.map(Paths.get(filePath)::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}
}