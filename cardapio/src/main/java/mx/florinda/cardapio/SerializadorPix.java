package mx.florinda.cardapio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.Instant;

public class SerializadorPix {

    public static void main(String[] args) throws IOException {

        Pix pix = new Pix(1L, new BigDecimal("10.99"), "chave.pix@gmail.com", Instant.now(), "Teste 1");

        try (FileOutputStream fos = new FileOutputStream("pix.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(pix);
        }
    }
}
