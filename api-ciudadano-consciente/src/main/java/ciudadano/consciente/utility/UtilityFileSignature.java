package ciudadano.consciente.utility;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// Almost completely copied from GPT.
@RequestScoped
public class UtilityFileSignature {

    @Inject
    Logger audit;

    public String detectFileType(byte[] fileBytes) {
        // Check if the fileBytes array is not empty
        if (fileBytes == null || fileBytes.length == 0) {
            throw new HttpBadRequestException("The file is empty.");
        }

        try (InputStream stream = new ByteArrayInputStream(fileBytes)) {
            // Read the first few bytes from the input stream
            byte[] signatureBytes = new byte[8]; // Adjust the number of bytes to read for your needs
            int bytesRead = stream.read(signatureBytes);

            // Check if enough bytes were read
            if (bytesRead < signatureBytes.length) {
                return null; // Return null if insufficient bytes were read
            }

            // Check for known file signatures
            if (isJPEG(signatureBytes)) {
                return "jpeg";
            } else if (isPNG(signatureBytes)) {
                return "png";
            } else if (isGIF(signatureBytes)) {
                return "gif";
            } else if (isPDF(signatureBytes)) {
                return "pdf";
            } else if (isWebP(signatureBytes)) {
                return "webp";
            } else if (isJSON(fileBytes)) {
                return "json";
            } else if (isBMP(signatureBytes)) {
                return "bmp";
            } else if (isWBMP(signatureBytes)) {
                return "wbmp";
            } else {
                // Add more checks for other file types as needed
                throw new HttpBadRequestException("File type not supported."); // Unknown file type
            }
        } catch (IOException e) {
            audit.debug("Problems detecting file extension. " + e);
            throw new HttpInternalServerException("Problems detecting file extension. " + e);
        }
    }

    private boolean isJSON(byte[] file) {
        // If the conversion fails, is not a valid json
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(new String(file));
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    private boolean isJPEG(byte[] signatureBytes) {
        // Check for JPEG file signature
        return signatureBytes[0] == (byte) 0xFF && signatureBytes[1] == (byte) 0xD8;
    }

    private boolean isPNG(byte[] signatureBytes) {
        // Check for PNG file signature
        return signatureBytes[0] == (byte) 0x89 && signatureBytes[1] == (byte) 0x50 &&
                signatureBytes[2] == (byte) 0x4E && signatureBytes[3] == (byte) 0x47 &&
                signatureBytes[4] == (byte) 0x0D && signatureBytes[5] == (byte) 0x0A &&
                signatureBytes[6] == (byte) 0x1A && signatureBytes[7] == (byte) 0x0A;
    }

    private boolean isGIF(byte[] signatureBytes) {
        // Check for GIF file signature
        return signatureBytes[0] == (byte) 0x47 && signatureBytes[1] == (byte) 0x49 &&
                signatureBytes[2] == (byte) 0x46 && signatureBytes[3] == (byte) 0x38 &&
                (signatureBytes[4] == (byte) 0x37 || signatureBytes[4] == (byte) 0x39) &&
                signatureBytes[5] == (byte) 0x61;
    }

    private boolean isPDF(byte[] signatureBytes) {
        // Check for PDF file signature
        return signatureBytes[0] == (byte) 0x25 && signatureBytes[1] == (byte) 0x50 &&
                signatureBytes[2] == (byte) 0x44 && signatureBytes[3] == (byte) 0x46;
    }

    private boolean isWebP(byte[] signatureBytes) {
        // Check for WebP file signature
        return signatureBytes[0] == 'R' && signatureBytes[1] == 'I' &&
                signatureBytes[2] == 'F' && signatureBytes[3] == 'F' &&
                signatureBytes[8] == 'W' && signatureBytes[9] == 'E' &&
                signatureBytes[10] == 'B' && signatureBytes[11] == 'P';
    }

    private boolean isBMP(byte[] signatureBytes) {
        // Check for BMP file signature
        return signatureBytes[0] == (byte) 0x42 && signatureBytes[1] == (byte) 0x4D;
    }

    private boolean isWBMP(byte[] signatureBytes) {
        // Check for WBMP file signature
        return signatureBytes[0] == (byte) 0x00 && signatureBytes[1] == (byte) 0x00 &&
                signatureBytes[2] == (byte) 0x00 && signatureBytes[3] == (byte) 0x0C;
    }

}
