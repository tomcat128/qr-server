package de.fewobacher.controller;

import com.google.zxing.WriterException;
import de.fewobacher.constant.ErrorLevel;
import de.fewobacher.properties.ApplicationProperties;
import de.fewobacher.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
public class CodeController
{
    final private static String CHARSET = "UTF-8";

    final private static String OUTPUT_FORMAT = "PNG";

    final private static String OUTPUT_FORMAT_MIME_TYPE = "image/png";

    private final QrCodeService service;

    private final ApplicationProperties properties;

    public CodeController(ApplicationProperties properties, QrCodeService service)
    {
        this.properties = properties;
        this.service = service;
    }

    /**
     * This endpoint will convert a text message into an qr code
     *
     * @param text
     * @param imageSize
     * @param errorLevel
     * @return test converted to qr code (ong)
     * @throws IOException
     * @throws WriterException
     */

    @GetMapping("/text")
    public ResponseEntity<byte[]> textToQr(
            @RequestParam(name = "input") String text,
            @RequestParam(name = "size", required = false) Optional<Integer> imageSize,
            @RequestParam(name = "el", required = false) Optional<ErrorLevel> errorLevel) throws IOException, WriterException
    {

        BufferedImage bufferedImage = service.createQR(
                text,
                CHARSET,
                errorLevel.orElseGet(properties::getDefaultErrorLevel),
                imageSize.orElseGet(properties::getDefaultSize),
                imageSize.orElseGet(properties::getDefaultSize));

        // Prepare output
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, OUTPUT_FORMAT, bos);
        return ResponseEntity
                .ok()
                .header("Content-type", OUTPUT_FORMAT_MIME_TYPE)
                .body(bos.toByteArray());
    }

    /**
     * This endpoint will create an wifi qr code with the given parameters
     *
     * @param ssid
     * @param password
     * @param imageSize
     * @param errorLevel
     * @return test converted to qr code (ong)
     * @throws IOException
     * @throws WriterException
     */
    @GetMapping("/wifi")
    public ResponseEntity<byte[]> wifiToQr(
            @RequestParam(name = "ssid") String text,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "size", required = false) Optional<Integer> imageSize,
            @RequestParam(name = "el", required = false) Optional<ErrorLevel> errorLevel) throws IOException, WriterException
    {
        return null;
    }


}
