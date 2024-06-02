package ciudadano.consciente.utility;

import com.luciad.imageio.webp.WebPWriteParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@RequestScoped
//@Path("/convertirImagen")
public class UtilityFileCompression {

  @Inject
  Logger auditor;

//  @GET
//  @Path("imagen")
//  @APIResponse(
//          responseCode = "200",
//          description = "Imágen convertida con éxito",
//          content = @Content(mediaType = "image/webp")
//  )
//  public Response hello(@QueryParam("path") String path) throws IOException {
//
//    auditor.debug("Path: " + path);
//
//    BufferedImage image = ImageIO.read(new File(path));
//
//    ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
//
//    WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
//    //Notify encoder to consider WebPWriteParams
//    writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//    //Set lossy compression
//    writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
//    //Set 20% quality. Allowed values are between 0 and 1
//    writeParam.setCompressionQuality(0.2f);
//
//    // Save the image
//    writer.setOutput(new FileImageOutputStream(new File(path + ".webp")));
//    writer.write(null, new IIOImage(image, null, null), writeParam);
//
//    return Response.ok(image)
//            .type("image/webp") // "image/png"
//            .build();
//
//  }

  public byte[] compress(byte[] originalImage) {

    // Convert byte array to BufferedImage
    BufferedImage image;
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage)) {
      image = ImageIO.read(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read image from byte array.", e);
    }

    // Compress the image to WebP format
    try {
      // Create a ByteArrayOutputStream to hold the compressed image bytes
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      // Get the WebP ImageWriter
      Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
      ImageWriter writer = writers.next();

      // Configure the writer
      WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
      writeParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
      writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
      writeParam.setCompressionQuality(0.2f); // Set quality to 20%

      // Write the compressed image to the ByteArrayOutputStream
      try (MemoryCacheImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream)) {
        writer.setOutput(imageOutputStream);
        writer.write(null, new javax.imageio.IIOImage(image, null, null), writeParam);
      }

      // Get the compressed image bytes from the ByteArrayOutputStream
      return outputStream.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Failed to compress image to WebP format.", e);
    }
  }

}