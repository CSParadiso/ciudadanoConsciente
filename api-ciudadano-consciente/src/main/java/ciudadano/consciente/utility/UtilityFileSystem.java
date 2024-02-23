package ciudadano.consciente.utility;

import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
@Path("files")
public class UtilityFileSystem {

    @ConfigProperty(name = "file.system.images.version.thumbnail")
    String fileSystemThumbnailDirectory;

    @ConfigProperty(name = "image.max.mb.size")
    Integer imageMaxMbFileSize;

    @GET
    @Path("/download/{filename}")
    public Response downloadFile(@PathParam("filename") String filename) {

        return downloadFileFromFileSystem(filename);

    }

    @GET
    @Path("{filename}")
    public byte[] getFile(@PathParam("filename") String filename) {

        return getByteArrayFromFileSystem(filename);

    }

    private Response getFileFromFileSystem(String filename) {

        // Construct the absolute file path
        String filePath = fileSystemThumbnailDirectory + File.separator + filename;

        // Create a File object with the specified file path
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists() && file.isFile()) {

            try {

                // Read the file content into a byte array
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

                // Set the content type based on the file extension
                String contentType = getContentType(filename);

                // Return the file content in the response body with appropriate headers
                return Response
                        .ok(fileContent)
                        .type(contentType)
                        .build();

            } catch (IOException e) {
                throw  new HttpInternalServerException("Failed to retrieve the file.");
            }

        }

        throw  new HttpNoContentException("File not found.");

    }

    public byte[] getByteArrayFromFileSystem(String filename) {


        // Construct the absolute file path
        String filePath = fileSystemThumbnailDirectory + File.separator + filename;

        System.out.println(fileSystemThumbnailDirectory);
        System.out.println(File.separator);
        System.out.println(filename);
        System.out.println(filePath);

        // Create a File object with the specified file path
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists() && file.isFile()) {

            try {

                // Read the file content into a byte array
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

                // Set the content type based on the file extension
                String contentType = getContentType(filename);

                // Return the file content in the response body with appropriate headers
                return fileContent;

            } catch (IOException e) {
                throw  new HttpInternalServerException("Failed to retrieve the file.");
            }

        }

        throw  new HttpNoContentException("File not found.");

    }

    private Response downloadFileFromFileSystem(String filename) {

        // Construct the absolute file path
        String filePath = fileSystemThumbnailDirectory + File.separator + filename;

        // Create a File object with the specified file path
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists() && file.isFile()) {

            // Set the content type based on the file extension
            String contentType = getContentType(filename);

            return Response.ok(file)
                    .type(contentType)
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .build();

        }

        throw  new HttpNoContentException("File not found.");

    }

    private static String getContentType(String fileName) {
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1].toLowerCase();

        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            case "pdf" -> "application/pdf";
            case "js" -> "application/javascript";
            // Add more cases for other file types as needed
            default -> "application/octet-stream"; // Default to binary data
        };
    }

    public void saveToFileSystem(String filename, byte[] file) {

        String filePath = fileSystemThumbnailDirectory + File.separator + filename;

        // Create a FileOutputStream to write the file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            // Write the file content to the output stream
            outputStream.write(file);
        } catch (IOException e) {
            throw new HttpInternalServerException("Failed to save file.");
        }
    }

    public Integer getImageMaxMbFileSize() {
        return imageMaxMbFileSize;
    }

    public boolean smallerThanMaxMbAllowed(Integer imageBytesCount) {

        // #Mb Allowed * 1024 * 1024 (To get the MB from the integer #MB)
        return this.imageMaxMbFileSize * 1024 * 1024 > imageBytesCount;

    }

}
