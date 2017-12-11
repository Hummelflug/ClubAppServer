package de.hummelflug.clubapp.server.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.ImageFile;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ImageFileDAO;

public class ImageFileFacade {

	private final ImageFileDAO imageFileDAO;
	
    private final int BUFFER_LENGTH = 1024;
	
	public ImageFileFacade(ImageFileDAO imageFileDAO) {
		this.imageFileDAO = imageFileDAO;
	}
	
	/** This method returns the ImageFile information that should not be returned to client
	 * 
	 * @param user client user
	 * @param inputStream for image upload
	 * @return ImageFile which is created in database
	 */
	public ImageFile uploadImageFile(User user, InputStream inputStream) {
		if (user != null && inputStream != null) {
			try {
				String relativeFileLocation = "/" + System.currentTimeMillis() + "-" + user.getId() + ".jpg";
				String uploadedFileLocation = System.getenv("IMAGE_PATH") + relativeFileLocation;
				storeFile(inputStream, uploadedFileLocation);
				return imageFileDAO.insert(new ImageFile(user.getId(), relativeFileLocation));
			} catch (IOException e) {
				throw new WebApplicationException(400);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public File downloadImageFile(Long imageId) {
		if (imageId != null) {
			Optional<ImageFile> imageFileOptional = imageFileDAO.findById(imageId);
			if (imageFileOptional.isPresent()) {
				ImageFile image = imageFileOptional.get();
				File download = new File(System.getenv("IMAGE_PATH") + image.getImagePath());
				return download;
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<ImageFile> findAllImageFiles() {
		return imageFileDAO.findAll();
	}
	
	public Optional<ImageFile> findImageFileById(Long id) {
		return imageFileDAO.findById(id);
	}
	
	/** save uploaded file to new location **/
	private void storeFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
	    int read;
	    byte[] buffer = new byte[BUFFER_LENGTH];
	    
	    OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	    
	    while ((read = uploadedInputStream.read(buffer)) != -1) {
	        out.write(buffer, 0, read);
	    }
	    
	    out.flush();
	    out.close();
	}
	
}
