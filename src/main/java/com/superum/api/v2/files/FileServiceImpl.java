package com.superum.api.v2.files;

import com.superum.api.v2.files.exception.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public File getPicture(String pictureName) {
		LOG.debug("Trying to find file '{}' in folder '{}'", pictureName, PICTURE_FOLDER);
		
		return getFile(PICTURE_FOLDER, pictureName);
	}

	@Override
	public File getDocument(String documentName) {
		LOG.debug("Trying to find file '{}' in folder '{}'", documentName, DOCUMENT_FOLDER);
		
		return getFile(DOCUMENT_FOLDER, documentName);
	}
	
	@Override
	public String saveFile(String folder, String name, MultipartFile multiPartFile) throws IOException {
		if (!(folder.equals(PICTURE_FOLDER) || folder.equals(DOCUMENT_FOLDER)))
			throw new FileNotFoundException("Couldn't find folder: " + folder);
		
		if (folder.equals(PICTURE_FOLDER) && multiPartFile.getSize() > 1024 * 1024)
			return "Please upload picture that is less than 1MB in size!";
		
		String contentType = multiPartFile.getOriginalFilename();
		if (contentType == null)
			contentType = "";
		
		int fileTypeIndex = contentType.lastIndexOf('.');
		contentType = fileTypeIndex < 0 ? "" : contentType.substring(fileTypeIndex);
		if (!contentType.isEmpty() && name.endsWith(contentType))
			name = name.substring(0, name.lastIndexOf(contentType));
		LOG.debug("Determined the following file extension: {}", contentType);
		
		String updatedName;
		File file;
		do {
			updatedName = name + "-" + System.currentTimeMillis() + contentType;
			LOG.debug("Trying to save file '{}' in folder '{}'", name, folder);
			
			file = makeFile(folder, updatedName);
		} while (file.exists());
		
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		byte[] bytes = multiPartFile.getBytes();
        stream.write(bytes);
        stream.close();
        LOG.debug("File successfully saved.");
        
		return updatedName;
	}
	
	@Override
	public void deletePicture(String pictureName) {
		LOG.debug("Trying to delete file '{}' in folder '{}'", pictureName, PICTURE_FOLDER);
		
		deleteFile(PICTURE_FOLDER, pictureName);
	}

	@Override
	public void deleteDocument(String documentName) {
		LOG.debug("Trying to delete file '{}' in folder '{}'", documentName, DOCUMENT_FOLDER);
		
		deleteFile(DOCUMENT_FOLDER, documentName);
	}

	// PRIVATE
	
	private File makeFile(String folder, String name) {
		Path path = Paths.get(folder, name);
		LOG.debug("Path constructed: {}", path);
		
		File file = path.toFile();
		LOG.debug("File constructed: {}", file);
		
		return file;
	}
	
	private File getFile(String folder, String name) {
		File file = makeFile(folder, name);
		
		if (file.exists())
			return file;
		
		throw new FileNotFoundException("Couldn't find file: " + name);
	}
	
	private void deleteFile(String folder, String name) {
		if (!getFile(folder, name).delete())
			throw new AssertionError("Deletion of file has failed for no explicable reason");
	}
	
	private static final String PICTURE_FOLDER = "pictures";
	private static final String DOCUMENT_FOLDER = "documents";
	
	private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

}
